/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ddu.spark.rdd;

import scala.Tuple2;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

// spark-submit --master yarn --executor-memory 512m --class ddu.spark.rdd.JavaWordCount target/ddu-spark.jar

public final class JavaWordCount {

  private static final Pattern SPACE = Pattern.compile(" ");

  public static void main(String[] args) throws Exception {

    if (args.length < 1) {
      System.err.println("Usage: JavaWordCount <file>");
      System.exit(1);
    }

    SparkSession spark = SparkSession
        .builder()
        .config("mapreduce.input.fileinputformat.input.dir.recursive", true) // 递归读取目录.
        .appName("JavaWordCount")
        .getOrCreate();

//    spark.sparkContext();

    JavaRDD<String> lines = spark.read().textFile(args[0]).javaRDD();

//    lines.collect().forEach(System.out::println);

    JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
      @Override
      public Iterator<String> call(String s) {
        return Arrays.asList(SPACE.split(s)).iterator();
      }
    });

    JavaPairRDD<String, Integer> ones = words.mapToPair(
        new PairFunction<String, String, Integer>() {
          @Override
          public Tuple2<String, Integer> call(String s) {
            return new Tuple2<>(s, 1);
          }
        });

    JavaPairRDD<String, Integer> counts = ones.reduceByKey(
        new Function2<Integer, Integer, Integer>() {
          @Override
          public Integer call(Integer i1, Integer i2) {
            return i1 + i2;
          }
        });

    List<Tuple2<String, Integer>> output = counts.collect();

    System.out.println("--- collect data and print. ---");

    for (Tuple2<?, ?> tuple : output) {
      System.out.println(tuple._1() + ": " + tuple._2());
    }
    spark.stop();
  }
}
