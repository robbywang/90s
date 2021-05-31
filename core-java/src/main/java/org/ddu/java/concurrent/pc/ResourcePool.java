package org.ddu.java.concurrent.pc;

public interface ResourcePool<E> {
  E get();

  void put(E e);
}
