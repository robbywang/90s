package ddu.spark.sample.java;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkUtil {
  
  public static String SAMPLE_DATA_FILE = "classes/spark/sample.txt";
  public static String SAMPLE_APP_NAME = "SparkSampleApp";
  private static JavaSparkContext sc = null;
  
  public static JavaSparkContext getSparkContext() {
    SparkConf conf = new SparkConf().setAppName(SAMPLE_APP_NAME);
    if (sc == null) {
      sc = new JavaSparkContext(conf);
    }
    return sc;
  }
  
  public static JavaRDD<String> getRdd() {
    JavaSparkContext sc = getSparkContext();
    JavaRDD<String> rdd = sc.textFile(SAMPLE_DATA_FILE);
    return rdd;
  }
  
  public static void collectTest() {
    JavaRDD<String> rdd = getRdd();
    rdd.cache();

    List<String> line = rdd.collect();
    System.out.println("rdd.collect() -> List<String>");
    line.forEach(System.out::println);
    
    StringBuilder logs = new StringBuilder("--- SparkSample ---\r\n");
    logs.append("rdd.first() -> ").append(rdd.first()).append("\r\n")
    .append("rdd.top(1) -> ").append(rdd.top(1)).append("\r\n")
    .append("rdd.take(3) -> ").append(rdd.take(3)).append("\r\n")
    .append("rdd.count() -> ").append(rdd.count()).append("\r\n")
    .append("rdd.takeOrdered(6) -> ").append(rdd.takeOrdered(6)).append("\r\n");

    System.out.println(logs.toString());
    
    close();
  }
  
  public static void close() {
    sc.close();
    sc = null;
  }
}
