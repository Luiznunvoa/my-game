package com.game.util;

import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.ansi;

import java.io.Console;
import java.util.Scanner;

public class ConsoleUtil {
    static {
        AnsiConsole.systemInstall();
    }

    public static void clearConsole() {
        System.out.print(ansi().eraseScreen().cursor(0, 0));
    }

    public static void println(String msg, ConsoleColor color) {
        AnsiConsole.out().println(color + msg + ConsoleColor.RESET);
    }

    public static void print(String msg, ConsoleColor color) {
        AnsiConsole.out().print(color + msg + ConsoleColor.RESET);
    }

    public static void printlnBold(String msg) {
        AnsiConsole.out().println(
                ansi()
                        .bold() // ativa negrito
                        .a(msg)
                        .boldOff() // desativa negrito
                        .toString());
    }

    public static void printlnBold(String msg, ConsoleColor color) {
        AnsiConsole.out().println(
                ansi()
                        .bold()
                        .a(color + msg + ConsoleColor.RESET)
                        .boldOff()
                        .toString());
    }

    public static void printBold(String msg, ConsoleColor color) {
        AnsiConsole.out().print(
                ansi()
                        .bold()
                        .a(color + msg + ConsoleColor.RESET)
                        .boldOff()
                        .toString());
    }
    public static void printf(String format, Object... args) {
        String formatted = String.format(format, args);
        AnsiConsole.out().print(formatted + ConsoleColor.RESET);
    }

    public static void printf(String format, ConsoleColor color, Object... args) {
        String formatted = String.format(format, args);
        AnsiConsole.out().print(color + formatted + ConsoleColor.RESET);
    }

    public static String readLine(String prompt, ConsoleColor color) {
        print(prompt + " ", color);
        Console console = System.console();
        if (console != null) {
            return console.readLine();
        } else {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            scanner.close();
            return input;
        }
    }
}
