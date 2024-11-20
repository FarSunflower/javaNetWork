package ua.edu.chmnu.net_dev.c4.udp.intarray.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class IntArray {

    private final static int DEFAULT_PORT = 6710;
    private final static int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = DEFAULT_PORT;

        if (args.length > 0) {
            serverHost = args[0];
            if (args.length > 1) {
                try {
                    serverPort = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid port. Using default: " + DEFAULT_PORT);
                }
            }
        }

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(serverHost);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Connected to UDP server at " + serverHost + ":" + serverPort);

            while (true) {
                System.out.print("Enter an array of numbers (space-separated, Q to quit): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("Q")) {
                    System.out.println("Exiting...");
                    break;
                }

                // Send the input to the server
                byte[] sendData = input.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                socket.send(sendPacket);

                // Prepare to receive the server's response
                byte[] receiveData = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                System.out.println("Waiting for response...");
                socket.receive(receivePacket);

                // Display the server's response
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server response: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
