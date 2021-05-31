package org.ddu;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncTest {

  private static final Object objLock = new Object();
  private static final Lock reentrantLock = new ReentrantLock();

  public static void main(String[] args) throws InterruptedException {

    Thread t1 = new Thread("t1") {
      int i = 0;

      public void run() {
        synchronized (objLock) {
          while (true) {
            System.out.println("t1: " + i++);
            try {
              Thread.sleep(5000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    };

    Thread t11 = new Thread("t11") {
      int i = 0;

      public void run() {
        reentrantLock.lock();
        while (true) {
          System.out.println("t11: " + i++);
          try {
            Thread.sleep(5000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };

    Thread t2 = new Thread("t2") {
      public void run() {
        System.out.println("t2 Start ");
        synchronized (objLock) {
          System.out.println("t2 get Lock ");
          try {
            Thread.sleep(15000);
          } catch (InterruptedException e) {
            System.out.println("t2 Interrupted");
//            e.printStackTrace();
          }

          System.out.println("t2 Finished");
        }
      }
    };

    Thread t22 = new Thread("t22") {
      public void run() {
        System.out.println("t22 Start ");
//        reentrantLock.lock();
        try {
          reentrantLock.lockInterruptibly();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("t22 get Lock ");
        try {
          Thread.sleep(15000);
        } catch (InterruptedException e) {
          System.out.println("t22 Interrupted");
//          e.printStackTrace();
        }

        System.out.println("t22 Finished");
      }
    };

    t1.start();
    Thread.sleep(100);
    t11.start();
    Thread.sleep(100);
    t2.start();
    Thread.sleep(100);
    t22.start();
    Thread.sleep(100);

//    t2.interrupt();
//    t22.interrupt();


  }
}
