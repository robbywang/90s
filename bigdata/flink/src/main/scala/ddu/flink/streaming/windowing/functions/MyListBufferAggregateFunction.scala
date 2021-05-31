package ddu.flink.streaming.windowing.functions

import ddu.flink.util.SourceEventData
import org.apache.flink.api.common.functions.AggregateFunction

import scala.collection.mutable.ListBuffer

class MyListBufferAggregateFunction extends AggregateFunction[SourceEventData, ListBuffer[SourceEventData], List[SourceEventData]] {
  override def createAccumulator(): ListBuffer[SourceEventData] = ListBuffer[SourceEventData]()

  override def add(in: SourceEventData, acc: ListBuffer[SourceEventData]): ListBuffer[SourceEventData] = {
    println(s"-------- ROBBY ---------- MyListBufferAggregateFunction.add -  in: $in, acc: $acc")
    acc += in
  }

  override def getResult(acc: ListBuffer[SourceEventData]): List[SourceEventData] = {
    println(s"-------- ROBBY ---------- MyListBufferAggregateFunction.getResult -  acc: $acc")
    acc.toList
  }

  override def merge(acc: ListBuffer[SourceEventData], acc1: ListBuffer[SourceEventData]): ListBuffer[SourceEventData] = {
    println(s"-------- ROBBY ---------- MyListBufferAggregateFunction.merge -  acc: $acc, acc1: $acc1")
    acc ++= acc1
  }
}
