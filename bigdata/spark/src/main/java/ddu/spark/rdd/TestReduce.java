package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lideyou on 16/5/22.
 * Action: reduce countByValue takeOrdered takeSample aggregate
 */
public class TestReduce {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.usage("TestReduce"));
            System.exit(0);
        }

        // Initialize SparkContext
        String appName = String.format("java_demo_%s", TestReduce.class.getName());
        SparkConf conf = new SparkConf().setAppName(appName);
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> lint = Arrays.asList(1, 2, 3, 4, 5, 3, 2);
        JavaRDD<Integer> rdd = sc.parallelize(lint);

        JavaRDD<Integer> rdd1 = sc.parallelize(Utils.range(0, 100));

        Integer sum = rdd.reduce(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        ///aggregate
        Function2<AvgCount, Integer, AvgCount> addAndCount =
            new Function2<AvgCount, Integer, AvgCount>() {
                @Override
                public AvgCount call(AvgCount v1, Integer v2) throws Exception {
                    v1.total += v2;
                    v1.num += 1;
                    return v1;
                }
            };
        Function2<AvgCount, AvgCount, AvgCount> combine =
            new Function2<AvgCount, AvgCount, AvgCount>() {
                @Override
                public AvgCount call(AvgCount v1, AvgCount v2) throws Exception {
                    v1.total += v2.total;
                    v1.num += v2.num;
                    return v1;
                }
            };

        AvgCount initial = new AvgCount(0, 0);
        AvgCount result = rdd.aggregate(initial, addAndCount, combine);


        System.out.println("-----------Action: reduce countByValue takeOrdered takeSample aggregate----------");
        System.out.println("----------reduce-----------");
        System.out.println(sum);
        System.out.println("----------countByValue-----------");
        System.out.println(rdd.countByValue());

        System.out.println("----------takeOrdered-----------");
        System.out.println(rdd.takeOrdered(7));

        System.out.println("----------takeSample-----------");
        System.out.println(rdd.takeSample(true, 10, 10));
        System.out.println(rdd.takeSample(true, 10, 10));

        System.out.println("----------takeSample-----------");
        System.out.println(rdd1.takeSample(false, 10, 1));
        System.out.println(rdd1.takeSample(false, 10, 1));

        System.out.println("----------aggregate-----------");
        System.out.println("total: " + result.total + " num: " + result.num);
        System.out.println("AvgCount: " + result.avg());

    }

}


