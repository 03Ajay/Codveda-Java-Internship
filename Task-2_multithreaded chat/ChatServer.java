import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clientHandlers = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("[Server] Chat server started on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                clientHandlers.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("[Server] Error: " + e.getMessage());
        }
    }

    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler handler : clientHandlers) {
            if (handler != sender) {
                handler.sendMessage(message);
            }
        }
    }

    static void removeClient(ClientHandler handler) {
        clientHandlers.remove(handler);
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Enter your name:");
                clientName = in.readLine();
                System.out.println("[Server] " + clientName + " connected.");
                broadcast("[Server] " + clientName + " has joined the chat.", this);
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("/quit")) {
                        break;
                    }
                    String formatted = clientName + ": " + message;
                    System.out.println(formatted);
                    broadcast(formatted, this);
                }
            } catch (IOException e) {
                System.err.println("[Server] Error with client: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
                removeClient(this);
                broadcast("[Server] " + clientName + " has left the chat.", this);
                System.out.println("[Server] " + clientName + " disconnected.");
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}
