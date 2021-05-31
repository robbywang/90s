package ddu.flink.streaming.windowing.functions

import ddu.flink.util.SourceEventData
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.collection.mutable.ListBuffer

class MyProcessWindowFunction extends ProcessWindowFunction[SourceEventData, List[SourceEventData], Tuple, TimeWindow] {

  override def process(key: Tuple, context: Context, elements: Iterable[SourceEventData], out: Collector[List[SourceEventData]]): Unit = {
    println(s"-------- ROBBY ---------- 调用ProcessWindowFunction for key: $key")
    val currentProcessingTime = context.currentProcessingTime
    val currentWatermark = context.currentWatermark
    val w_start = context.window.getStart
    val w_end = context.window.getEnd
    println(s"-------- ROBBY ---------- currentProcessingTime: $currentProcessingTime, currentWatermark: $currentWatermark")
    println(s"-------- ROBBY ---------- window_start: $w_start, end: $w_end.")

    val sourceEventDataList: ListBuffer[SourceEventData] = ListBuffer()

    elements.foreach(sourceEventData => {
      sourceEventDataList += sourceEventData
    })

    out.collect(sourceEventDataList.toList)
  }
}

object MyProcessWindowFunction
