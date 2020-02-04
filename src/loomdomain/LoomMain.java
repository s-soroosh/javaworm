package loomdomain;

import loomintro.Util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class LoomMain {
    public static void main(String[] args) {
        var took = measure(() -> {
                    try (var scope = Executors.newUnboundedExecutor(Thread.builder().virtual().factory())) {
                        Files
                                .lines(Path.of("/Users/sosarabadani/projects/javaworm/javaworm/src/loomdomain/top10milliondomains.csv"))
                                .skip(1)
                                .map(line -> line.split(",")[1].replaceAll("\"", ""))
                                .limit(200)
                                .forEach(domain -> scope.submit(() -> {
                                    try {
                                        final String hostAddress = InetAddress.getByName(domain).getHostAddress();
                                        System.out.println(hostAddress);
                                    } catch (UnknownHostException e) {
                                        e.printStackTrace();
                                    }

                                }));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );

        System.out.println(Util.nanoToMS(took));
    }

    public static long measure(Supplier<?> supplier) {
        var start = System.nanoTime();
        supplier.get();
        var end = System.nanoTime();
        return end - start;
    }
}
