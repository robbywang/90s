package org.ddu.java.concurrent.disk;

/**
 * Simulate multi threads write data to disk through the DiskResourceManager.
 * The threads are not blocked on the writing operation, so they can go on do
 * other operation.
 */
public class DiskTester {
  private static final int THREAD_NUM = 5;

  public static void main(String[] args) {
    DiskTester tester = new DiskTester();
    tester.writeToDisk();
  }

  public void writeToDisk() {
    for (int i = 0; i < THREAD_NUM; i++) {

      Thread testThread = new Thread("Test Thread " + i) {
        @Override
        public void run() {
          System.out.println(getName() + " request DiskResourceManager to write...");
          DiskResourceManager.getInstance().writeTakePut(getName().getBytes());
          doOhterTask(getName());
        }
      };

      testThread.start();
    }
  }

  private void doOhterTask(String name) {
    System.out.println(name + " do other task.");
  }
}
