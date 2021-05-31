package org.ddu.algorithm.amazon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class BinaryTree {

  public List<List<Integer>> levelOrder(TreeNode root) {
    if (root == null) {
      return new ArrayList<>();
    }

    List<List<Integer>> result = new ArrayList<>();
    Map<Integer, List<Integer>> levelMap = new TreeMap<>();
    Deque<TreeNodeLevel> queue = new ArrayDeque<>();

    queue.add(new TreeNodeLevel(0, root));

    while (!queue.isEmpty()) {
      TreeNodeLevel treeNodeLevel = queue.removeFirst();
      TreeNode treeNode = treeNodeLevel.getTreeNode();
      int level = treeNodeLevel.getLevel();
      levelMap.computeIfAbsent(level, k -> new ArrayList<Integer>())
          .add(treeNodeLevel.getTreeNode().val);

      if (treeNode.left != null) {
        queue.add(new TreeNodeLevel(level + 1, treeNode.left));
      }

      if (treeNode.right != null) {
        queue.add(new TreeNodeLevel(level + 1, treeNode.right));
      }

    }

    result.addAll(levelMap.values());

    return result;
  }

  /**
   * 给定二叉树 [3,9,20,null,null,15,7],
   *
   *     3
   *    / \
   *   9  20
   *     /  \
   *    15   7
   * 返回其自底向上的层次遍历为：
   *
   * [
   *   [15,7],
   *   [9,20],
   *   [3]
   * ]
   *
   */
  public List<List<Integer>> levelOrderBottom(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) {
      return  result;
    }

    Queue<TreeNode> queue = new LinkedList();
    queue.add(root);

    while (!queue.isEmpty()) {
      List<Integer> level = new ArrayList<>();
      int size = queue.size();
      for (int i=0; i< size; i++) {
        TreeNode treeNode = queue.poll();
        level.add(treeNode.val);
        if (treeNode.left != null) {
          queue.add(treeNode.left);
        }
        if (treeNode.right != null) {
          queue.add(treeNode.right);
        }
      }

      result.add(level);

    }

    Collections.reverse(result);
    return result;
  }



  int minDepth = Integer.MAX_VALUE;

  public int minDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }

    depth(root, 1);
    return minDepth;
  }

  private void depth(TreeNode treeNode, int depth) {
    if (treeNode.left == null && treeNode.right == null) {
      if (depth < minDepth) {
        minDepth = depth;
      }
    } else {
      if (treeNode.left != null) {
        depth(treeNode.left, depth + 1);
      }
      if (treeNode.right != null) {
        depth(treeNode.right, depth + 1);
      }
    }
  }


  class TreeNodeLevel {

    private int level;
    private TreeNode treeNode;

    TreeNodeLevel(int level, TreeNode treeNode) {
      this.level = level;
      this.treeNode = treeNode;
    }

    public int getLevel() {
      return level;
    }

    public void setLevel(int level) {
      this.level = level;
    }

    public TreeNode getTreeNode() {
      return treeNode;
    }

    public void setTreeNode(TreeNode treeNode) {
      this.treeNode = treeNode;
    }
  }

  class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
}
