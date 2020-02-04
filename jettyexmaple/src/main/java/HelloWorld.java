import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.ThreadPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HelloWorld extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        try {
            Thread.sleep(50l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello World</h1>");
    }

    public static void main(String[] args) throws Exception {
        var threadpool = new ThreadPool() {

            private ExecutorService pool = Executors.newUnboundedExecutor(Thread.builder().virtual().factory());

            @Override
            public void execute(Runnable command) {
                pool.submit(command);
            }

            @Override
            public void join() throws InterruptedException {
                pool.awaitTermination(1, TimeUnit.DAYS);
            }

            @Override
            public int getThreads() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getIdleThreads() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isLowOnThreads() {
                return false;
            }
        };
//        Server server0 = new Server(threadpool);
//        final ServerConnector connector = new ServerConnector(server0);
//        connector.setPort(8080);
//        server0.addConnector(connector);
//        server0.setHandler(new HelloWorld());
//        server0.start();
//        server0.join();


        Server server1 = new Server(8080);
        server1.setHandler(new HelloWorld());
        server1.start();
        server1.join();
    }
}
