package loomdomain;

import loomintro.Util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public class SingleMain {
    public static void main(String[] args) {
        var took = measure(() -> {
                    try {
                        Files
                                .lines(Path.of("/Users/sosarabadani/projects/javaworm/javaworm/src/loomdomain/top10milliondomains.csv"))
                                .skip(1)
                                .map(line -> line.split(",")[1].replaceAll("\"", ""))
                                .map(domain -> {
                                    try {
                                        return InetAddress.getByName(domain).getHostAddress();
                                    } catch (UnknownHostException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                })
                                .limit(200)
                                .forEach(System.out::println);
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
