package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lideyou on 16/5/25.
 * Transformations: combineByKey
 */
public class TestCombineByKey {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.usage("TestCombineByKey"));
            System.exit(0);
        }

        // Initialize SparkContext
        String appName = String.format("java_demo_%s", "TestCombineByKey");
        SparkConf conf = new SparkConf().setAppName(appName);
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Tuple2<String, Integer>> elements = new ArrayList<>();
        elements.add(new Tuple2<>("Apple", 1));
        elements.add(new Tuple2<>("Apple", 2));
        elements.add(new Tuple2<>("Pear", 3));
        elements.add(new Tuple2<>("Durian", 9));
        JavaRDD<Tuple2<String, Integer>> rdd = sc.parallelize(elements);

        JavaPairRDD<String, Integer> pairRDD =
            rdd.mapToPair(new PairFunction<Tuple2<String, Integer>, String, Integer>() {
                @Override
                public Tuple2<String, Integer> call(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                    return stringIntegerTuple2;
                }
            });


        Function<Integer, AvgCount> createCombiner = new Function<Integer, AvgCount>() {
            @Override
            public AvgCount call(Integer v1) throws Exception {
                return new AvgCount(v1, 1);
            }
        };

        Function2<AvgCount, Integer, AvgCount> mergeValue =
            new Function2<AvgCount, Integer, AvgCount>() {
                @Override
                public AvgCount call(AvgCount v1, Integer v2) throws Exception {
                    v1.total += v2;
                    v1.num += 1;
                    return v1;
                }
            };

        Function2<AvgCount, AvgCount, AvgCount> mergeCombiners =
            new Function2<AvgCount, AvgCount, AvgCount>() {
                @Override
                public AvgCount call(AvgCount v1, AvgCount v2) throws Exception {
                    v1.total += v2.total;
                    v1.num += v2.num;
                    return v1;
                }
            };

        //        AvgCount Initial = new AvgCount(0, 0);

        JavaPairRDD<String, AvgCount> avgCounts =
            pairRDD.combineByKey(createCombiner, mergeValue, mergeCombiners);

        Map<String, AvgCount> countMap = avgCounts.collectAsMap();

        System.out.println("-----------Transformations: combineByKey----------");

        System.out.println("-----------pairRDD----------");
        System.out.println(pairRDD.collect());

        System.out.println("-----------Key:[total,num,avg]----------");
        for (Map.Entry<String, AvgCount> entry : countMap.entrySet()) {
            String res =
                String.format("%s:[%s,%s,%s]", entry.getKey(), entry.getValue().total, entry.getValue().num, entry.getValue().avg());
            System.out.println(res);
        }

        sc.close();
    }
}
