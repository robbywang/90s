package org.ddu.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * (a+b)%M=(a%M+b%M)%M(a+b)%M=(a%M+b%M)%M  --- 1
 * (a−b)%M=(a%M−b%M)%M(a−b)%M=(a%M−b%M)%M  --- 2
 *
 * Date: 2016年5月20日 上午10:49:53 <br/>
 *
 * @author wangtao
 */
public class MaximizeSumSolution {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int cases = Integer.valueOf(in.nextLine());
    while (cases > 0) {
      String[] sizeNumber = in.nextLine().split(" ");
      int size = Integer.valueOf(sizeNumber[0]);
      Long number = Long.valueOf(sizeNumber[1]);
      String[] numStr = in.nextLine().split(" ");
      Set<Long> sumModeSet = new TreeSet<>();
      List<Long> sumList = new ArrayList<>();
      Long sumMode = 0L;
      for (int i=0; i<size; i++) {
        Long value = Long.valueOf(numStr[i]);
        sumMode = (sumMode + value%number)%number;
        sumModeSet.add(sumMode);
      }

      Long[] sumModeArray = sumModeSet.toArray(new Long[sumModeSet.size()]);
      long max = 0L;
      for (int i=0; i<sumModeSet.size(); i++) {
        for (int j= i-1; j>=0; j--) {
          if (sumModeArray[j] < sumModeArray[i]) {
            continue;
          }

          if (sumModeArray[i] - sumModeArray[j] > max) {
            max = sumModeArray[i] - sumModeArray[j];
          }

        }
      }
      System.out.println(max);
      cases--;
    }
    in.close();
  }
}
