package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by lideyou on 16/5/19.
 * Transformations: flatMap reduceByKey
 */
public class TestFlatMap2 {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.usage("TestFlatMap2"));
            System.exit(0);
        }

        // Initialize SparkContext
        String appName = "java_demo_testMap";
//        SparkConf conf = new SparkConf().setAppName(appName);
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        JavaRDD<String> input = sc.textFile(dataFile);

        String dataFile = "src/main/resources/TestFlatMap.txt";
        SparkSession spark = SparkSession.builder().appName(appName).getOrCreate();
        Dataset<Row> df = spark.read().text(dataFile);
        df.show();
        
//        JavaRDD<String> input = spark.sparkContext().textFile(dataFile, 1).toJavaRDD();

//        dataSet.flatMap(f, encoder)
        
//        Dataset<String> words = dataSet.flatMap(new FlatMapFunction<String, String>() {
//            @Override
//            public Iterator<String> call(String t) throws Exception {
//                return Arrays.asList(t.split(" ")).iterator();
//            }
//        }, Encoders.STRING());
//        words.show();
 

//        JavaPairRDD<String, Integer> counts =
//            words.mapToPair(new PairFunction<String, String, Integer>() {
//                @Override
//                public Tuple2<String, Integer> call(String s) throws Exception {
//                    return new Tuple2<>(s, 1);
//                }
//            }).reduceByKey(new Function2<Integer, Integer, Integer>() {
//                @Override
//                public Integer call(Integer v1, Integer v2) throws Exception {
//                    return v1 + v2;
//                }
//            });
//
		System.out.println("----------flatMap-----------");
//		System.out.println(words.collect());
//		System.out.println("----------mapToPair-----------");
//		System.out.println(counts.collect());
//		System.out.println("----------reduceByKey-----------");
//		System.out.println(counts.collect());
    }

}
