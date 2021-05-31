package org.ddu.algorithm.amazon;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

public class FindKthLargest {

  public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> heap =
        new PriorityQueue<Integer>((n1, n2) -> n1 - n2);

    for (int num : nums) {
      heap.add(num);
      System.out.println(String.format("%s IN (%s)", num, heap.toString()));
      if (heap.size() > k) {
        heap.poll();
        System.out.println(String.format("%s OUT (%s)", num, heap.toString()));
      }
    }

    return heap.poll();
  }

  public int findKthLargest_QuickSort(int[] nums, int k) {
    quickSearch(nums, 0, nums.length-1, k);
    return nums[k-1];
  }

  private void quickSearch(int[] nums, int low, int high, int k) {
    if (low < high) {
      int temp = nums[low];
      int l = low;
      int h = high;

      while (l < h ) {
        while (l < h && nums[h] <= temp) {
          h--;
        }
        nums[l] = nums[h];

        while (l < h && nums[l] >= temp) {
          l++;
        }
        nums[h] = nums[l];
      }

      nums[l] = temp;

      if (l >= k) {
        quickSearch(nums, low, l-1, k);
      } else {
        quickSearch(nums, l+1, high, k);
      }

    }

  }

  public static void main(String[] args) {
    FindKthLargest findKthLargest = new FindKthLargest();
    int[] nums = {3, 2, 3, 1, 2, 4, 5, 5, 6};
    int k = 4;
    System.out.println(findKthLargest.findKthLargest(nums, k));
    System.out.println(findKthLargest.findKthLargest_QuickSort(nums, k));
  }

}
