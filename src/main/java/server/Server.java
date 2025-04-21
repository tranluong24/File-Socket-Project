package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import server.ClientHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 5000;
    private static final int MAX_CLIENTS = 10;
    private static ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);

    public static void main(String[] args) throws IOException {
        System.out.println("File sever on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for a client connection...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler handler = new ClientHandler(clientSocket);

                pool.execute(handler);
            }
        } catch (IOException e) {
            System.err.println("Error handling client connection: " + e.getMessage());
        }finally {
            pool.shutdown();
            System.out.println("Server shutting down thread pool.");
        }
    }
}

