package org.ddu.algorithm.amazon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SolutionTwoSum {

  /**
   * 两数之和
   *
   * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
   *
   * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
   *
   * 给定 nums = [2, 7, 11, 15], target = 9
   *
   * 因为 nums[0] + nums[1] = 2 + 7 = 9 所以返回 [0, 1]
   */
  public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();

    int index = 0;

    for (int i = 0; i < nums.length; i++) {
      int num = nums[i];
      if (map.containsKey(target - num)) {
        index = i;
        break;
      } else {
        map.put(num, i);
      }
    }

    return new int[]{index, map.get(target - nums[index])};
  }


  public static void main(String[] args) {
    SolutionTwoSum solutions = new SolutionTwoSum();

    // 两数之和
    int[] twoSum = solutions.twoSum(new int[]{2, 7, 11, 15}, 9);
    Arrays.stream(twoSum).forEach(System.out::println);
  }

}
