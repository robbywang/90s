package org.ddu.algorithm;

import java.util.Scanner;

/**
 * Date: 2016年5月20日 上午10:49:53 <br/>
 *
 * @author wangtao
 */
public class SherlockAndArraySolution {
  public static void main(String[] args) {
    /*
     * Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be
     * named Solution.
     */
    Scanner in = new Scanner(System.in);
    int t = Integer.valueOf(in.nextLine());
    // System.out.println("t: " + t);
    while (t > 0) {
      int n = Integer.valueOf(in.nextLine());
      String str = in.nextLine();
      // System.out.println("str: " + str);
      String[] a = str.split(" ");

      int index = (n - 1) / 2;
      int leftSum = 0, rightSum = 0;
      for (int i = 0; i <= index - 1; i++) {
        leftSum += Integer.valueOf(a[i]);
      }
      for (int i = index + 1; i < n; i++) {
        rightSum += Integer.valueOf(a[i]);
      }

      if (find(a, leftSum, rightSum, index, index)) {
        System.out.println("YES");
      } else {
        System.out.println("NO");
      }
      t--;
    }
  }

  private static boolean find(String[] a, int leftSum, int rightSum, int nowIndex, int lastIndex) {
    if (nowIndex < 0 || nowIndex > a.length) {
      return false;
    } else {
      if (leftSum == rightSum) {
        return true;
      } else if (rightSum > leftSum) {
        int nextIndex = nowIndex + 1;
        if (nextIndex != lastIndex) {
          return find(a, leftSum + Integer.valueOf(a[nowIndex]),
              rightSum - Integer.valueOf(a[nowIndex + 1]), nextIndex, nowIndex);
        } else {
          return false;
        }

      } else {
        int nextIndex = nowIndex - 1;
        if (nextIndex != lastIndex) {
          return find(a, leftSum - Integer.valueOf(a[nowIndex - 1]),
              rightSum + Integer.valueOf(a[nowIndex]), nextIndex, nowIndex);
        } else {
          return false;
        }
      }
    }
  }

}
