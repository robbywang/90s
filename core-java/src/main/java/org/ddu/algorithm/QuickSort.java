package org.ddu.algorithm;

import java.util.Arrays;

class QuickSort {

  public static void main(String[] args) {
    int[] input = {3,4,7,2,3,1};
    qsortCommon(input, 0, 5);
    Arrays.stream(input).forEach(System.out::print);

    System.out.println();

    int[] input2 = {3,4,7,2,3,1};
    quickSort(input2, 0, 5);
    Arrays.stream(input2).forEach(System.out::print);

  }

  public static void quickSort(int[] arr, int low, int high) {
    if (low >= high) {
      return;                        //递归出口
    }

    int first = low;
    int last = high;
    //取第一个元素作为基准元
    int pivot = arr[low];

    while (first < last) {
      while (first < last && arr[last] >= pivot) {
        last--;
      }
      arr[first] = arr[last];
      while (first < last && arr[first] <= pivot) {
        first++;
      }
      arr[last] = arr[first];
    }

    //基准元居中
    arr[first] = pivot;

    quickSort(arr, low, first-1);
    quickSort(arr, first+1, high);
  }


  /// <summary>
  /// 1.0 固定基准元（基本的快速排序）
  /// </summary>
  public static void qsortCommon(int[] arr, int low, int high) {
    if (low >= high) {
      return;                        //递归出口
    }
    int partition = partition(arr, low, high);      //将 >= x 的元素交换到右边区域，将 <= x 的元素交换到左边区域
    qsortCommon(arr, low, partition - 1);
    qsortCommon(arr, partition + 1, high);
  }

  /// <summary>
  /// 固定基准元，默认数组第一个数为基准元，左右分组，返回基准元的下标
  /// </summary>
  private static int partition(int[] arr, int low, int high) {
    int first = low;
    int last = high;
    int pivot = arr[low];                             //取第一个元素作为基准元
    while (first < last) {
      while (first < last && arr[last] >= pivot) {
        last--;
      }
      arr[first] = arr[last];
      while (first < last && arr[first] <= pivot) {
        first++;
      }
      arr[last] = arr[first];
    }
    arr[first] = pivot;                               //基准元居中
    return first;
  }

}