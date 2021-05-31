package org.ddu.algorithm.amazon;

/**
 * 最长回文子串
 *
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 *
 * 输入: "babad" 输出: "bab" 注意: "aba" 也是一个有效答案。
 */
public class LongestPalindrome {

  /**
   * 扩展中心
   */
  public String longestPalindrome(String s) {
    if (s == null || s.isEmpty()) {
      return "";
    }

    int start = 0;
    int end = 0;

    for (int i = 0; i < s.length(); i++) {
      int len1 = length(s, i, i);
      int len2 = length(s, i, i + 1);

      int max = Math.max(len1, len2);

      if (max > end - start + 1) {
        start = i - (max - 1) / 2;
        end = i + max / 2;
      }

    }

    return s.substring(start, end + 1);
  }

  private int length(String s, int start, int end) {
    int l = start;
    int r = end;
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
      l--;
      r++;
    }

    // 注意之前l,r多-- ++ 了一次。
    return r - l - 1;
  }

  /**
   * 动态规划
   */
  public String longestPalindrome2(String s) {
    if (s == null || s.isEmpty()) {
      return "";
    }

    int start = 0;
    int end = 0;
    boolean[][] isPalindrome = new boolean[s.length()][s.length()];

    for (int r = 0; r < s.length(); r++) {
      for (int l = 0; l < r; l++) {
        if (s.charAt(r) == s.charAt(l) && (r - l <= 2 || isPalindrome[l + 1][r - 1])) {
          isPalindrome[l][r] = true;

          if (r - l > end - start) {
            start = l;
            end = r;
          }
        }
      }
    }

    return s.substring(start, end + 1);
  }

  public static void main(String[] args) {
    LongestPalindrome instance = new LongestPalindrome();
    System.out.println(instance.longestPalindrome("bb"));
    System.out.println(instance.longestPalindrome2("bb"));

  }
}
