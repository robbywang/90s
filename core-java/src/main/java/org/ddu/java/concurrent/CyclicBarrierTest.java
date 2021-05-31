package org.ddu.java.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Date:     2016年5月1日 下午7:21:22 <br/>
 * @author   wangtao
 */
public class CyclicBarrierTest {

  private static final int THREAD_NUM = 5;

  public static void main(String[] args) {
    test();
  }

  static void test() {
    final CyclicBarrier barrier =
        new CyclicBarrier(THREAD_NUM, new CyclicBarrierTest().new BarrierAction());

    for (int i = 0; i < THREAD_NUM; i++) {
      new Thread(String.valueOf(i)) {
        @Override
        public void run() {
          try {
            System.out.println("Thread: " + Thread.currentThread().getName());
            barrier.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (BrokenBarrierException e) {
            e.printStackTrace();
          }
        }
      }.start();
    }
  }

  class BarrierAction implements Runnable {

    @Override
    public void run() {
      System.out.println("All Thread finished.");
    }
  }
}
