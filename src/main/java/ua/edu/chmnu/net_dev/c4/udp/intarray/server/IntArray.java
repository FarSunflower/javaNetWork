package ua.edu.chmnu.net_dev.c4.udp.intarray.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class IntArray {

    private final static int DEFAULT_PORT = 6710;
    private final static int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port. Using default: " + DEFAULT_PORT);
            }
        }

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP Server listening on port " + port);

            while (true) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Receive a packet
                socket.receive(packet);

                // Process the packet in a separate thread
                new Thread(() -> processPacket(socket, packet)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processPacket(DatagramSocket socket, DatagramPacket packet) {
        try {
            String receivedData = new String(packet.getData(), 0, packet.getLength()).trim();
            System.out.println("Received data: " + receivedData);

            // Process the input to calculate Min, Max, Avg
            String response = MaxMinAvg(receivedData);

            // Send response
            byte[] responseData = response.getBytes();
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
            socket.send(responsePacket);

            System.out.println("Response sent: " + response);
        } catch (Exception e) {
            e.printStackTrace();
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

            return String.format("Min: %d, Max: %d, Avg: %.2f", min, max, avg);
        } catch (Exception e) {
            return "Invalid input. Please provide a space-separated list of integers.";
        }
    }
}
