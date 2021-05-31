package org.ddu.nio.csl;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class JavaNioServer {
	private ExecutorService mExecutor = null;
	private ServerSocketChannel mServerSocketChannel = null;
	private ServerSocket mServerSocket = null;

	private Selector mSelector = null;

	private Pipe mReturnSignalingChannel = null;
	private Pipe.SourceChannel pipeSourceChannel = null;
	private LinkedBlockingQueue<SocketChannel> mReturnSockets = null;

	private int mThreadPoolSize = 5;
	private int mInPort = 2011;
	private int mConnectBackLog = 5;
	private int miChannelReaderBufferSize = 1;
	private int miFairShareRecordCount = 2;

	private Thread mSelectorThread = null;
	private Thread mChannelReadThread = null;

	private boolean mIsStarted = false;

	private List<SocketChannelWrapper> socketChannelWrappers = Collections
			.synchronizedList(new LinkedList<SocketChannelWrapper>());
	private List<SocketChannel> mClientSockets = Collections.synchronizedList(new ArrayList<>());

	private Object returnChannelLock = new Object();
	private final static byte RC_ADD_TO_READ = 0x1;

	public JavaNioServer(int port) {
		this.mInPort = port;
		mExecutor = Executors.newFixedThreadPool(mThreadPoolSize);
		openServerSocket();
	}

	private void openServerSocket() {
		System.out.println("openServerSocket...");
		try {
			mServerSocketChannel = ServerSocketChannel.open();
			mServerSocketChannel.configureBlocking(false);
			mServerSocket = mServerSocketChannel.socket();
			mServerSocket.setReuseAddress(true);

			InetSocketAddress isa = new InetSocketAddress(mInPort);
			mServerSocket.bind(isa, mConnectBackLog);

			configSelector();

			mIsStarted = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		mSelectorThread = new Thread("DDU selector thread") {
			public void run() {
				handleSelection();
			}
		};
		mSelectorThread.start();

		mChannelReadThread = new Thread("DDU Channel Read") {
			public synchronized void run() {
				while (mIsStarted) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					SocketChannelWrapper socketChannelWrapper = null;

					do {
						socketChannelWrapper = null;
						synchronized (socketChannelWrappers) {
							if (!socketChannelWrappers.isEmpty()) {
								socketChannelWrapper = socketChannelWrappers.remove(0);
							}
						}

						final SocketChannelWrapper socketChannelWrapperToReadFrom = socketChannelWrapper;

						if (socketChannelWrapperToReadFrom != null) {
							Runnable r = new Runnable() {
								public void run() {

									if (handleRead(socketChannelWrapperToReadFrom)) {
										socketChannelWrappers.add(socketChannelWrapperToReadFrom);
									}

									synchronized (mChannelReadThread) {
										mChannelReadThread.notify();
									}
								}
							};

							mExecutor.execute(r);
						}
					} while (socketChannelWrapper != null);
				}
			}
		};
		mChannelReadThread.start();
	}

	private void configSelector() throws IOException {
		System.out.println("configSelector...");

		mReturnSignalingChannel = Pipe.open();
		pipeSourceChannel = mReturnSignalingChannel.source();
		mReturnSockets = new LinkedBlockingQueue<SocketChannel>();
		pipeSourceChannel.configureBlocking(false);
		mSelector = Selector.open();
		pipeSourceChannel.register(mSelector, SelectionKey.OP_READ);
		mServerSocketChannel.register(mSelector, SelectionKey.OP_ACCEPT);
	}

	private void handleSelection() {
		try {
			while (mIsStarted) {
				try {
					mSelector.select();
					Set<SelectionKey> skeys = mSelector.selectedKeys();
					System.out.println(skeys.size());
					Iterator<SelectionKey> it = skeys.iterator();
					while (it.hasNext()) {
						SelectionKey readyKey = it.next();
						it.remove();
						System.out.println(skeys.size());

						System.out.println(String.format("isReadable=%s, isWritable=%s, isAcceptable=%s, readyKey=%s",
								readyKey.isReadable(), readyKey.isWritable(), readyKey.isAcceptable(),
								readyKey.isConnectable()));

						if (readyKey.isValid()) {
							if (readyKey.isAcceptable()) {
								System.out.println("Server get new connection " + readyKey.readyOps() + " "
										+ readyKey.interestOps());
								SocketChannel socketChannel = mServerSocketChannel.accept();
								socketChannel.configureBlocking(false);
								socketChannel.register(mSelector, SelectionKey.OP_READ);

							} else {
								final SelectableChannel sc = readyKey.channel();

								if (sc == pipeSourceChannel) {
									System.out.println("Server get returned channel " + readyKey.readyOps() + " "
											+ readyKey.interestOps());
									// "READ event" on returned channel - means
									// channel was
									// returned to the selector from service
									// thread
									SocketChannel returnedChannel = getFromReturnChannel();
									returnedChannel.configureBlocking(false);
									returnedChannel.register(mSelector, SelectionKey.OP_READ);
								} else {
									// Client socket connection, deselects it
									// from the selector and processes it.
									System.out.println("Server get client data." + readyKey.readyOps() + " "
											+ readyKey.interestOps());
									readyKey.cancel();
									SocketChannelWrapper channelWrapper = new SocketChannelWrapper((SocketChannel) sc,
											miChannelReaderBufferSize, miFairShareRecordCount);

									socketChannelWrappers.add(channelWrapper);

									synchronized (mChannelReadThread) {
										mChannelReadThread.notify();
									}
								}
							}
							/*
							 * else if (readyKey.isWritable()) {
							 * System.out.println("channel is writable.");
							 * readyKey.cancel(); } else if
							 * (readyKey.isConnectable()) {
							 * System.out.println("channel is connectable.");
							 * readyKey.cancel(); }
							 */

						} else {
							// Channel was cancelled in the background
							System.out.println("Invalid readyKey. Skip it.");
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally { // Time to shut down
			closeAll();
		}
	}

	private boolean handleRead(SocketChannelWrapper channelWrapper) {
		boolean keepRead = true;
		SocketChannel channel = channelWrapper.getSocketChannel();
		ByteBuffer readBuf = channelWrapper.getReadByteBuffer();

		int bytesRead;
		try {
			bytesRead = channel.read(readBuf);
			System.out.println("bytesRead: " + bytesRead);

			switch (bytesRead) {
			case 0:// 如果读不到数据超过3次，放回selector重新select
				channelWrapper.incrementUnsuccessfulReadAttempts();
				if (channelWrapper.getUnsuccessfulReadAttempts() > 3) {
					System.out.println("putToReturnChannel");
					putToReturnChannel(channel);
					keepRead = false;
				}
				break;
			case -1:
				// 链接关闭
				channel.close();
				keepRead = false;
				break;
			default:
				String data = new String(readBuf.array(), "UTF-8");
				System.out.println(String.format("Server get data: %s, readBuf: %s", data, readBuf.toString()));

				// readBuf.flip();
				System.out.println("write Buf: " + readBuf.toString());
				// channel.write(readBuf);
				int bytesWrite = channel.write(ByteBuffer.wrap(data.getBytes()));
				System.out.println("bytesWrite: " + bytesWrite);
				readBuf.clear();
				System.out.println("cleard Buf: " + readBuf.toString());
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return keepRead;
	}

	/**
	 * Socket channel state class.
	 */
	private static class SocketChannelWrapper {
		/**
		 * The socket channel.
		 */
		private SocketChannel moSocketChannel;

		/**
		 * The byte buffer used to read from the socket channel.
		 */
		private ByteBuffer moReadByteBuffer;

		/**
		 * The byte buffer used to write to the socket channel. It shares the
		 * same byte-array storage with the read byte buffer.
		 */
		private ByteBuffer moWriteByteBuffer;

		/**
		 * Flag indicating if reading has started.
		 */
		private boolean mbHasReadingStarted;

		/**
		 * The total number of bytes read from this channel.
		 */
		private int miTotalBytesRead;

		/**
		 * The current number of unsuccessful read attempts.
		 */
		private int miCrtUnsuccessfulReadAttempts;

		/**
		 * The number of consecutive max readers.
		 */
		private int miNumberConsecutiveMaxReads;

		/**
		 * The number indicating fair share record count.
		 */
		private int miFairshareRecordCount;

		/**
		 * The number indicating fair share increment.
		 */
		private final int miFairshareIncrement = 150;

		/**
		 * The number indicating fair share decrement.
		 */
		private int miFairshareDecrement = 20;

		/**
		 * The remote IP address.
		 */
		private String mszRemoteIpAddress = null;

		/**
		 * The remote port Id.
		 */
		private int miRemotePort = 0;

		/**
		 * Constructor.
		 *
		 * @param socketChannel
		 *            The socket channel
		 * @param bufSize
		 *            The read buffer size
		 * @param iFairshareRecordCount
		 *            The number of fairshare record
		 */
		public SocketChannelWrapper(SocketChannel socketChannel, int bufSize, int iFairshareRecordCount) {
			moSocketChannel = socketChannel;

			Socket oSocket = moSocketChannel.socket();

			if (oSocket != null) {
				InetAddress oIPAdd = oSocket.getInetAddress();

				if (oIPAdd != null) {
					mszRemoteIpAddress = oSocket.getInetAddress().getHostAddress();
				}

				miRemotePort = oSocket.getPort();
			}

			moReadByteBuffer = ByteBuffer.allocate(bufSize);
			moWriteByteBuffer = moReadByteBuffer.duplicate();
			mbHasReadingStarted = false;
			miTotalBytesRead = 0;
			miCrtUnsuccessfulReadAttempts = 0;
			miNumberConsecutiveMaxReads = 0;
			miFairshareRecordCount = iFairshareRecordCount;
		}

		/**
		 * Returns the address to which the socket is connected.
		 *
		 * @return the remote IP address to which this socket is connected, or
		 *         <code>null</code> if the socket is not connected.
		 */
		public String getRemoteIpAddress() {
			return (mszRemoteIpAddress);
		}

		/**
		 * Returns the remote port to which this socket is connected.
		 *
		 * @return the remote port number to which this socket is connected, or
		 *         0 if the socket is not connected yet.
		 */
		public int getRemotePort() {
			return (miRemotePort);
		}

		/**
		 * Socket channel accessor.
		 *
		 * @return The socket channel.
		 */
		public SocketChannel getSocketChannel() {
			return moSocketChannel;
		}

		/**
		 * Read byte buffer accessor.
		 *
		 * @return The byte buffer used to read data from the channel
		 */
		public ByteBuffer getReadByteBuffer() {
			return moReadByteBuffer;
		}

		/**
		 * Gets the fair share record count used to read data from the channel.
		 * 
		 * @return The fair share record count used to read data from the
		 *         channel
		 */
		public int getFairShareIncrement() {
			return miFairshareIncrement;
		}

		/**
		 * Sets the fair share record count used to read data from the channel.
		 * 
		 * @param iFairshareRecordCount
		 *            the fair share record count
		 */
		public void setFairShareRecordCount(int iFairshareRecordCount) {
			miFairshareRecordCount = iFairshareRecordCount;
		}

		/**
		 * Increments the fair share record count used to read data from the
		 * channel.
		 */
		public void incrementFairShareRecordCount() {
			if (miFairshareRecordCount < 500) {
				miFairshareRecordCount += miFairshareIncrement;
			}
		}

		/**
		 * Decrements the fair share record count used to read data from the
		 * channel.
		 */
		public void decrementFairShareRecordCount() {
			if (miFairshareRecordCount > 60) {
				miFairshareRecordCount -= miFairshareDecrement;
			}
		}

		/**
		 * Write byte buffer accessor.
		 *
		 * @return The byte buffer used to write data to the channel
		 */
		public ByteBuffer getWriteByteBuffer() {
			return moWriteByteBuffer;
		}

		/**
		 * Reading start flag accessor.
		 *
		 * @return the flag
		 */
		public boolean hasReadingStarted() {
			return mbHasReadingStarted;
		}

		/**
		 * Reading start flag setter.
		 *
		 * @param flag
		 *            the reading start flag
		 */
		public void setHasReadingStarted(boolean flag) {
			mbHasReadingStarted = flag;
		}

		/**
		 * Add one more consecutive max read.
		 * 
		 * @return The total number of consecutive max reads which has been
		 *         incremented
		 */
		public int increaseNumberConsecutiveMaxReads() {
			miNumberConsecutiveMaxReads++;
			return miNumberConsecutiveMaxReads;
		}

		/**
		 * Reset consecutive max read counter.
		 *
		 */
		public void resetNumberConsecutiveMaxReads() {
			miNumberConsecutiveMaxReads = 0;
		}

		/**
		 * Total bytes read accessor.
		 *
		 * @return The total number of bytes read from this channel
		 */
		public int getTotalBytesRead() {
			return miTotalBytesRead;
		}

		/**
		 * Record more bytes read from this channel.
		 *
		 * @param bytesRead
		 *            The number of read bytes
		 */
		public void addBytesRead(int bytesRead) {
			miTotalBytesRead += bytesRead;
		}

		/**
		 * Current unsuccesful read attempts accessor.
		 *
		 * @return The current number of unsuccessful read attempts
		 */
		public int getUnsuccessfulReadAttempts() {
			return miCrtUnsuccessfulReadAttempts;
		}

		/**
		 * Resets the current number of unsuccessful read attempts.
		 */
		public void resetUnsuccessfulReadAttempts() {
			miCrtUnsuccessfulReadAttempts = 0;
		}

		/**
		 * Increments the current number of unsuccessful read attempts.
		 *
		 * @return The current number of unsuccessful read attempts
		 */
		public int incrementUnsuccessfulReadAttempts() {
			miCrtUnsuccessfulReadAttempts++;

			return miCrtUnsuccessfulReadAttempts;
		}

		/**
		 * Debug representation.
		 *
		 * @return A debug representation of this object.
		 */
		public String toString() {
			return moSocketChannel.toString();
		}
	}

	private SocketChannel getFromReturnChannel() throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1);
		SocketChannel channel = null;

		synchronized (returnChannelLock) {
			if (pipeSourceChannel != null) {
				pipeSourceChannel.read(buffer);

				channel = mReturnSockets.poll();
			}
		}
		return channel;
	}

	private int readToByteBuffer(SocketChannel channel, ByteBuffer buf) throws IOException {
		int bytesRead = channel.read(buf);

		if (bytesRead != 0) {
			return bytesRead;
		}

		int retryAttempts = 5;
		long sleepTime = 10;

		for (int i = 0; i < retryAttempts && bytesRead == 0; i++) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}

			try {
				bytesRead = channel.read(buf);
			} catch (IOException e) {
				throw new EOFException();
			}

			sleepTime += 1;
		}

		return bytesRead;
	}

	private void putToReturnChannel(SocketChannel channel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1);
		buffer.put(RC_ADD_TO_READ);
		buffer.flip();

		synchronized (returnChannelLock) {
			Pipe.SinkChannel pipeSinkChannel = mReturnSignalingChannel.sink();

			if (pipeSinkChannel != null) {
				pipeSinkChannel.write(buffer);
				mReturnSockets.offer(channel);
			}
		}
	}

	private synchronized void closeAll() {
		// closeSelector();
		// closeServerClientSocket();
	}

    public static void main(String[] args) throws Exception {

        final int port = 2011;
        new JavaNioServer(port);
//        new NettyNioClient(host, port).start();
    }
	
}
