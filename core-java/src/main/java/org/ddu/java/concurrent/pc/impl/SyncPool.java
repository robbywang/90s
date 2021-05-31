package org.ddu.java.concurrent.pc.impl;

import org.ddu.java.concurrent.pc.ResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncPool<E> extends AbstractPool<E> implements ResourcePool<E> {

  private static final Logger logger = LoggerFactory.getLogger(SyncPool.class);

  public SyncPool(int size) {
    super(size);
  }

  @Override
  public synchronized E get() {
    String threadName = Thread.currentThread().getName();
    E e = null;
    // Tips:  use while instead of if to double check the locking state.
    while (this.isEmpty()) {
      try {
        logger.info("Pool is empty, [ {} ] is wait...", threadName);
        this.wait();
        logger.info(" [ {} ] is weakup: {}", threadName);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
    }
    e = super.get();
    logger.info("Got : {}", e);

    this.notifyAll();

    return e;
  }

  @Override
  public synchronized void put(E e) {
    String threadName = Thread.currentThread().getName();
    while (this.isFull()) {
      try {
        logger.info("Pool is full, [ {} ] wait...", threadName);
        this.wait();
        logger.info(" [ {} ] is weakup: {}", threadName);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
    }

    super.put(e);
    logger.info("Put : {}", e);
    this.notifyAll();
  }
}
