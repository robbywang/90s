package ddu.flink.streaming.windowing.functions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import ddu.flink.util.SourceEventData
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.watermark.Watermark

/**
  * This generator generates watermarks assuming that elements arrive out of order,
  * but only to a certain degree. The latest elements for a certain timestamp t will arrive
  * at most n milliseconds after the earliest elements for timestamp t.
  */
class BoundedOutOfOrdernessGenerator extends AssignerWithPeriodicWatermarks[SourceEventData] {

  var maxOutOfOrderness = 1000L //

  var currentMaxTimestamp: Long = _

  def this(maxOutOfOrderness: Long) = {
    this()
    this.maxOutOfOrderness = maxOutOfOrderness
  }

  override def extractTimestamp(element: SourceEventData, previousElementTimestamp: Long): Long = {
    val timestamp = element.eventTime
    println(s"-------- ROBBY ---------- extractTimestamp -  element: $element, previousElementTimestamp: $previousElementTimestamp")
    currentMaxTimestamp = math.max(timestamp, currentMaxTimestamp)
    timestamp
  }

  override def getCurrentWatermark(): Watermark = {
    // return the watermark as current highest timestamp minus the out-of-orderness bound
    val currentWatermark = currentMaxTimestamp - maxOutOfOrderness
    val invokeTime = LocalDateTime.now().toString
    println(s"-------- ROBBY ----------水位线调用时间: $invokeTime, getCurrentWatermark - currentWatermark:$currentWatermark, currentMaxTimestamp: $currentMaxTimestamp")
    new Watermark(currentWatermark)
  }
}

/**
  * This generator generates watermarks that are lagging behind processing time by a fixed amount.
  * It assumes that elements arrive in Flink after a bounded delay.
  */
class TimeLagWatermarkGenerator extends AssignerWithPeriodicWatermarks[SourceEventData] {

  val maxTimeLag = 5000L // 5 seconds

  override def extractTimestamp(element: SourceEventData, previousElementTimestamp: Long): Long = {
    element.eventTime
  }

  override def getCurrentWatermark(): Watermark = {
    // return the watermark as current time minus the maximum time lag
    new Watermark(System.currentTimeMillis() - maxTimeLag)
  }
}
