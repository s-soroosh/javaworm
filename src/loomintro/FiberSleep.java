package loomintro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class FiberSleep {
    public static void main(String[] args) throws InterruptedException {
        List<Long> timeSpents = new ArrayList<>(100);
        var jobs = IntStream.range(0, 10000)
                .mapToObj(n -> new Job())
                .collect(toList());

        for (int c = 0; c <= 100; c++) {
            var jobRunner = new FiberBasedJobRunner();
            var timeSpent = jobRunner.run(jobs);
            timeSpents.add(timeSpent);
        }

        Collections.sort(timeSpents);
        System.out.println("Top 10 executions took:");
        timeSpents.stream().limit(10)
                .forEach(timeSpent ->
                        System.out.println("%s ms".formatted(timeSpent))
                );
    }
}
