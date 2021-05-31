package org.ddu.algorithm.amazon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class TopKFrequent {

  public List<Integer> topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    PriorityQueue<Entry<Integer, Integer>> treeSet = new PriorityQueue<>(
        new Comparator<Entry<Integer, Integer>>() {
          @Override
          public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
            return o1.getValue() - o2.getValue();
          }
        });

    for (int num : nums) {
      int count = map.getOrDefault(num, 0);
      map.put(num, count+1);
    }

    for (Entry<Integer, Integer> entry : map.entrySet()) {
      treeSet.add(entry);
      if (treeSet.size() > k) {
        treeSet.poll();
      }
    }

    List<Integer> result = new ArrayList<>();

    for (Entry<Integer, Integer> entry : treeSet) {
      result.add(entry.getKey());
    }

    return result;

  }


  public static void main(String[] args) {
    TopKFrequent ins = new TopKFrequent();
    int[] nums = {1,2};
    System.out.println(ins.topKFrequent(nums, 2));
  }
}
