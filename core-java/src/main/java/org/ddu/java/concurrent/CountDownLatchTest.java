package org.ddu.java.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Date:     2016年5月1日 下午8:47:56 <br/>
 */
public class CountDownLatchTest {

  private static final int THREAD_NUM = 5;
  private static final CountDownLatch startLatch = new CountDownLatch(1);
  private static CountDownLatch finishedLatch = new CountDownLatch(THREAD_NUM);

  public static void main(String[] args) {

    System.out.println("--- create runner threads ---");

    for (int i=0; i< THREAD_NUM; i++) {
      new Thread("Runner" + String.valueOf(i)) {
        @Override
        public void run() {
          System.out.println(Thread.currentThread().getName() + " is awaiting...");
          try {
            // 等待开始
            startLatch.await();
            System.out.println(Thread.currentThread().getName() + " has finished");
            // 报告自己结束
            finishedLatch.countDown();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }.start();
    }

    System.out.println("--- create commander ---");

    new Thread("Commander thread") {
      @Override
      public void run() {
        System.out.println("-- Ready? Go! --");
        // 发令枪响起
        startLatch.countDown();
        // 等都跑完， 报告成绩
        try {
          finishedLatch.await();
          System.out.println("-- Game is over! --");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }.start();

  }
}
