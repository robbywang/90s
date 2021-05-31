package ddu.spark.rdd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lideyou on 16/5/22. Utils
 */
public class Utils {
  public static void main(String[] args) {
    System.out.println("range(0,10)" + range(0, 10));
  }

  static List<Integer> range(int start, int stop) {
    int i;
    List<Integer> ranger = new ArrayList<>();
    for (i = start; i < stop; i++) {
      ranger.add(i);
    }
    return ranger;

  }

  // Usage
  static String usage(String className) {
    String fullName = Utils.class.getPackage().getName() + "." + className;

    return String.format(
        "Usage: spark-submit --master local[1] --class %s ddu-core-1.0.0-SNAPSHOT.jar run", fullName);
  }

}
