package org.ddu.algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * dfs -> stack OR 递归
 * bfs -> Queue, LinkedList or ArrayDeque
 */
public class TreeTraversal {

  /**
   * 深度优先遍历(Depth First Search)
   *
   *  => 123567489
   */
  public void dfs(Node root) {
    if (root != null) {
      System.out.print(root.value);
      if (root.children != null && !root.children.isEmpty()) {
        for(Node node: root.children) {
          dfs(node);
        }
      }
    }
  }

  public void dfs2(Node root) {
    Stack<Node> stack = new Stack<>();
    if (root != null) {
      stack.add(root);

      while (!stack.isEmpty()) {
        Node node = stack.pop();
        System.out.print(node.value);

        if (node.children != null) {
          for(int i=node.children.size()-1; i>=0; i--) {
            stack.push(node.children.get(i));
          }
        }
      }
    }
  }

  /**
   * 广度优先遍历(Breadth First Search)的主要思想是：类似于树的层序遍历。
   */
  public void bfs(Node root) {
    Queue<Node> q = new ArrayDeque<>();
    if (root != null) {
      q.add(root);


      while(!q.isEmpty()) {
        Node node = q.remove();
        System.out.print(node.value);

        if (node.children != null) {
          q.addAll(node.children);
        }
      }
    }
  }


  public static void main(String[] args) {
    TreeTraversal ins = new TreeTraversal();
    Node root = ins.init();

    System.out.println("------ dfs ------");
//    if (root != null) {
//      System.out.print(root.value);
//    }
    ins.dfs(root);
    System.out.println("\n------ dfs2 ------");
    ins.dfs2(root);

    System.out.println("\n------ bfs ------");
    ins.bfs(root);

    /**
     ------ dfs ------
     123567489
     ------ dfs2 ------
     123567489
     ------ bfs ------
     123456897
     */

  }

  /**
   *      1
   *  2   3   4
   *     5 6  8 9
   *       7
   */
  private Node init() {
    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);
    Node node5 = new Node(5);
    Node node6 = new Node(6);
    Node node7 = new Node(7);
    Node node8 = new Node(8);
    Node node9 = new Node(9);
    node1.children.add(node2);
    node1.children.add(node3);
    node1.children.add(node4);
    node3.children.add(node5);
    node3.children.add(node6);
    node6.children.add(node7);
    node4.children.add(node8);
    node4.children.add(node9);

    return node1;
  }


  class Node {

    public Node(int value) {
      this.value = value;
    }

    int value;
    List<Node> children = new ArrayList<>();
  }

}
