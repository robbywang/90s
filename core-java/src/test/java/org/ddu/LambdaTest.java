package org.ddu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LambdaTest {

  public static void main(String[] args) {
    Listener listener = new Listener();
    test(listener);
    test(() -> System.out.println("yes"));

    List list = new ArrayList();

//    Collections.sort(list, (s1, s2) -> s1.);
  }

  public static void test(IListener listener) {
    listener.print();
  }
}

interface IListener {
  void print();
}

class Listener implements IListener {

  @Override
  public void print() {
    System.out.println("test");
  }
}



