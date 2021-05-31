package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lideyou on 16/5/19.
 * Transformations: map sample
 */

public class TestMap {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.usage("TestMap"));
            System.exit(0);
        }

        // Initialize SparkContext
        String appName = "java_demo_testMap";
        SparkConf conf = new SparkConf().setAppName(appName);
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> lint = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> rdd = sc.parallelize(lint);
        JavaRDD<Integer> rdd1 = sc.parallelize(Utils.range(0, 100), 3);

        System.out.println("-----------Transformations: map sample----------");
		System.out.println(String.format(
				"---------------------\nrdd1: %s\nrdd2: %s", rdd.collect(),
				rdd1.collect()));

        JavaRDD<Integer> result = rdd.map(new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer v1) throws Exception {
                return v1 * v1;
            }
        });
        
        JavaRDD<Integer> resultLambda = rdd.map(a -> a*a);
        
        System.out.println("----------map-----------");
        System.out.println(result.collect());
        System.out.println(resultLambda.collect());

        System.out.println("----------sample-----------");
        System.out.println(rdd1.sample(true, 0.1, 10).collect());
        System.out.println(rdd1.sample(true, 0.1, 10).collect());
        System.out.println(rdd1.sample(true, 0.2, 10).collect());
        System.out.println(rdd1.sample(true, 0.2, 10).collect());
        System.out.println("----------sample-----------");
        System.out.println(rdd1.sample(false, 0.1).collect());
        System.out.println(rdd1.sample(false, 0.1).collect());
        
        JavaRDD<Integer> sampleRDD = sc.parallelize(Arrays.asList(1,2,3,4));
        System.out.println("---------- withReplacement sample-----------");
        System.out.println(sampleRDD.sample(false, 0.5).collect());
        System.out.println(sampleRDD.sample(true, 0.5).collect());
        
        sc.close();
    }

}
