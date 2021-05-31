package org.ddu.java.concurrent.pc.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.ddu.java.concurrent.pc.ResourcePool;

/**
 * 用ArrayBlockingQueue的 blocked method take() & put() 来实现同步。
 */
public class ABQPool<E> implements ResourcePool<E> {

  private BlockingQueue<E> resource;

  public ABQPool(final int size) {
    resource = new ArrayBlockingQueue<E>(size);
  }

  @Override
  public E get() {
    try {
      // Blocked method.
      return this.resource.take();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void put(E e) {
    try {
      this.resource.put(e);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
  }

}
