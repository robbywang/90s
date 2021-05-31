package org.ddu.java.concurrent.pc.impl;

import org.ddu.java.concurrent.pc.ResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractPool<E> implements ResourcePool<E> {

  private Object[] items = null;
  int putptr, takeptr, count;

  private static final Logger logger = LoggerFactory.getLogger(LockPool.class);

  public AbstractPool(int size) {
    items = new Object[size];
  }

  public boolean isFull() {
    if (count == items.length) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isEmpty() {
    if (count == 0) {
      return true;
    } else {
      return false;
    }
  }

  public int nextIndex(int index) {
    int nextIndex = ++index;
    if (nextIndex == items.length) {
      return 0;
    } else {
      return nextIndex;
    }
  }

  @Override
  public E get() {
    Object x = items[takeptr];
    if (++takeptr == items.length) {
      takeptr = 0;
    }
    --count;
    return (E) x;
  }

  @Override
  public void put(E e) {
    items[putptr] = e;
    if (++putptr == items.length) {
      putptr = 0;
    }
    ++count;
  }
}
