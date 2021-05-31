package org.ddu.algorithm.amazon;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 最接近原点的 K 个点
 *
 * 输入：points = [[1,3],[-2,2]], K = 1 输出：[[-2,2]] 解释： (1, 3) 和原点之间的距离为 sqrt(10)， (-2, 2) 和原点之间的距离为
 * sqrt(8)， 由于 sqrt(8) < sqrt(10)，(-2, 2) 离原点更近。 我们只需要距离原点最近的 K = 1 个点，所以答案就是 [[-2,2]]。
 */
public class KClosest {

  /**
   * TreeSet 法.
   */
  public int[][] kClosest(int[][] points, int K) {
    if (points == null) {
      return null;
    }

    TreeSet<int[]> set = new TreeSet<>(new Comparator<int[]>() {
      @Override
      public int compare(int[] o1, int[] o2) {
        return doCompare(o1, o2);
      }
    });

    for (int x = 0; x < points.length; x++) {
      if (set.size() < K) {
        set.add(points[x]);

      } else {
        int[] last = set.last();

        if (doCompare(last, points[x]) > 0) {
          set.pollLast();
          set.add(points[x]);
        }
      }
    }

    int length = set.size();
    int[][] result = new int[length][2];

    int i = 0;
    Iterator<int[]> it = set.iterator();
    while (it.hasNext()) {
      result[i++] = it.next();
    }

    return result;
  }

  private int doCompare(int[] o1, int[] o2) {
    int s1 = o1[0] * o1[0] + o1[1] * o1[1];
    int s2 = o2[0] * o2[0] + o2[1] * o2[1];
    if (s1 >= s2) {
      return 1;
    } else {
      return -1;
    }
  }

  // ------------------------

  /**
   * 分治法.
   */
  public int[][] kClosest2(int[][] points, int K) {
    work(points, 0, points.length - 1, K);
    return Arrays.copyOfRange(points, 0, K);
  }

  public void work(int[][] points, int low, int high, int K) {
    if (low >= high) {
      return;
    }
    int first = low, last = high;
    int pivot = dist(points, first);
    int[] pointFirst = points[first];

    while (first < last) {
      while (first < last && dist(points, last) > pivot) {
        last--;
      }
      points[first] = points[last];
      while (first < last && dist(points, first) < pivot) {
        first++;
      }
      points[last] = points[first];

//      swap(points, first, last);
    }

    points[first] = pointFirst;

    if (last >= K - 1) {
      return;
//      work(points, first, i, K);
    } else {
      work(points, first + 1, high, K - (last - first + 1));
    }
  }

  public int dist(int[][] points, int i) {
    return points[i][0] * points[i][0] + points[i][1] * points[i][1];
  }

  // ------------

  /**
   * 改进的快速排序 - 分治法.
   */
  public int[][] kClosestQuickSort(int[][] points, int K) {
    int start = 0;
    int end = points.length - 1;
    while (start < end) {
      int index = patition(points, start, end);
      if (index == K) {
        break;
      } else if (index < K) {
        start = index + 1;
      } else {
        end = index - 1;
      }
    }

    return Arrays.copyOf(points, K);
  }

  private int patition(int[][] points, int start, int end) {
    int i = start;
    int j = end + 1;
    int mid = distance(points[i][0], points[i][1]);
    while (true) {
      while (distance(points[++i][0], points[i][1]) < mid && i < end) {
        ;
      }
      while (distance(points[--j][0], points[j][1]) > mid && j > start) {
        ;
      }
      if (i >= j) {
        break;
      }
      swap(points, i, j);
    }
    swap(points, start, j);
    return j;
  }

  private int distance(int a, int b) {
    return a * a + b * b;
  }

  private void swap(int[][] points, int a, int b) {
    int[] temp = points[a];
    points[a] = points[b];
    points[b] = temp;
  }


  public static void main(String[] args) {
    KClosest instance = new KClosest();
    int[][] points = {
        {1, 3},
        {-2, 2}
    };
    instance.kClosest(points, 1);

  }

}
