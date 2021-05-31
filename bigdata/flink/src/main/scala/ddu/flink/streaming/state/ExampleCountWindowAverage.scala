package ddu.flink.streaming.state

import ddu.flink.streaming.state.functions.CountWindowAverageFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
// 这个要引入，否者会报 No implicits found for parameter evidence$11: TypeInformation
import org.apache.flink.streaming.api.scala._


object ExampleCountWindowAverage {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.fromCollection(List(
      (1L, 3L),
      (1L, 5L),
      (1L, 7L),
      (1L, 4L),
      (1L, 2L)
    )).keyBy(_._1)
      .flatMap(new CountWindowAverageFunction())
      .print()
    // the printed output will be (1,4) and (1,5)

    env.execute("ExampleManagedState")
  }
}
