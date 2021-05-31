package org.ddu.java.dynamicproxy;

public class TargetServiceImpl implements TargetService {

  @Override
  public void add() {
    System.out.println("--------------------add---------------");
  }
}
