package com.game.app;

import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.channel.ChannelInitializer;

import com.game.codec.JsonDecoder;
import com.game.codec.JsonEncoder;
import com.game.handler.ClientHandler;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                .addLast(new LoggingHandler(LogLevel.DEBUG))
                .addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                .addLast("strDecoder", new StringDecoder()) // bytes → String
                .addLast("jsonDecoder", new JsonDecoder()) // String → Message
                .addLast("strEncoder", new StringEncoder()) // String ← bytes
                .addLast("jsonEncoder", new JsonEncoder()) // Message → String
                .addLast("clientHandler", new ClientHandler());
    }
}
