package loomintro;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadBasedJobRunner implements JobRunner {
    @Override
    public long run(List<Job> jobs) {
        var start = System.nanoTime();
        var executor = Executors.newCachedThreadPool();

        for (Job job : jobs) {
            executor.submit(job::doIt);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
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
