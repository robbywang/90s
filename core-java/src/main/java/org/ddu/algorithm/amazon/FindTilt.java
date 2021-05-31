package org.ddu.algorithm.amazon;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class FindTilt {

  int result;

  public int findTilt(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int leftSum = sum(root.left);
    int rightSum = sum(root.right);
    return result += Math.abs(leftSum -rightSum);
  }


  private int sum(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int leftSum = sum(root.left);
    int rightSum = sum(root.right);
    result += Math.abs(leftSum -rightSum);
    return root.val + leftSum + rightSum;
  }

  public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }



  public boolean wordBreak(String s, List<String> wordDict) {
    for (int i=0; i< wordDict.size(); i++) {
      if (s.startsWith(wordDict.get(i))) {
        s = s.substring(wordDict.get(i).length());
        i--;
        continue;
      }

      if (s.endsWith(wordDict.get(i))) {
        s = s.substring(0, s.length()- wordDict.get(i).length());
        i--;
      }
    }

    if (s.length() == 0) {
      return true;
    }
    return false;
  }

  public static void main(String[] args) {
    FindTilt ins = new FindTilt();
    String s = "cars";
    List<String> wordDict = Arrays.asList("car","ca","rs");
    ins.wordBreak(s, wordDict);
  }


  public int pivotIndex(int[] nums) {

    if (nums.length == 0 || nums.length == 1) {
      return -1;
    }

    int sum = 0;
    for(int num : nums) {
      sum+= num;
    }

    int subSum = 0;
    for (int i=0; i< nums.length; i++) {
      if (i >= 1) {
        subSum +=nums[i-1];
      }

      if (2*subSum + nums[i] == sum) {
        return i;
      }
    }

    return -1;
  }


}
