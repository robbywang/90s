package org.ddu.java.concurrent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 1. Future's get() method is will wait the Callable to finish.
 *
 * 2. Runnable can be submit() or execute().
 *
 */
public class CallableTest {

  private static ExecutorService executor = Executors.newFixedThreadPool(5);

  public static void main(String[] args) {
    System.out.println("1. runCallable");
    runCallable();
    System.out.println("2. after runCallable");

    System.out.println("3. runRunnable");
    runRunnable();
    System.out.println("4. after runRunnable");

    // runFutureTask();
  }

  public static void runCallable() {
    Callable<String> c = new Callable<String>() {
      @Override
      public String call() throws Exception {
        System.out.println("Inside the Callable...");
        Thread.sleep(6000);
        return "Callable ret value.";
      }
    };

    final Future<String> f = executor.submit(c);

    // 异步check Further is done.
    Thread t = new Thread() {
      int i;
      @Override
      public void run() {
        while (!f.isDone()) {
          try {
            System.out.println("wait..." + i++);
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        isDone(f);
      }
    };
    t.start();

    System.out.println("Callable state: " + f.isDone());
//    try {
//      // The get method is blocked.
//      System.out.println(f.get());
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    } catch (ExecutionException e) {
//      e.printStackTrace();
//    }

  }

  public static void runRunnable() {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        System.out.println("Inside the Runnable...");
      }
    };

    Future<?> f = executor.submit(r);
    executor.execute(r);
    System.out.println("Callable state: " + f.isDone());
  }

  public static void runFutureTask() {
    FutureTask<String> ft = new FutureTask<String>(new Callable<String>() {
      @Override
      public String call() {
        try {
          Thread.sleep(6*1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        return "FutureTask ret value";
      }
    });

    executor.execute(ft);
    System.out.println("FutureTask state : " +ft.isDone());
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd-MM:ss");
    System.out.println(df.format(new Date()));
    try {
      ft.get();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("FutureTask state : " +ft.isDone());
    System.out.println(df.format(new Date()));
  }

  public static void isDone(Future<String> f) {
    try {
      System.out.println(f.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

}
