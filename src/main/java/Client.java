import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final String HOST = "localhost";
    public static final int PORT = 8989;

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Установлено соединение с сервером");
            System.out.println("Введите слово для поиска");
            out.println(scanner.nextLine());
            System.out.println("Сервер прислал результат");
            System.out.println(in.readLine());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
