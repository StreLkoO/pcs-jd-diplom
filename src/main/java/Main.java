import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static final int PORT = 8989;
    public static final String PDFSPATH = "pdfs";

    public static void main(String[] args) {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File(PDFSPATH));
        JsonWriter jsonWriter = JsonWriter.get();
        //System.out.println(engine.search("бизнес"));

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream())
                ) {
                    System.out.println("Установлено новое соединение");
                    String input = in.readLine();
                    System.out.println("Клиент прислал сообщение: \"" + input + "\"");
                    List<PageEntry> list = engine.search(input);
                    String result = jsonWriter.listToJSON(list);
                    out.println(result);
                    System.out.println("Клиенту отправлен результат: " + result);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }
}