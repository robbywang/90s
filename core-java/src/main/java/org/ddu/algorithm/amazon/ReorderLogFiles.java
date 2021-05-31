package org.ddu.algorithm.amazon;

import java.util.Arrays;
import java.util.Comparator;

public class ReorderLogFiles {

  public String[] reorderLogFiles(String[] logs) {
    Arrays.sort(logs, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        int index1 = o1.indexOf(" ");
        int index2 = o2.indexOf(" ");
        String head1 = o1.substring(0, index1);
        String body1 = o1.substring(index1+1, o1.length());
        String head2 = o2.substring(0, index2);
        String body2 = o2.substring(index2+1, o2.length());

        boolean isDigital1 = Character.isDigit(body1.charAt(0));
        boolean isDigital2 = Character.isDigit(body2.charAt(0));

        if (isDigital1 && !isDigital2) {
          return 1;
        } else if (!isDigital1 && isDigital2) {
          return -1;
        } else if (isDigital1 && isDigital2) {
          return 0;
        } else {
          if (body1.compareTo(body2) == 0) {
            return head1.compareTo(head2);
          } else {
            return body1.compareTo(body2);
          }
        }

      }
    });

    return logs;
  }


  public static void main(String[] args) {

    ReorderLogFiles ins = new ReorderLogFiles();
    String[] logs = {
        "dig1 8 1 5 1",
        "let1 art can",
        "dig2 3 6",
        "let2 own kit dig",
        "let3 art zero"
    };
    ins.reorderLogFiles(logs);


  }

}
