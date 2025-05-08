package com.game.app;

import com.game.util.ConsoleColor;
import com.game.util.ConsoleUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Server Application Entry Point
 */
public class App {
    private final int port;

    /**
     * Constructs the server application bound to the specified port.
     *
     * @param port TCP port for the server to listen on.
     */
    public App(int port) {
        this.port = port;
    }

    /**
     * Starts the Netty server, sets up pipeline, and begins listening.
     *
     * @throws InterruptedException if the server is interrupted while binding.
     */
    public void start() throws InterruptedException {
        // Boss group accepts incoming connections (1 thread)
        EventLoopGroup bossGroup = new MultiThreadIoEventLoopGroup(
                1,
                NioIoHandler.newFactory());
        // Worker group handles the I/O of accepted connections
        EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(
                NioIoHandler.newFactory());

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // Use NIO selector-based channel
                    .option(ChannelOption.SO_BACKLOG, 100) // Max queued connections
                    .handler(new LoggingHandler(LogLevel.INFO)) // Log server socket events
                    .childHandler(new ServerChannelInitializer());

            // Bind and start to accept incoming connections
            ChannelFuture future = bootstrap.bind(port).sync();
            ConsoleUtil.printf("[Server] Running on port %d 🚀\n", ConsoleColor.GREEN, port);

            // Wait until the server socket is closed
            future.channel().closeFuture().sync();
        } finally {
            // Gracefully shut down both event loop groups
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        ConsoleUtil.printf("[Server] Starting server on the port %d…%n", ConsoleColor.YELLOW, port);
        new App(port).start();
    }
}
