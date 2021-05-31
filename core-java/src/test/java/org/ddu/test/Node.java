package org.ddu.test;

import java.util.HashMap;
import java.util.Map;

public class Node {

  private Map<Character, Node> children = new HashMap<>();
  private char value;
  private Node next;

  public Node() {
  }

  public Node(char value) {
    this.value = value;
    this.children = new HashMap<>();
  }

  public Map<Character, Node> getChildren() {
    return children;
  }

  public char getValue() {
    return value;
  }

  public Node getNext() {
    return next;
  }

  public void setNext(Node next) {
    this.next = next;
  }

//  @Override
//  public int hashCode() {
//    return new Integer(value).hashCode();
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (obj instanceof Node) {
//      Node anotherNode = (Node) obj;
//      return this.value == anotherNode.getValue();
//    }
//    return false;
//  }
}
