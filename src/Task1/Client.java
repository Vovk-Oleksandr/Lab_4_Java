import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        // підключаємося до сервера на локальній машині, порт 12345
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("підключено до сервера. введіть повідомлення (введіть 'bye' для виходу):");

            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                out.println(userInput); // надсилаємо повідомлення серверу
                String response = in.readLine(); // отримуємо відповідь від сервера
                System.out.println(response);
                if ("до побачення!".equalsIgnoreCase(response)) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("помилка клієнта: " + e.getMessage());
        }
    }
}
