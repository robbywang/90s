package ddu.spark.rdd

import java.util.regex.Pattern

import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

object YarnSchedulerTest {

  val SPACE: Pattern = Pattern.compile(" ")

  def main(args: Array[String]): Unit = {

    /*
    数字测试
     */
    val numArrayBuffer = ArrayBuffer[Int]()
    for (i <- 0 to 100) {
      numArrayBuffer += i
    }
    println(s"data1: ${numArrayBuffer}")

    val spark = SparkSession
      .builder
      .appName("YarnSchedulerTest")
      .getOrCreate()

    val sc = spark.sparkContext

    val distData = sc.parallelize(numArrayBuffer, 2)
//    val sum: Int = distData.reduce((a, b) => a + b)
    val sum: Int = distData.reduce(myReduce)
    println(s"sum: ${sum}")
  }

    def myReduce(a: Int, b: Int): Int = {
      val threadId = Thread.currentThread().getId
      println(s"$threadId is sleeping...")
      Thread.sleep(30*1000)
      a + b
    }
}
