package loomintro;

import java.util.List;
import java.util.concurrent.Executors;

public class FiberBasedJobRunner implements JobRunner {
    @Override
    public long run(List<Job> jobs) {
        var start = System.nanoTime();
        var factory = Thread.builder().name("fiber").virtual().factory();

        try (var executor = Executors.newUnboundedExecutor(factory)) {
            for (Job job : jobs) {
                executor.submit(job::doIt);
            }
        }

        var end = System.nanoTime();
        long timeSpentInMS = Util.nanoToMS(end - start);
        System.out.println(
                "Running %s jobs took  %s ms".
                        formatted(jobs.size(), timeSpentInMS)
        );

        return timeSpentInMS;
    }
}
