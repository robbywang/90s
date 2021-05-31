package ddu.spark.sample.java;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;

/**
 * spark-submit --master local[1] --class ddu.spark.sample.java.SparkSample ddu-spark-1.0.0-SNAPSHOT.jar run
 */
public class SparkSample {

  public static void main(String[] args) {
    SparkUtil.collectTest();

    JavaRDD<String> rdd = SparkUtil.getRdd();
    rdd.cache();

    List<String> line = rdd.collect();
    System.out.println("rdd.collect() -> List<String>");
    line.forEach(System.out::println);
    
    StringBuilder logs = new StringBuilder("--- SparkSample ---\r\n");
    logs.append("rdd.first() -> ").append(rdd.first()).append("\r\n")
    .append("rdd.top(1) -> ").append(rdd.top(1)).append("\r\n")
    .append("rdd.take(3) -> ").append(rdd.take(3)).append("\r\n")
    .append("rdd.count() -> ").append(rdd.count()).append("\r\n")
    .append("rdd.takeOrdered(6) -> ").append(rdd.takeOrdered(6)).append("\r\n")
    ;

    System.out.println(logs.toString());
    SparkUtil.close();
  }
}
