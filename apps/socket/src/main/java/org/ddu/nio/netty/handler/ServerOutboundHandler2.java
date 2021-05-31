package org.ddu.nio.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class ServerOutboundHandler2 extends ChannelOutboundHandlerAdapter{
	@Override
	// 向client发送消息
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println("OutboundHandler2.write");
		// 执行下一个OutboundHandler
		super.write(ctx, msg, promise);
	}
}
