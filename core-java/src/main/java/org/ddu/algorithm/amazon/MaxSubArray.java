package org.ddu.algorithm.amazon;

public class MaxSubArray {

  public int maxSubArray(int[] nums) {
    int maxSum = nums[0];

    int l = 0;
    int r = 0;

    for (int i = 1; i < nums.length; i++) {
      if (nums[i] > 0) {
        r = i;

        int sum = sum(nums, l, r);

        if (sum > maxSum) {
          maxSum = sum;
        }

      } else {
        l = i;
      }

    }
    return maxSum;
  }

  int sum(int[] nums, int l, int r) {
    int sum = 0;
    for (int i = l; i <= r; i++) {
      sum += nums[i];
    }

    return sum;
  }

}
