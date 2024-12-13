import java.net.http.*;
import java.net.URI;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Scanner;

public class HttpClientConsoleApp {
    public static void main(String[] args) {
        // створюємо http-клієнт
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10)) // час очікування підключення
                .build();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("введіть url для запиту:");
            String url = scanner.nextLine(); // введення url користувачем

            System.out.println("виберіть тип запиту (GET або POST):");
            String method = scanner.nextLine().toUpperCase(); // вибір методу запиту

            if ("GET".equals(method)) {
                // виконання GET-запиту
                HttpRequest getRequest = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .GET()
                        .build();
                HttpResponse<String> getResponse = client.send(getRequest, BodyHandlers.ofString());

                // обробка відповіді GET
                System.out.println("статус код: " + getResponse.statusCode());
                System.out.println("заголовки: " + getResponse.headers());
                System.out.println("тіло відповіді: " + getResponse.body());

            } else if ("POST".equals(method)) {
                System.out.println("введіть дані для POST-запиту (у форматі JSON):");
                String requestBody = scanner.nextLine(); // введення даних для POST

                // виконання POST-запиту
                HttpRequest postRequest = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .POST(BodyPublishers.ofString(requestBody))
                        .header("Content-Type", "application/json")
                        .build();
                HttpResponse<String> postResponse = client.send(postRequest, BodyHandlers.ofString());

                // обробка відповіді POST
                System.out.println("статус код: " + postResponse.statusCode());
                System.out.println("заголовки: " + postResponse.headers());
                System.out.println("тіло відповіді: " + postResponse.body());

            } else {
                System.out.println("невідомий тип запиту. будь ласка, виберіть GET або POST.");
            }
        } catch (Exception e) {
            // обробка виключень
            System.err.println("сталася помилка: " + e.getMessage());
        }
    }
}
