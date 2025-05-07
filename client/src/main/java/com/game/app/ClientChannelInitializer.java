package com.game.app;

import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.channel.ChannelInitializer;

import com.game.handler.ClientHandler;

/**
 * Initializes the Netty channel pipeline for the client.
 *
 * <p>
 * This class sets up the sequence of handlers that process inbound and outbound
 * data. It includes:
 * <ul>
 * <li>A {@link LengthFieldBasedFrameDecoder} to handle inbound message framing
 * based on a length field.</li>
 * <li>A {@link LengthFieldPrepender} to prepend the length field to outbound
 * messages.</li>
 * <li>JSON message decoder and encoder for
 * serialization/deserialization.</li>
 * <li>the custom {@link ClientHandler} for application logic.</li>
 * </ul>
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                // 1) Inbound framing decoder
                .addLast(new LengthFieldBasedFrameDecoder(
                        10 * 1024 * 1024, // max frame length (10 MB)
                        0, // offset of length field within the frame
                        4, // length of the length field in bytes
                        0, // compensation value to add to the length
                        4)) // bytes to strip out (length field itself)
                // 2) JSON -> Message
                .addLast(new MessageDecoder())
                // 3) Business logic
                .addLast(new ClientHandler())
                // 4) Outbound framing prepender
                .addLast(new LengthFieldPrepender(4))
                // 5) Message -> JSON
                .addLast(new MessageEncoder());
    }
}
