package ua.edu.chmnu.net_dev.c4.tcp.echo.client;

import ua.edu.chmnu.net_dev.c4.tcp.core.client.EndPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class EchoParallel {
    private final static int DEFAULT_PORT = 6710;

    public static String generateCharacters() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static void main(String[] args) throws IOException {

        EndPoint endPoint;

        if (args.length > 0) {
            endPoint = new EndPoint(args[0]);
        } else {
            endPoint = new EndPoint("localhost", DEFAULT_PORT);
        }

        try(Socket clientSocket = new Socket(endPoint.getHost(), endPoint.getPort())) {

            System.out.println("Establish connection to " + endPoint.getHost() + ":" + endPoint.getPort());

            try (
                    var scanner = new Scanner(System.in);
                    var writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String promptNick = reader.readLine();

                System.out.print(promptNick);

                var nick = scanner.nextLine();

                writer.println(nick);

                while (!clientSocket.isClosed()) {
                    String promptData = reader.readLine();

                    System.out.print(promptData);
                    var line = generateCharacters();
                    //try {
                    //    Thread.sleep(3000);
                    //} catch (InterruptedException e) {
                    //    Thread.currentThread().interrupt();
                    //}
                   //line = scanner.nextLine();
                   //if (line.equalsIgnoreCase("Q")) {
                   //    System.out.println("Done client!");
                   //    break;
                   //}

                    writer.println(line);
                    System.out.println("");
                    System.out.println("Waiting for response...");

                    line = reader.readLine();

                    System.out.println("Received response: " + line);
                }
            }
        }
    }
}
