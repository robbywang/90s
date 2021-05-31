package ddu.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
//import org.codehaus.janino.Java;

/**
 * Created by lideyou on 16/5/29.
 */
public class StorageFormat {
    public static void main(String[] args) {
        // Initialize SparkContext
        String appName = "java_demo_testMap";
        String dataFile = "file:///hdkz/data/spark/data/people.json";
        String saveText = "file:///hdkz/data/spark/data/saveText.txt";
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName(appName);
        JavaSparkContext sc = new JavaSparkContext(conf);


        System.out.println("-----------text----------");
        JavaRDD<String> input = sc.textFile(dataFile);
        System.out.println(input.collect());
        // input.saveAsTextFile(saveText);

        JavaRDD<Person> result = input.mapPartitions(new ParseJson());

        System.out.println("-----------Read json----------");
        for (Person person : result.collect()) {
            System.out.println(person.getName() + ": " + person.getAge());

        }

        System.out.println("-----------Write json----------");
        JavaRDD<String> formatted = result.mapPartitions(new WriteJson());
        formatted.saveAsTextFile(saveText);


    }
}
