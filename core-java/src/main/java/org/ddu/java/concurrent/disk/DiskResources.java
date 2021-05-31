package org.ddu.java.concurrent.disk;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DiskResources {

  private static BlockingQueue<Disk> disks =
      new LinkedBlockingDeque<Disk>();

  static {
    disks.add(new Disk("A"));
    disks.add(new Disk("B"));
    disks.add(new Disk("C"));
  }

  public static BlockingQueue<Disk> getDisks() {
    return disks;
  }
}
