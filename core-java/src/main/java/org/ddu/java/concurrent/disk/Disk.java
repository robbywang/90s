package org.ddu.java.concurrent.disk;

/**
 * The Disk represents a really disk.
 * It knows how to write the data to disk.
 */
public class Disk {

  private String label = null;

  public Disk(String label) {
    this.label = label;
  }

  public void write(byte[] b) {
    System.out.println(String.format("Disk %s writing: %s", getLabel(), new String(b)));

    // simulator the write operation.
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Finished writting, return the Disk to Queue.
    DiskResources.getDisks().offer(this);
    System.out.println("Return Disk: " + getLabel());

    synchronized (DiskResourceManager.getInstance()) {
     //notify waitting thread to poll Disk from Queue.
      DiskResourceManager.getInstance().notifyAll();
    }
  }

  public void write2(byte[] b) {
    System.out.println(String.format("Disk %s writing: %s", getLabel(), new String(b)));
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      DiskResources.getDisks().put(this);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("Return Disk: " + getLabel());
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
