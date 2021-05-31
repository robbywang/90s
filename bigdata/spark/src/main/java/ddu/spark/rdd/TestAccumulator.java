package ddu.spark.rdd;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.util.CollectionAccumulator;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by lideyou on 16/5/29.
 */
public class TestAccumulator {
    public static void main(String[] args) {
        // Initialize SparkContext
        String appName = "java_demo_TestAccumulator";
        String dataFile = "hdfs://dev/user/spark/data.txt";
        String outFile = "file:///hdkz/data/spark/data/outFile.txt";

        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName(appName);
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> rdd = sc.textFile(dataFile);

        final Accumulator<Integer> counts = sc.accumulator(0);

        CollectionAccumulator<Integer> ca = new CollectionAccumulator<>();
        
        JavaRDD<String> signs = rdd.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                if (s.contains("hadoop")) {
                    counts.add(1);
                    ca.add(1);
                }
                return Arrays.asList(s.split(" ")).iterator();
            }
        });

        System.out.println("-----------TestAccumulator----------");
//        signs.saveAsTextFile(outFile);

        System.out.println("Lines contains hadoop: " + counts.value());

        sc.stop();
    }
}
