package com.game.app;

import com.game.codec.JsonDecoder;
import com.game.codec.JsonEncoder;
import com.game.handler.ServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Initializes the server-side channel pipeline.
 *
 * <p>
 * Configures logging, framing, serialization, and business logic handler.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new LoggingHandler(LogLevel.DEBUG))
                .addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                .addLast("strDecoder", new StringDecoder()) // bytes → String
                .addLast("jsonDecoder", new JsonDecoder()) // String → Message
                .addLast("strEncoder", new StringEncoder()) // String ← bytes
                .addLast("jsonEncoder", new JsonEncoder()) // Message → String
                .addLast("clientHandler", new ServerHandler());
    }
}
