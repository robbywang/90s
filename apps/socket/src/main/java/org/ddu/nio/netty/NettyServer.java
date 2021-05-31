package org.ddu.nio.netty;

import org.ddu.nio.netty.handler.ServerInboundHandler1;
import org.ddu.nio.netty.handler.ServerInboundHandler2;
import org.ddu.nio.netty.handler.ServerOutboundHandler1;
import org.ddu.nio.netty.handler.ServerOutboundHandler2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	public void start(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							// 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2
							// OutboundHandler1
							ch.pipeline().addLast(new ServerOutboundHandler1());
							ch.pipeline().addLast(new ServerOutboundHandler2());
							// 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1
							// InboundHandler2
							ch.pipeline().addLast(new ServerInboundHandler1());
							ch.pipeline().addLast(new ServerInboundHandler2());
						}
					}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture f = b.bind(port).sync();
			
			System.out.println("Server is started at port: " + port);

			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		NettyServer server = new NettyServer();
		server.start(8000);
	}
}
