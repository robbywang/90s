package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.List;

/**
 * Created by lideyou on 16/5/19.
 * Actions: first top take count collect takeOrdered
 */

public class TestCollect {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.usage("TestCollect"));
            System.exit(0);
        }

        // Initialize SparkContext
        String appName = "java_demo_testCollect";
        String input = "/Users/taowang/git/ddu/spark/target/classes/data.txt";
        SparkConf conf = new SparkConf().setAppName(appName);
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile(input);
        lines.cache();

        // -- SparkSession usage.
        SparkSession spark = SparkSession.builder().appName(appName).getOrCreate();
        JavaRDD<String> lines2 = spark.read().textFile(input).javaRDD();
        
        System.out.println("----------Actions: first top take count collect takeOrdered-----------");
        System.out.println("----------first-----------");
        System.out.println(lines.first());
        System.out.println("----------top-----------");
        System.out.println(lines.top(1));
        System.out.println("----------take-----------");
        System.out.println(lines.take(3));
        System.out.println("----------count-----------");
        System.out.println(lines.count());
        System.out.println("----------collect-----------");
        List<String> line = lines.collect();
        line.forEach(System.out::println);
//        for (String l : line) {
//            System.out.println(l);
//        }
        System.out.println("----------takeOrdered-----------");
        System.out.println(lines.takeOrdered(6));


        sc.close();
    }
}


