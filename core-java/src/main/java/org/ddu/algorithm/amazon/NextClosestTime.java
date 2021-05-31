package org.ddu.algorithm.amazon;

import java.util.HashSet;
import java.util.Set;

public class NextClosestTime {

  //"19:34"
  public String nextClosestTime(String time) {
    return null;
  }


  //
  private Set<Integer> set;
  private int target;
  public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
    if (root1 == null || root2 == null) {
      return false;
    }

    this.target = target;
    set = new HashSet<>();

    addNodeToSet(root1);

    return iterateTreeNode(root2);
  }
  private void addNodeToSet(TreeNode treeNode) {
    if (treeNode != null) {
      set.add(treeNode.val);
      addNodeToSet(treeNode.left);
      addNodeToSet(treeNode.right);
    }
  }
  private boolean iterateTreeNode(TreeNode treeNode) {
    boolean result = false;

    if (treeNode != null) {
      if (set.contains(target - treeNode.val)) {
        return true;
      }

      result = iterateTreeNode(treeNode.left);

      if (!result) {
        result = iterateTreeNode(treeNode.right);
      }
    }

    return result;
  }

  public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

}
