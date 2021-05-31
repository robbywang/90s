package org.ddu.algorithm.amazon;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

  public int[] twoSum(int[] numbers, int target) {
    if (numbers.length < 2 || target > numbers[numbers.length - 1] + numbers[numbers.length - 2]) {
      return null;
    }

    int[] result = new int[2];

    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < numbers.length; i++) {
      if (map.containsKey(target - numbers[i])) {
        int index1 = map.get(target - numbers[i]);
        result[0] = index1 + 1;
        result[1] = i + 1;
        return result;
      }

      map.put(numbers[i], i);
    }

    return null;
  }

  public static void main(String[] args) {
    int[] numbers = {2, 3, 4};
    TwoSum twoSum = new TwoSum();
    System.out.println(twoSum.twoSum(numbers, 6));
  }

}
