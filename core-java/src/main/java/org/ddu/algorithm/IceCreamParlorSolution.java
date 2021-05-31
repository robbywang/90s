package org.ddu.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Date: 2016年5月20日 上午10:49:53 <br/>
 *
 * @author wangtao
 */
public class IceCreamParlorSolution {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int cases = Integer.valueOf(in.nextLine());
    while (cases > 0) {
      int sum = Integer.valueOf(in.nextLine());
      int num = Integer.valueOf(in.nextLine());
      String numberStr = in.nextLine();
      String[] numberArray = numberStr.split(" ");
      Map<Integer, Integer> numberMap = new HashMap<>();
      for (int i=0; i<num; i++) {
        if (Integer.valueOf(numberArray[i]) < sum) {
          Integer sumLeft = sum - Integer.valueOf(numberArray[i]);

          if (numberMap.containsKey(sumLeft)) {
            int index = i+1;
            System.out.println(numberMap.get(sumLeft) + " " + index);

          } else {
            if (!numberMap.containsKey(Integer.valueOf(numberArray[i]))) {
              numberMap.put(Integer.valueOf(numberArray[i]), Integer.valueOf(i+1));
            }
          }
        }
      }
      cases--;
    }
    in.close();
  }
}
