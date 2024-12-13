import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket(); // створюємо клієнтський сокет
             Scanner scanner = new Scanner(System.in)) {

            InetAddress serverAddress = InetAddress.getByName("localhost"); // адреса сервера
            int serverPort = 12345; // порт сервера
            System.out.println("введіть повідомлення для сервера (введіть 'bye' для виходу):");

            while (true) {
                // читаємо повідомлення з консолі
                String message = scanner.nextLine();
                byte[] messageData = message.getBytes();

                // надсилаємо повідомлення серверу
                DatagramPacket packet = new DatagramPacket(messageData, messageData.length, serverAddress, serverPort);
                clientSocket.send(packet);

                if ("bye".equalsIgnoreCase(message.trim())) {
                    System.out.println("клієнт завершує роботу.");
                    break;
                }

                // отримуємо відповідь від сервера
                byte[] buffer = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(responsePacket); // чекаємо на відповідь
                String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
                System.out.println(response);
            }
        } catch (Exception e) {
            System.err.println("помилка клієнта: " + e.getMessage());
        }
    }
}
