package org.ddu.algorithm.amazon;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
public class MinStack {

  //维持最小元素
  private PriorityQueue<Integer> queue = null;
  // 栈
  Stack<Integer> stack = null;



  /** initialize your data structure here. */
  public MinStack() {
    queue = new PriorityQueue<>(new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        if (o1 >= o2) {
          return 1;
        } else {
          return -1;
        }
      }
    });
    stack = new Stack<>();
  }

  public void push(int x) {
    queue.add(x);
    stack.push(x);
  }

  public void pop() {
    int p = stack.pop();
    queue.remove(p);
  }

  public int top() {
    return stack.peek();

  }

  public int getMin() {
    return queue.peek();
  }

  public static void main(String[] args) {

    MinStack minStack = new MinStack();

    minStack.push(2147483646);
    minStack.push(2147483646);
    minStack.push(2147483647);

    System.out.println(minStack.top());
    minStack.pop();
    System.out.println(minStack.getMin());
    minStack.pop();
    System.out.println(minStack.getMin());
    minStack.pop();
    minStack.push(2147483647);
    System.out.println(minStack.top());
    System.out.println(minStack.getMin());
    minStack.push(-2147483648);
    System.out.println(minStack.top());
    System.out.println(minStack.getMin());

    System.out.println("---");
    System.out.println(Integer.MAX_VALUE);
    System.out.println(Integer.MIN_VALUE);



  }


}


