package ddu.spark.rdd

import java.util.regex.Pattern

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ScalaWordCount {

  val SPACE: Pattern = Pattern.compile(" ")

  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      System.err.println("Usage: JavaWordCount <file>")
      System.exit(1)
    }

    val spark = SparkSession
      .builder
      .appName("ScalaWordCount")
      .getOrCreate()

    val sc = spark.sparkContext

    /* 老的创建SparkContext方式.
    val conf = new SparkConf().setAppName("ScalaWordCount")
    val sc = new SparkContext(conf)
     */

    // 递归读取目录
    sc.hadoopConfiguration.set("mapreduce.input.fileinputformat.input.dir.recursive", "true")

    // Scala 专有API
    /*
    val wholeFiles = sc.wholeTextFiles((args(0)))
    val collectdWholeFiles = wholeFiles.collect()
    collectdWholeFiles.foreach(println)
     */

    //    val lines: RDD[String] = spark.read.text(args(0)).rdd
    val lines = sc.textFile(args(0), 1)
    val result: RDD[(String, Int)] = lines.
      flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey((a, b) => a + b)

    println("--- collect data and print. ---")
    val collectedResult = result.collect()
    collectedResult.foreach(println)
  }

}
