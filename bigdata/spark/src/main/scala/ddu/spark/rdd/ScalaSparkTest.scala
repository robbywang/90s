package ddu.spark.rdd

import java.util.regex.Pattern

import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

object ScalaSparkTest {

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
      .appName("ScalaSparkTest")
      .getOrCreate()

    val sc = spark.sparkContext

    val distData = sc.parallelize(numArrayBuffer, 10)
    val sum: Int = distData.reduce((a, b) => a + b)
    println(s"sum: ${sum}")

    /*
    KvPair 测试
     */
    val stringArray = Array("a", "a", "a", "a", "b", "b", "b", "c", "c", "d")
    val data2KvRdd = sc
      .parallelize(stringArray, 2)
      .map(e => (e, 1))

    data2KvRdd.persist()

    val reduceByKeyRdd = data2KvRdd
      .reduceByKey((a, b) => a + b)
    println("--- reduceByKeyRdd ---")
    reduceByKeyRdd.collect().foreach(println)

    val aggregateByKey = data2KvRdd.aggregateByKey(0)((a,b) => a+b, (a,b) => a*b)
    println("--- aggregateByKey ---")
    aggregateByKey.collect().foreach(println)

    val sortByKey = data2KvRdd.sortByKey(false)
    println("--- sortByKey ---")
    sortByKey.collect().foreach(println)


    /*
    join
     */
    val joinArray1 = Array("a", "b", "c", "d")
    val joinRdd1= sc
      .parallelize(joinArray1, 2)
      .map(e => (e, 1))
    val joinArray2 = Array("a", "b", "c", "e")
    val joinRdd2= sc
      .parallelize(joinArray2, 2)
      .map(e => (e, 1))
    val joinedRdd = joinRdd1.join(joinRdd2)
    println("--- joinedRdd ---")
    joinedRdd.sortByKey().collect().foreach(println)
    println("--- leftOuterJoin ---")
    joinRdd1.leftOuterJoin(joinRdd2).sortByKey().collect().foreach(println)
    println("--- rightOuterJoin ---")
    joinRdd1.rightOuterJoin(joinRdd2).collect().foreach(println)
    println("--- fullOuterJoin ---")
    joinRdd1.fullOuterJoin(joinRdd2).collect().foreach(println)

    /*
     两个DataSet操作
    */

  }
}
