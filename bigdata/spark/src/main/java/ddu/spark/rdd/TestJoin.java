package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lideyou on 16/5/25.
 * Transformations:
 */
public class TestJoin {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.usage("TestJoin"));
            System.exit(0);
        }

        // Initialize SparkContext
        String appName = "java_demo_testMap";
        SparkConf conf = new SparkConf().setAppName(appName);
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Tuple2<Integer, Integer>> ele1 = new ArrayList<>();
        ele1.add(new Tuple2<>(1, 2));
        ele1.add(new Tuple2<>(3, 4));
        ele1.add(new Tuple2<>(3, 9));

        List<Tuple2<Integer, Integer>> ele2 = new ArrayList<>();
        ele2.add(new Tuple2<>(3, 9));

        //        JavaRDD<Tuple2<Integer, Integer>> rdd1 = sc.parallelize(ele1);
        //        JavaRDD<Tuple2<Integer, Integer>> rdd2 = sc.parallelize(ele2);

        JavaPairRDD<Integer, Integer> pairRDD1 = TestJoin.getPairRDD(sc.parallelize(ele1)).cache();
        JavaPairRDD<Integer, Integer> pairRDD2 = TestJoin.getPairRDD(sc.parallelize(ele2)).cache();

        System.out.println("----------Transformations:-----------");
        System.out.println(String.format("---------------------\npairRDD1: %s\npairRDD2: %s", pairRDD1.collect(), pairRDD2.collect()));

        System.out.println("----------join-----------");
        System.out.println(pairRDD1.join(pairRDD2).collect());

        System.out.println("----------subtractByKey-----------");
        System.out.println(pairRDD1.subtractByKey(pairRDD2).collect());

        System.out.println("----------leftOuterJoin-----------");
        System.out.println(pairRDD1.leftOuterJoin(pairRDD2).collect());

        System.out.println("----------rightOuterJoin-----------");
        System.out.println(pairRDD1.rightOuterJoin(pairRDD2).collect());

        System.out.println("----------cogroup-----------");
        System.out.println(pairRDD1.cogroup(pairRDD2).collect());

        sc.close();
    }

    private static JavaPairRDD<Integer, Integer> getPairRDD(JavaRDD<Tuple2<Integer, Integer>> rdd) {
        return rdd.mapToPair(new PairFunction<Tuple2<Integer, Integer>, Integer, Integer>() {
            @Override
            public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> integerIntegerTuple2) throws Exception {
                return integerIntegerTuple2;
            }
        });
    }

}
