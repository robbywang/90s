package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.List;

/**
 * Created by lideyou on 16/5/21. Transformations: filter
 */
public class TestFilter {

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println(Utils.usage("TestFilter"));
      System.exit(0);
    }

    // Initialize SparkContext
    String appName = "java_demo_testMap";
    String dataFile = "hdfs://localhost:9000/test/data.txt";
    SparkConf conf = new SparkConf().setAppName(appName);
    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<String> input = sc.textFile(dataFile);
    input.cache();

    System.out.println("----------Transformations: filter-----------");
    List<String> lines = input.filter(new Function<String, Boolean>() {
      @Override
      public Boolean call(String v1) throws Exception {
        return v1.contains("Java");
      }
    }).collect();
    System.out.println("-----------filter via Function");
    for (String s : lines) {
      System.out.println(s);
    }

    System.out.println("-----------filter via Lambda");
    List<String> lineslambda = input.filter(value -> value.contains("Java")).collect();
    lineslambda.forEach(System.out::println);

    sc.close();
  }
}
