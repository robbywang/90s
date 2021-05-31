package org.ddu.nio.csl;

import java.util.Scanner;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Listing 2.3 of <i>Netty in Action</i>
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
@Sharable
public class ClientInboundHandler extends
        SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    	System.out.println("channelActive");
    	acceptInput(ctx);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,
        Object msg) {
        System.out.println("Client received: " + msg);
    }
    
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
        ByteBuf in) {
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
        Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    private void acceptInput(ChannelHandlerContext ctx) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        while (n != 0) {
        	ctx.writeAndFlush(Unpooled.copiedBuffer(String.valueOf(n), CharsetUtil.UTF_8));
        	n = in.nextInt();
        }
        
		System.out.println("Client close the socket.");
		ctx.channel().close();
        
    }
}
