package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lideyou on 16/5/22.
 * Transformations: union intersection subtract cartesian distinct
 */
public class TestUnion {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.usage("TestUnion"));
            System.exit(0);
        }

        // Initialize SparkContext
        String appName = "java_demo_testMap";
        SparkConf conf = new SparkConf().setAppName(appName);
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> lint = Arrays.asList(1, 2, 2, 3, 3);
        List<Integer> lint1 = Arrays.asList(3, 4, 5, 5);

        JavaRDD<Integer> rdd1 = sc.parallelize(lint);
        JavaRDD<Integer> rdd2 = sc.parallelize(lint1);

        System.out.println("----------Transformations: union intersection subtract cartesian distinct-----------");

        System.out.println(String.format("---------------------\nrdd1: %s\nrdd2: %s", rdd1.collect(), rdd2.collect()));

        System.out.println("----------union-----------");
        System.out.println(rdd1.union(rdd2).collect());

        System.out.println("----------intersection-----------");
        System.out.println(rdd1.intersection(rdd2).collect());
        System.out.println("-----------subtract----------");
        System.out.println(rdd1.subtract(rdd2).collect());
        System.out.println("-----------cartesian----------");
        System.out.println(rdd1.cartesian(rdd2).collect());

        System.out.println("----------distinct-----------");
        System.out.println(rdd1.distinct().collect());

        sc.close();
    }
}
