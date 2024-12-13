import java.net.*;

public class UDPServer {
    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(12345)) { // створюємо серверний сокет на порту 12345
            byte[] buffer = new byte[1024];
            System.out.println("сервер очікує повідомлення...");

            while (true) {
                // отримуємо дані від клієнта
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(packet); // чекаємо на вхідний пакет
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("отримано від клієнта: " + received);

                // якщо клієнт надсилає "bye", завершуємо роботу
                if ("bye".equalsIgnoreCase(received.trim())) {
                    System.out.println("сервер завершує роботу.");
                    break;
                }

                // відповідаємо клієнту
                String response = "сервер отримав: " + received;
                byte[] responseData = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length,
                        packet.getAddress(), packet.getPort());
                serverSocket.send(responsePacket); // надсилаємо відповідь
            }
        } catch (Exception e) {
            System.err.println("помилка сервера: " + e.getMessage());
        }
    }
}
