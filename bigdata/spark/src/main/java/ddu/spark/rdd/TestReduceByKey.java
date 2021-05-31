package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.*;

/**
 * Created by lideyou on 16/5/23.
 * Transformations: sortByKey groupByKey reduceByKey mapValues keys values
 */
public class TestReduceByKey {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.usage("TestReduceByKey"));
            System.exit(0);
        }

        // Initialize SparkContext
        String appName = String.format("java_demo_%s", "TestReduceByKey");
        SparkConf conf = new SparkConf().setAppName(appName);
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Tuple2<Integer, Integer>> elements = new ArrayList<>();
        elements.add(new Tuple2<>(3, 4));
        elements.add(new Tuple2<>(1, 2));
        elements.add(new Tuple2<>(3, 6));
        JavaRDD<Tuple2<Integer, Integer>> rdd = sc.parallelize(elements);

        JavaPairRDD<Integer, Integer> pairRDD =
            rdd.mapToPair(new PairFunction<Tuple2<Integer, Integer>, Integer, Integer>() {
                @Override
                public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> integerIntegerTuple2) throws Exception {
                    return integerIntegerTuple2;
                }
            });

        // reduceByKey
        JavaPairRDD<Integer, Integer> rbk =
            pairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
                @Override
                public Integer call(Integer v1, Integer v2) throws Exception {
                    return v1 + v2;
                }
            });

        JavaPairRDD<Integer, Integer> mv = pairRDD.mapValues(new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer v1) throws Exception {
                return v1 + 1;
            }
        });

        // flatMapValues
        JavaPairRDD<Integer, Integer> fmv =
            pairRDD.flatMapValues(new Function<Integer, Iterable<Integer>>() {
                @Override
                public Iterable<Integer> call(Integer v1) throws Exception {
                    List<Integer> vv = new ArrayList<>();
                    while (v1 <= 7) {
                        vv.add(v1);
                        v1++;
                    }
                    return vv;
                }
            });



        System.out.println("----------Transformations: Pair RDD-----------");
        //        System.out.println(rdd.collect());

        System.out.println("----------pairRDD:-----------");
        System.out.println(pairRDD.collect());

        System.out.println("----------sortByKey:-----------");
        System.out.println(pairRDD.sortByKey().collect());

        System.out.println("----------groupByKey-----------");
        System.out.println(pairRDD.groupByKey().collect());

        System.out.println("----------reduceByKey-----------");
        System.out.println(rbk.collect());



        System.out.println("----------mapValues-----------");
        System.out.println(mv.collect());

        System.out.println("-----------flatMapValues----------");
        System.out.println(fmv.collect());

        System.out.println("----------keys-----------");
        System.out.println(pairRDD.keys().collect());

        System.out.println("----------values-----------");
        System.out.println(pairRDD.values().collect());


        sc.close();
    }
}
