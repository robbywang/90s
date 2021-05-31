package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by lideyou on 16/5/19.
 * Transformations: flatMap reduceByKey
 */
public class TestFlatMap {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.usage("TestFlatMap"));
            System.exit(0);
        }

        // Initialize SparkContext
        String appName = "java_demo_testMap";
        String dataFile = "src/main/resources/TestFlatMap.txt";
        SparkConf conf = new SparkConf().setAppName(appName);
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> input = sc.textFile(dataFile);
        input.cache();

        JavaRDD<String> words = input.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String t) throws Exception {
                return Arrays.asList(t.split(" ")).iterator();
            }
        });

        JavaPairRDD<String, Integer> counts =
            words.mapToPair(new PairFunction<String, String, Integer>() {
                @Override
                public Tuple2<String, Integer> call(String s) throws Exception {
                    return new Tuple2<>(s, 1);
                }
            }).reduceByKey(new Function2<Integer, Integer, Integer>() {
                @Override
                public Integer call(Integer v1, Integer v2) throws Exception {
                    return v1 + v2;
                }
            });

		System.out.println("----------flatMap-----------");
		System.out.println(words.collect());
		System.out.println("----------mapToPair-----------");
		System.out.println(counts.collect());
		System.out.println("----------reduceByKey-----------");
		System.out.println(counts.collect());
		sc.close();
    }

}
