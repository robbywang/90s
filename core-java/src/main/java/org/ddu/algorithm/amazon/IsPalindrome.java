package org.ddu.algorithm.amazon;

/**
 * 回文数.
 */
public class IsPalindrome {
  public boolean isPalindrome(int x) {
    //边界判断
    if (x < 0) return false;
    int div = 1;
    //
    while (x / div >= 10) div *= 10;
    while (x > 0) {
      int left = x / div;
      int right = x % 10;
      if (left != right) return false;
      // 去掉首尾数字.
      x = (x % div) / 10;
      div /= 100;
    }
    return true;
  }

  public boolean isPalindrome2(int x) {
    //思考：这里大家可以思考一下，为什么末尾为 0 就可以直接返回 false
    if (x < 0 || (x % 10 == 0 && x != 0)) return false;
    int revertedNumber = 0;
    while (x > revertedNumber) {
      revertedNumber = revertedNumber * 10 + x % 10;
      x /= 10;
    }
    return x == revertedNumber || x == revertedNumber / 10;
  }

  public static void main(String[] args) {
    IsPalindrome instance = new IsPalindrome();
    System.out.println(instance.isPalindrome(121));
  }
}
