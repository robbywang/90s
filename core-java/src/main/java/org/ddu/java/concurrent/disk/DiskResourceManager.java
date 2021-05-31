package org.ddu.java.concurrent.disk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiskResourceManager {

  private ExecutorService executor = null;
  private static final int THREAD_POOL_SIZE = 5;
  private static DiskResourceManager instance = null;
  private int count;

  private DiskResourceManager() {
    executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
  }

  public synchronized static DiskResourceManager getInstance() {
    if (instance == null) {
      instance = new DiskResourceManager();
    }
    return instance;
  }

  public void writePollOffer(final byte[] content) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        // poll() is not a blocked method.
        Disk disk = DiskResources.getDisks().poll();
        while (disk == null) {
          try {
            synchronized (instance) {
              System.out.println("Waitting a Disk...");
              instance.wait();
              disk = DiskResources.getDisks().poll();
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println("Got Disk: " + disk.getLabel());
        disk.write(content);
      }
    };

    executor.execute(r);
  }

  public void writeTakePut(final byte[] content) {

    Runnable r = new Runnable() {
      @Override
      public void run() {
        Disk disk;
        try {
          disk = DiskResources.getDisks().take();
          System.out.println("Got Disk: " + disk.getLabel());
          disk.write2(content);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    executor.execute(r);
  }
}
