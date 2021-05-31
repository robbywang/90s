package org.ddu.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Arrays {
  public static void main(String[] args) {
    gTest();
//    findMissNum();
//    findN2Num();
//    fibonacci1();
//    fibonacci2();
//
//    bubbleSort(new int[]{1, 2, 3, 4});
//    bubbleSort(new int[]{4, 3, 2, 1});
//
//    insertSort();
//    insertSort2();
  }

  private static void gTest() {
    int[] list_a = {5, 21, 61, 25, 11, 59};
    int[] list_b = {17, 81, 0, 7};
    int[] list_c = {42, 8, 25, 33, 2};
    fool(list_a, list_b, list_c);
  }

  private static void fool(int[] list_a, int[] list_b, int[] list_c) {
    List x = new ArrayList();
    int y = 1000;
    for (int a : list_a) {
      for (int b: list_b) {
        for (int c: list_c) {
          int z = 0;
          if (Math.abs(a-b) < Math.abs(a-c)) {
            z = Math.abs(a-c);
          } else {
            z = Math.abs(a-b);
          }

          if (z < Math.abs(b-c)) {
            z = Math.abs(b-c);
          }

          if (z < y) {
            y = z;
            x.clear();
            x.add(a);
            x.add(b);
            x.add(c);
//            x = {a, b,c};
          }

        }
      }
    }

    System.out.print(x);

  }


  /**
   * 1到n中减少了一个数，顺序被打乱，找出缺失的数
   */
  private static void findMissNum() {
    int[] input = {1, 2, 3, 5};
    int n = input.length;
    int result = 0;
    for (int i : input) {
      result = result ^ i;
    }

    for (int i = 1; i <= n; i++) {
      result = result ^ i;
    }

    System.out.println("findMissNum:" + result);
  }

  /**
   * 找到数组中出现次数超过 n/2的数字.
   */
  private static void findN2Num() {
    int[] input = {1, 2, 3, 3, 3, 3, 4, 3};
    int n = input.length;
    int result = 0;
    int count = 0;

    for (int i = 0; i <= n - 1; i++) {
      // 重置.
      if (count == 0) {
        result = input[i];
        count = 1;

      } else {
        // 相等+1,不相等-1.
        if (result == input[i]) {
          count++;
        } else {
          count--;
        }
      }
    }

    System.out.println("findN2Num:" + result);
  }

//  public static void fibonacci1() {
//    int n = 10;
//    int result = fibonacci(n);
//    System.out.println("fibonacci1:" + result);
//  }

//  public static void fibonacci2() {
//    int n = 10;
//    int result = 0;
//    int temp1 = 1;
//    int temp2 = 1;
//    if (n == 0) {
//      result = 0;
//
//    } else if (n <= 2) {
//      result = 1;
//
//    } else {
//      for (int i = 3; i <= n; i++) {
//        result = temp1 + temp2;
//        temp1 = temp2;
//        temp2 = result;
//      }
//    }
//
//    System.out.println("fibonacci2:" + result);
//  }
//
//  // 1,1,2,3,5,8,13,21,34,55.
//  private static int fibonacci(int n) {
//    if (n == 0) {
//      return 0;
//    } else if (n == 1 || n == 2) {
//      return 1;
//    } else {
//      return fibonacci(n - 1) + fibonacci(n - 2);
//    }
//  }
//
//  public static void bubbleSort(int[] arr) {
//    boolean didSwap;
//    int o = 0;
//    for (int i = 0, len = arr.length; i < len - 1; i++) {
//      didSwap = false;
//      for (int j = 0; j < len - i - 1; j++) {
//        o++;
//        if (arr[j + 1] < arr[j]) {
//          int temp = arr[j];
//          arr[j] = arr[j + 1];
//          arr[j + 1] = temp;
//          didSwap = true;
//        }
//      }
//      if (!didSwap) {
//        break;
//      }
//    }
//
//    System.out.println("bubbleSort: " + o);
//    java.util.Arrays.stream(arr).forEach(System.out::print);
//    System.out.println("");
//  }
//
//  public static void insertSort() {
//    int[] arrays = {6, 5, 3, 4, 1, 2};
//    //临时变量
//    int temp;
//
//    //外层循环控制需要排序的趟数(从1开始因为将第0位看成了有序数据)
//    for (int i = 1; i < arrays.length; i++) {
//
//      temp = arrays[i];
//
//      //如果前一位(已排序的数据)比当前数据要大，那么就进入循环比较[参考第二趟排序]
//      while (i >= 1 && arrays[i - 1] > temp) {
//
//        //往后退一个位置，让当前数据与之前前位进行比较
//        arrays[i] = arrays[i - 1];
//        //不断往前，直到退出循环
//        i--;
//      }
//
//      //退出了循环说明找到了合适的位置了，将当前数据插入合适的位置中
//      arrays[i] = temp;
//
//    }
//    System.out.println("insertSort");
//    java.util.Arrays.stream(arrays).forEach(System.out::print);
//    System.out.println("");
//  }
//
//  public static void insertSort2() {
//    int[] arrays = {6, 5, 3, 4, 1, 2};
//    //临时变量
//    int temp;
//    for (int i=1; i< arrays.length; i++) {
//      temp = arrays[i];
//      for (int j=i-1; j>=0; j--) {
//        if (temp < arrays[j]) {
//          arrays[j+1] = arrays[j];
//          arrays[j] = temp;
//        }
//      }
//    }
//
//    System.out.println("insertSort2");
//    java.util.Arrays.stream(arrays).forEach(System.out::print);
//    System.out.println("");
//
//  }

}
