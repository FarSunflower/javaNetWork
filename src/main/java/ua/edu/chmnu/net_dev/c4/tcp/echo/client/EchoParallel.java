package ua.edu.chmnu.net_dev.c4.tcp.echo.client;

import ua.edu.chmnu.net_dev.c4.tcp.core.client.EndPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class EchoParallel implements Runnable {
    private final static int DEFAULT_PORT = 6710;
    private final EndPoint endPoint;
    private final String nickname;

    public EchoParallel(EndPoint endPoint, String nickname) {
        this.endPoint = endPoint;
        this.nickname = nickname;
    }

    private static String generateCharacters() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public void run() {
        try (Socket clientSocket = new Socket(endPoint.getHost(), endPoint.getPort());
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.println("[" + nickname + "] Connected to server at " + endPoint.getHost() + ":" + endPoint.getPort());

            // Send nickname to the server
            writer.println(nickname);

            while (!clientSocket.isClosed()) {
                // Record the start time
                long startTime = System.nanoTime();
                for (int i = 0; i < 5; i++) { // Sends 5 messages
                    String line = generateCharacters();



                    writer.println(line);
                    System.out.println("[" + nickname + "] Sent: " + line);

                    String response = reader.readLine();

                    // Record the end time


                    System.out.println("[" + nickname + "] Received: " + response);


                    Thread.sleep(1000); // Simulate client activity delay
                }
                    String line = "Q";
                if (line.equalsIgnoreCase("Q")) {
                    System.out.println("Done client!");
                    System.out.println("[" + nickname + "] Sent: Quit (disconnect request)");
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1_000_000_000; // Convert to milliseconds
                    System.out.println("[" + nickname + "] Processing time: " + duration + " s");
                    break;
                }
                // Send "Q" to indicate the client wants to exit


            }

        } catch (IOException | InterruptedException e) {
            System.err.println("[" + nickname + "] Connection error: " + e.getMessage());
        }
    }
}
