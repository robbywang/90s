package org.ddu.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;

public class JavaStackAndQueue {

  private static Deque<Integer> deque = new ArrayDeque<>(10);

  public static void main(String[] args) {

    System.out.println("Stack");
    in();
    useAsStack();

    System.out.println("Queue");
    in();
    useAsQueue();

  }


  private static void in() {
    for (int i=0; i< 10; i++) {
      System.out.print(i);
      deque.add(i);
    }
  }

  private static void useAsStack() {
    System.out.print(" LIFO -> ");
    while(!deque.isEmpty()) {
      System.out.print(deque.pollLast());
    }
  };

  private static void useAsQueue() {
    System.out.print(" FIFO -> ");
    while(!deque.isEmpty()) {
      System.out.print(deque.pollFirst());
    }
  };


  public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();

    for (char c: s.toCharArray()) {
      if (c == '{' || c == '[' || c == '(' ) {
        stack.addLast(c);
      } else {

        if (stack.size() == 0) {
          return false;
        }

        Character top = stack.removeLast();

        if (top == null) {
          return false;
        }
        if (top == '{' && c != '}') {
          return false;
        }
        if (top == '[' && c != ']') {
          return false;
        }
        if (top == '(' && c != ')') {
          return false;
        }

      }
    }

    if (stack.size() == 0) {
      return true;
    }

    return false;
  }

}
