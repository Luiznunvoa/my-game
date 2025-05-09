package com.game.handler;

import com.game.model.Message;
import com.game.util.ConsoleColor;
import com.game.util.ConsoleUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;

/**
 * ClientHandler que, ao ativar o canal, abre um loop de leitura
 * do console e envia cada linha como uma nova Message.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Ao ativar, inicia uma thread que lê do console
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            try {
                ConsoleUtil.printf("[Client] Digite suas mensagens (\"quit\" para sair):\n", ConsoleColor.CYAN);
                while (true) {
                    if (!scanner.hasNextLine()) {
                        break; // fim de input
                    }
                    String line = scanner.nextLine().trim();
                    if ("quit".equalsIgnoreCase(line)) {
                        ConsoleUtil.printf("[Client] Encerrando cliente...\n", ConsoleColor.YELLOW);
                        ctx.close();
                        break;
                    }
                    // Monta e envia a Message
                    Message msg = new Message();
                    msg.content = line;
                    ctx.writeAndFlush(msg);
                }
            } finally {
                scanner.close();
            }
        }, "console-input-thread").start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) {
        ConsoleUtil.printf(
                "%s\n",
                message.content);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ConsoleUtil.printf(
                "[Client] Error:\n %s\n",
                ConsoleColor.RED,
                cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
