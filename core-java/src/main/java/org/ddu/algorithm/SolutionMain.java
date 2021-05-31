package org.ddu.algorithm;

import java.util.Arrays;

public class SolutionMain {

  private static int round = 0;

  public static void main(String[] args) {

    // 反转单词.
    String input = "the sky is blue";
    reverseWords(input);

    // 反转链表.
    Node nodeA = new Node("a");
    Node nodeB = new Node("b");
    Node nodeC = new Node("c");
    nodeA.setNext(nodeB);
    nodeB.setNext(nodeC);
    Node root = nodeA;
    printNode(root);

    // 值传递/引用传递.
    Node reverseRoot = reverseLink(root);
    printNode(reverseRoot);

    // fibonacci
    System.out.println("fibonacci1(8) " + fibonacci1(8));
    System.out.println("fibonacci2(8) " + fibonacci2(8));

    Node node = new Node("test");
    System.out.println("Node: " + node.getValue());
    update(node);
    System.out.println("Update Node: " + node.getValue());
    change(node);
    System.out.println("Change Node: " + node.getValue());

    // quickSort
    System.out.println(" --- Quick Sort ---");
    int[] intArray = {5, 4, 8, 2, 9, 1};
    System.out.println("Before sort");
    Arrays.stream(intArray).forEach(System.out::print);
    System.out.println("");
    quickSort(intArray);
    System.out.println("\nAfter sort");
    Arrays.stream(intArray).forEach(System.out::print);

    //
    String hhl = sortHHL("红蓝蓝黄红黄蓝红红黄红");
    System.out.println("HHL: " + hhl);

  }

  private static void reverseWords(String input) {
    String[] strings = input.split(" ");
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = strings.length - 1; i >= 0; i--) {
      stringBuilder.append(strings[i]).append(" ");
    }
    System.out.println(stringBuilder.toString());
  }

  // root
  // |
  // a -> b -> c
  private static Node reverseLink(Node root) {
    if (root.getNext() != null) {
      Node nextNode = root.getNext();
      root.setNext(null);
      return reverseLink(root, nextNode);
    } else {
      return root;
    }
  }

  // 1,1,2,3,5,8,13,21...
  private static int fibonacci1(int n) {
    if (n <= 2) {
      return 1;
    } else {
      return fibonacci1(n - 1) + fibonacci1(n - 2);
    }
  }

  private static int fibonacci2(int n) {
    if (n <= 2) {
      return 1;
    } else {
      int a = 1;
      int b = 1;
      int i = 3;
      while (i < n) {
        int temp = b;
        b = a + b;
        a = temp;
        i++;
      }
      return a + b;
    }
  }

  private static void quickSort(int[] chars) {
    int low = 0;
    int high = chars.length - 1;
    quickSort(chars, low, high);
  }

  /**
   * {5, 4, 8, 2, 9, 1};
   */
  private static void quickSort(int[] chars, int l, int h) {
    StringBuilder sb = new StringBuilder();
    Arrays.stream(chars).forEach(sb::append);
    System.out.println(
        String.format("Round %s, chars: %s, low: %s, high: %s", round++, sb.toString(), l, h));
    if (l < h) {
      int temp = chars[l];
      int low = l;
      int high = h;
      while (low < high) {

        while (chars[high] > temp && high > low) {
          high--;
        }
        chars[low] = chars[high];

        while (chars[low] < temp && low < high) {
          low++;
        }

        chars[high] = chars[low];
      }

      chars[low] = temp;
      quickSort(chars, l, low - 1);
      quickSort(chars, low + 1, h);
    }
  }

  /**
   *  数组中球的顺序为:黄、红、蓝
   *
   *  例如：红蓝蓝黄红黄蓝红红黄红
   * 排序后为：黄黄黄红红红红红蓝蓝蓝
   */
  private static String sortHHL(String input) {
    char[] inputArray = input.toCharArray();

    int i = 0;

    //  第一趟， 找出黄色的
    for (int j=0; j < inputArray.length; j++) {
      if (inputArray[j] == '黄') {
        char temp = inputArray[i];
        inputArray[i] = inputArray[j];
        inputArray[j] = temp;
        i++;
      }
    }

    //  第二趟， 找出红色的
    for (int j=i; j < inputArray.length; j++) {
      if (inputArray[j] == '红') {
        char temp = inputArray[i];
        inputArray[i] = inputArray[j];
        inputArray[j] = temp;
        i++;
      }
    }

    //  第三趟， 找出蓝色的
    for (int j=i; j < inputArray.length; j++) {
      if (inputArray[j] == '蓝') {
        char temp = inputArray[i];
        inputArray[i] = inputArray[j];
        inputArray[j] = temp;
        i++;
      }
    }

    return String.valueOf(inputArray);
  }

  // -------------

  // root
  // |
  // a -> b -> c
  private static Node reverseLink(Node root, Node nextNode) {
    if (root != null && nextNode != null) {
      Node nextNextNode = nextNode.getNext();
      nextNode.setNext(root);
      root = nextNode;

      return reverseLink(root, nextNextNode);
    }
    return root;
  }

  private static void printNode(Node root) {
    StringBuilder sb = new StringBuilder();
    Node cur = root;
    while (cur != null) {
      sb.append(cur.getValue());
      cur = cur.getNext();
      if (cur != null) {
        sb.append(" -> ");
      }
    }

    System.out.println(sb.toString());
  }

  private static class Node {

    private String value;
    private Node next;

    public Node(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public Node getNext() {
      return next;
    }

    public void setNext(Node next) {
      this.next = next;
    }
  }

  private static void update(Node node) {
    node.value = "Changed";
  }

  private static void change(Node node) {
    Node node1 = new Node("Updated");
    node = node1;
  }

}
