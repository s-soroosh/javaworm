import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;

public class ClassicWebServer {

    public ClassicWebServer() {
        var executor = Executors.newCachedThreadPool(Thread.builder().factory());
        System.out.println("Webserver Started");
        try (ServerSocket serverSocket = new ServerSocket(80)) {
            while (true) {
//                System.out.println("Waiting for client request");
                Socket remote = serverSocket.accept();
//                System.out.println("Connection made");
                executor.submit(new ClientHandler(remote));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new ClassicWebServer();
    }

    public static class ClientHandler implements Runnable {

        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
//            System.out.println("\nClientHandler Started for " +
//                    this.socket);
            handleRequest(this.socket);
//            System.out.println("ClientHandler Terminated for "
//                    + this.socket + "\n");
        }

        public void handleRequest(Socket socket) {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));) {
                String headerLine = in.readLine();
                StringTokenizer tokenizer =
                        new StringTokenizer(headerLine);
                String httpMethod = tokenizer.nextToken();
                socket.getOutputStream().write("""
                        HTTP/1.1 200 OK
                        Date: Mon, 27 Jul 2009 12:28:53 GMT
                        Server: Apache/2.2.14 (Win32)
                        Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT
                        Content-Length: 4
                        Content-Type: text/html
                        Connection: Closed

                        """.getBytes());
                socket.getOutputStream().write("\n".getBytes());
                socket.getOutputStream().write(httpMethod.getBytes());
                socket.getOutputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
