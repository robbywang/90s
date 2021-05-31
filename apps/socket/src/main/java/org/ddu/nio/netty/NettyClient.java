package org.ddu.nio.netty;

import org.ddu.nio.netty.handler.ClientInboundHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	public void connect(String host, int port) throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ClientInboundHandler());
				}
			});

			// Start the client.
			ChannelFuture f = b.connect(host, port).sync();
			
			System.out.println("Client connected to server at: " + host + ":" + port);
			
			f.channel().closeFuture().sync();
			
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
//		NettyServer server = new NettyServer();
//		server.start(8000);
//		
//		Thread.sleep(2*1000);
		
		NettyClient client = new NettyClient();
		client.connect("127.0.0.1", 8000);
	}
}
