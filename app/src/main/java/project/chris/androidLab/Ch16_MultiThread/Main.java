package project.chris.androidLab.Ch16_MultiThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] arg) {
//        thread();
//        runnable();
//        threadFactory();
//        executor();
        callable();
    }

    private static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "callable";
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(callable);
        try {
            String result = future.get();
            System.out.print("method : " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.print("executor");
            }
        };
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

    }

    private static void threadFactory() {

    }

    private static void runnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.print("runnable");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.print("thread");
            }
        };
        thread.start();
        thread.stop();
        thread.interrupt();
    }

}
