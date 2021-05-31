package org.ddu.java.concurrent.pc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ddu.java.concurrent.pc.impl.ABQPool;
import org.ddu.java.concurrent.pc.impl.RunnableConsumer;
import org.ddu.java.concurrent.pc.impl.RunnableProducer;
import org.ddu.java.concurrent.pc.impl.SyncPool;

public class MainApp {

  private static ExecutorService consumerExecutor = Executors.newFixedThreadPool(1);
  private static ExecutorService producerExecutor = Executors.newFixedThreadPool(1);

  private static ResourcePool<String> abqResPool = new ABQPool<String>(2);
  private static ResourcePool<String> syncResPool = new SyncPool<String>(2);

  private static final int consumerNum = 1;
  private static final int producerNum = 1;

  public static void main(String[] args) {
//    testRunnalbe(abqResPool);
    testRunnalbe(syncResPool);
  }

  private static void testRunnalbe(ResourcePool<String> resPool) {
    for (int i = 0; i < consumerNum; i++) {
      RunnableConsumer consumer = new RunnableConsumer(resPool, i);
      consumerExecutor.execute(consumer);
    }

    for (int i = 0; i < producerNum; i++) {
      RunnableProducer producer = new RunnableProducer(resPool, i);
      producerExecutor.execute(producer);
    }
  }


}
