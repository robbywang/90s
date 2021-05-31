package org.ddu.algorithm;

/**
 * Date:     2016年5月2日 下午8:28:57 <br/>
 */
public class Fibonacci {

  public static void main(String[] args) {
    System.out.println(f1(10));
    System.out.println(f2(10));
    System.out.println(f3(10));
  }

  /**
   * 1 1 2 3 5 8 13 21 34 55 ... k n:1 2 3 4 5 6 7  8  9  10
   */
  static int f1(int n) {
    if (n == 0) {
      return 0;
    } else if (n <= 2) {
      return 1;
    } else {
      return f1(n - 1) + f1(n - 2);
    }
  }

  static int f2(int n) {
    int a = 1, b = 1, result = 0;
    int i = 1;

    while (i <= n) {
      result = a;
      a = b;
      b = result + b;
      i++;
    }
    return result;
  }

  static int f3(int n) {
    if (n <= 2) {
      return 1;

    } else {
      int a = 1, b = 1, temp = 0;

      for (int i = 3; i <= n - 1; i++) {
        temp = a;
        a = b;
        b = temp + b;
      }

      return a + b;
    }
  }


}
