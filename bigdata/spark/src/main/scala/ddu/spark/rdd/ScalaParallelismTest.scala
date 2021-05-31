package ddu.spark.rdd

import java.net.InetAddress
import java.util.regex.Pattern

import org.apache.spark.TaskContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.util.CollectionAccumulator

import scala.collection.mutable.ArrayBuffer

object ScalaParallelismTest {

  val SPACE: Pattern = Pattern.compile(" ")

  def main(args: Array[String]): Unit = {

    val p1 = args(0)
    println(s"p1: $p1")

    /*
    数字测试
     */
    val numArrayBuffer = ArrayBuffer[Int]()
    for (i <- 0 to 100) {
      numArrayBuffer += i
    }
    println(s"data1: $numArrayBuffer")

    val spark = SparkSession
      .builder
      .appName("ScalaParallelismTest")
      .getOrCreate()

    val sc = spark.sparkContext
    val collectionAccumulator: CollectionAccumulator[String] = sc.collectionAccumulator

    val distData = sc.parallelize(numArrayBuffer, p1.toInt)
    val sum: Int = distData.reduce((a, b) => {
      val taskCtx = TaskContext.get
      val threadId = Thread.currentThread().getId
      val threadName = Thread.currentThread().getName
      var taskId = 0L
      if (taskCtx != null) {
        taskId = taskCtx.taskAttemptId()
      }

      val addr = InetAddress.getLocalHost
      val ip = addr.getHostAddress.toString
      val info = ip + ":" + threadName + "(" + threadId + ")" + ":" + taskId
      collectionAccumulator.add(info)

      println(s"myReduce thread id: $threadId, ip: $ip")
      a + b
    })

    println("--- printing the sum ---")
    println(s"sum: $sum")

    /*
      获取上个任务的执行信息.
     */
    val taskDetails = sc.parallelize(collectionAccumulator.value.toArray)
      .map(info => {
        val array: Array[String] = info.toString.split(":")
        println(array)
        // ip,threadId,taskId.
        (array(0), array(1), array(2))
      }).distinct()
    val collectedTaskDetails = taskDetails.collect()
    println("--- printing the collectedTaskDetails ---")
    collectedTaskDetails.foreach(println)

    /*
     集群总 thread 信息
     */
    val threadsRdd = taskDetails
      .map(a => {
        a._1 + " | " + a._2
      })
      .distinct()

    val collectedThreadsRdd = threadsRdd.collect()

    println("--- printing the collectedThreadsRdd ---")
    collectedThreadsRdd.foreach(println)
    println("--- printing the threadsCount ---")
    println(collectedThreadsRdd.length)

    /*
     集群总 task 数
     */
    val tasksRdd = taskDetails.map(a => {
      a._3
    })
      .distinct()
    val collectedTaskRdd = tasksRdd.collect()

    println("--- printing the collectedTaskRdd ---")
    collectedTaskRdd.foreach(println)
    println("--- printing the tasksCount ---")
    println(collectedTaskRdd.length)

    //    val execInfo = taskDetails.map(a => {
    //      val ip = a._1
    //      val thread = a._2
    //      val taskId = a._3
    //      Map(s"$ip($thread)" -> taskId)
    ////      Map(ip -> (thread -> taskId))
    ////      (a._1 + "-" + a._2, ab + a._3)
    //    })
    //
    //    val distExecInfo = execInfo.distinct()
    //    println("--- printing the taskDetails ---")
    //    taskDetails.collect().foreach(println)
    //    println("--- printing the execInfo ---")
    //    execInfo.collect().foreach(println)
    //
    //    val execInfo2 = taskDetails.map(a => {
    //      val ip = a._1
    //      val thread = a._2
    //      val taskId = a._3
    //      val list = List[String]()
    //      (ip, Map(thread -> (taskId)  ))
    //    }).reduceByKey((map1, map2) => {
    //
    //      // 合并两个Map
    //      map1 ++ map2.map(t => t._1 -> (t._2 + map1.get(t._1)))
    //    })
    //
    ////    val distExecInfo2 = execInfo2.distinct()
    //    println("--- printing the execInfo2 ---")
    //    execInfo2.collect().foreach(println)
    //  }

    //  def myReduce(a: Int, b: Int): Int = {
    //    val threadId = Thread.currentThread().getId
    //    val addr = InetAddress.getLocalHost
    //    val ip = addr.getHostAddress.toString
    //    val hostName = addr.getHostName.toString
    //    val info = String.format("ip: %s, threadId: %s", ip, threadId)
    //
    //    println(s"myReduce thread id: $threadId, ip: $ip, hostName: $hostName")
    //    collectionAccumulator.add(info)
    //    a + b
    //  }
  }
}
