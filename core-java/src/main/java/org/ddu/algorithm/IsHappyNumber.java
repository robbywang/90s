package org.ddu.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * 编写一个算法来判断一个数是不是“快乐数”。
 *
 * 一个“快乐数”定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和， 然后重复这个过程直到这个数变为 1，也可能是无限循环但始终变不到 1。如果可以变为
 * 1，那么这个数就是快乐数。
 *
 * 输入: 19 输出: true 解释: 12 + 92 = 82 82 + 22 = 68 62 + 82 = 100 12 + 02 + 02 = 1
 *
 * 解题思路: 对每次的结果判断是否重复， 如果重复，就肯定误解
 */
public class IsHappyNumber {

  public boolean isHappy(int n) {
    Set<Integer> set = new HashSet<>();
    int m = 0;
    while (true) {
      while (n != 0) {
        //巧用 % 和 /
        m += Math.pow(n % 10, 2);
        n /= 10;
      }

      if (m == 1) {
        return true;
      }

      if (set.contains(m)) {
        return false;
      } else {
        set.add(m);
        n = m;
        m = 0;
      }
    }
  }
}
