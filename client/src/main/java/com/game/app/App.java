package com.game.app;

import com.game.util.ConsoleColor;
import com.game.util.ConsoleUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Client Application Entry Point
 */
public class App {
    private final String host;
    private final int port;

    /**
     * Constructor: saves host and port for connection.
     *
     * @param host remote server hostname or IP
     * @param port remote server TCP port
     */
    public App(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Boots up the Netty client, connects to server,
     * and pumps console input into the channel.
     *
     * @throws Exception if bootstrapping or I/O fails
     */
    public void run() {
        EventLoopGroup group = new MultiThreadIoEventLoopGroup(
                NioIoHandler.newFactory());

        try {
            Bootstrap bootstrap = new Bootstrap()
                    // Assigns the EventLoopGroup that will handle all I/O
                    .group(group)
                    // Specifies the Channel implementation to use
                    .channel(NioSocketChannel.class)
                    // Sets a socket option: keep‑alive probes to detect dead peers
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // Debug handler
                    // .handler(new LoggingHandler(LogLevel.INFO))
                    // Pipeline Initialization
                    .handler(new ClientChannelInitializer());

            // Attempt to connect
            ChannelFuture future = bootstrap.connect(host, port).sync();
            ConsoleUtil.printf("[Client] Connected to server %s:%d 🚀\n", ConsoleColor.GREEN, host, port);

            // Wait until the connection is closed
            future.channel().closeFuture().sync();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            ConsoleUtil.printlnBold("[Client] Connection attempt was interrupted, shutting down....", ConsoleColor.RED);

        } catch (Exception e) {
            // Include host, port and exception message in the output
            ConsoleUtil.printf("[Client] Unexpected error during connection:\n%s\n%s\n", ConsoleColor.RED, e.getMessage(), e.getCause());
            
        } finally {
            // Only shutdown here, once
            group.shutdownGracefully();
        }
    }

    /**
     * Program entry point.
     * Reads host and port from args or uses defaults.
     */
    public static void main(String[] args) throws Exception {
        // Read host/port from args or use defaults
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;

        ConsoleUtil.printf("[Client] Trying to connect client on %s:%d…%n", ConsoleColor.YELLOW, host, port);
        new App(host, port).run();
    }
}
