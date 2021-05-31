package org.ddu.java.concurrent.pc.impl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.ddu.java.concurrent.pc.ResourcePool;

public class LockPool<E> extends AbstractPool<E> implements ResourcePool<E> {

  final Lock lock = new ReentrantLock();
  final Condition notFull = lock.newCondition();
  final Condition notEmpty = lock.newCondition();

  public LockPool(int size) {
    super(size);
  }

  @Override
  public E get() {
    lock.lock();
    try {
      while (super.isEmpty()) {
        notEmpty.await();
      }
      E e = super.get();
      notFull.signal();
      return e;
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    } finally {
      lock.unlock();
    }
    return null;
  }

  @Override
  public void put(E e) {
    lock.lock();
    try {
      while (super.isFull()) {
        notFull.await();
      }
      super.put(e);
      notEmpty.signal();
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    } finally {
      lock.unlock();
    }
  }
}
