package ua.edu.chmnu.net_dev.c4.tcp.intarray.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class IntArray {

    private final static int DEFAULT_PORT = 6710;

    private static Integer resolvePort(String src, int defaultPort) {
        try {
            return Integer.parseInt(src);
        } catch (Exception e) {
            return defaultPort;
        }
    }

    private static void processClient(Socket socket) {

        try (var ir = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var writer = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Establishing connection from: " + socket.getRemoteSocketAddress());

            String promptNick = "Enter your nick:";

            writer.println(promptNick);

            String nick = ir.readLine();

            String promptData = "Enter message (Q to quit):";

            System.out.println("Client nick: " + nick);

            String inPrefix = "[" + nick + "] < ";

            String outPrefix = "[" + nick + "] > ";

            while (!socket.isClosed()) {
                try {
                    System.out.println("Waiting for input from: " + nick);

                    writer.println(promptData);

                    String inLine = ir.readLine();

                    if (inLine == null || inLine.isBlank() || inLine.equalsIgnoreCase("Q")) {
                        break;
                    }

                    System.out.println(inPrefix + inLine);

                    String outLine = MaxMinAvg(inLine);

                    writer.println(outLine);

                    System.out.println(outPrefix + outLine);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }

            System.out.println("Client " + nick + " disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Integer port = DEFAULT_PORT;

        if (args.length > 0 ) {
            port = resolvePort(args[0], DEFAULT_PORT);
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Listening on port " + port);
            while (!serverSocket.isClosed()) {
                System.out.println("Waiting for connection...");
                try (Socket socket = serverSocket.accept()) {
                    processClient(socket);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String MaxMinAvg(String source) {
        try {
            String[] parts = source.trim().split("\\s+");
            int[] numbers = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Integer.parseInt(parts[i]);
            }

            int min = numbers[0];
            int max = numbers[0];
            int sum = 0;

            for (int num : numbers) {
                if (num < min) min = num;
                if (num > max) max = num;
                sum += num;
            }

            double avg = (double) sum / numbers.length;

            // Формування відповіді
            return String.format("Min: %d, Max: %d, Avg: %.2f", min, max, avg);
        } catch (Exception e) {
            return "Invalid input. Please provide a space-separated list of integers.";
        }
    }
}
