import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        // створюємо серверний сокет на порту 12345
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("сервер запущений, очікується підключення клієнтів...");

            // приймаємо підключення клієнта
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                System.out.println("клієнт підключився!");
                String clientMessage;

                // читаємо повідомлення від клієнта і надсилаємо відповідь
                while ((clientMessage = in.readLine()) != null) {
                    System.out.println("отримано від клієнта: " + clientMessage);
                    if ("bye".equalsIgnoreCase(clientMessage)) {
                        out.println("до побачення!");
                        break;
                    }
                    out.println("сервер відповідає: " + clientMessage); // повертаємо те саме повідомлення
                }

                System.out.println("клієнт відключився.");
            }
        } catch (IOException e) {
            System.err.println("помилка сервера: " + e.getMessage());
        }
    }
}
