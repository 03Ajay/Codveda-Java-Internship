import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            // Thread to read messages from server
            Thread readerThread = new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("[Client] Disconnected from server.");
                }
            });
            readerThread.setDaemon(true);
            readerThread.start();

            // Main thread sends user input to server
            String input;
            while ((input = userInput.readLine()) != null) {
                out.println(input);
                if (input.equalsIgnoreCase("/quit")) {
                    break;
                }
            }
            System.out.println("[Client] Exiting chat.");
        } catch (IOException e) {
            System.err.println("[Client] Error: " + e.getMessage());
        }
    }
}
