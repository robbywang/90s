package org.ddu.test;

import org.junit.Test;

public class NodeTest {

  @Test
  public void test() {
    Node root = new Node();
    insert(root, "abcd");
    insert(root, "abac");
    insert(root, "abcc");

    System.out.println(root.toString());
    prefixWith(root, "ab");
    prefixWith(root, "abca");

  }

  private void insert(Node pNode, String words) {
    Node cursor = pNode;
    char[] chars = words.toCharArray();
    for (int i = 0; i <= chars.length - 1; i++) {
      char currentChar = chars[i];

      if (cursor.getChildren().keySet().contains(currentChar)) {
        cursor = cursor.getChildren().get(currentChar).getNext();

      } else {
        Node currentNode = new Node(currentChar);
        cursor.getChildren().put(currentChar, currentNode);
        currentNode.setNext(currentNode);
        cursor = currentNode;
      }
    }
  }

  public boolean prefixWith(Node root, String word) {
    Node cursor = root;
    char[] chars = word.toCharArray();
    int count = 0;
    for (int i = 0; i < chars.length; i++) {
      count++;
      char currentChar = chars[i];
      if (cursor.getChildren() != null && cursor.getChildren().keySet().contains(currentChar)) {
        cursor = cursor.getChildren().get(currentChar);
      } else {
        return false;
      }
    }
    return true;
  }

}
