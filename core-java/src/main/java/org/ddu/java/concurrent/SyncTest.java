package org.ddu.java.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncTest {

  private static ExecutorService consumerExecutor = Executors.newFixedThreadPool(1);
  private static ExecutorService producerExecutor = Executors.newFixedThreadPool(1);

//  private static ResourcePool<String> abqResPool = new ABQResPoolImpl<String>(2);
//  private static ResourcePool<String> syncResPool = new SyncResPoolImpl<String>(2);

  private static final int consumerNum = 1;
  private static final int producerNum = 1;

  private Tester tester = new Tester();

  public SyncTest() {
//    tester = new Tester();
  }

  public static void main(String[] args) {
    SyncTest instance = new SyncTest();
//    testRunnalbe(abqResPool);
    instance.testRunnalbe();
  }

  private void testRunnalbe() {
    for (int i = 0; i < consumerNum; i++) {
      Runnable r = new Runnable() {
        @Override
        public void run() {
          try {
            Thread.sleep(5*1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          tester.product();
        }

      };
      producerExecutor.execute(r);
    }

    for (int i = 0; i < consumerNum; i++) {
      Runnable r = new Runnable() {
        @Override
        public void run() {
          tester.consume();
        }
      };
      consumerExecutor.execute(r);
    }
  }

  class Tester {

    Tester() {};

    private Object lck1 = new Object();
    private Object lck2 = new Object();

    public synchronized void product() {
      System.out.println("Product.");
    }

    public synchronized void product1() {
      System.out.println("Product.");
    }

    public void consume() {
      synchronized (lck1) {
        System.out.println("Consume.");
      }
    }
  }

}

