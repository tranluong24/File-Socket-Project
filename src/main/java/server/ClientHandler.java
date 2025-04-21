package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientCommand;
            while ((clientCommand = reader.readLine()) != null) {
                System.out.println("Received command from " + clientSocket.getInetAddress().getHostAddress() + ": " + clientCommand);

                if ("quit".equalsIgnoreCase(clientCommand)) {
                    writer.println("Server: Goodbye!");
                    System.out.println("Client " + clientSocket.getInetAddress().getHostAddress() + " requested quit.");
                    break;
                }
                writer.println("Server received: " + clientCommand);
            }

        } catch (IOException e) {
            System.err.println("Error handling client " + clientSocket.getInetAddress().getHostAddress() + ": " + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                    System.out.println("Client connection closed: " + clientSocket.getInetAddress().getHostAddress());
                }
            } catch (IOException e) {
                System.err.println("Error closing resources for client " + clientSocket.getInetAddress().getHostAddress() + ": " + e.getMessage());
            }
        }
    }
}
