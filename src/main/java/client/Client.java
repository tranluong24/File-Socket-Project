package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Connecting to server at " + SERVER_ADDRESS + ":" + SERVER_PORT);

        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            System.out.println("Connected to server.");
            System.out.println("Type 'quit' to exit.");

            String messageToSend;

            while (true) {
                System.out.print("Enter message: ");
                messageToSend = scanner.nextLine();


                writer.println(messageToSend);

                String serverResponse = reader.readLine();
                if (serverResponse != null) {
                    System.out.println("Received from server: " + serverResponse);
                }

                if ("quit".equalsIgnoreCase(messageToSend)) {
                    break;
                }

            }

        } catch (IOException e) {
            System.err.println("Error connecting to server or during communication: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("Client disconnected.");
        }


    }
}
