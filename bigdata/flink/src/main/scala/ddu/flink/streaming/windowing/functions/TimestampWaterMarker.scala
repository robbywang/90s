package ddu.flink.streaming.windowing.functions

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.watermark.Watermark

/**
  *
  * @param maxOutOfOrderness      The (fixed) interval between the maximum seen timestamp seen in the records,
  *                               and that of the watermark to be emitted.
  * @param maxAllowedTimeExceeded the max allowed watermarker exceed the current event timestamp, this
  *                               parameter is used to trigger session window periodically, but also
  *                               control the watermarker not increment so fast.
  * @param incrementalStep        the step to increment teh water marker when no new data is found.
  */
class TimestampWaterMarker[T](maxOutOfOrderness: Long,
                              maxAllowedTimeExceeded: Long,
                              incrementalStep: Long,
                              extractFromField: T => Long)
  extends AssignerWithPeriodicWatermarks[T] {

  /**
    * The timestamp of the last emitted watermark.
    */
  private var latestEmittedWatermark = Long.MinValue

  /**
    * The current event timestamp seen so far.
    */
  private var latestEventTimestamp = Long.MinValue + maxOutOfOrderness

  /**
    * The current maximum timestamp seen so far.
    */
  private var latestMaxEventTimestamp = this.latestEventTimestamp

  /**
    * The last time to invoke the getCurrentWatermark() method to multiGet the watermark when no new data
    * arrived.
    */
  private var latestIdleInvokeTime: Long = this.latestEventTimestamp

  /**
    * indicate whether a new event is arrived
    */
  @volatile private var newEventArriveIndicator: Boolean = false

  override def getCurrentWatermark: Watermark = {
    val prevWatermark = latestEmittedWatermark
    val potentialWM = latestEventTimestamp - maxOutOfOrderness
    val maxWatermark = math.max(prevWatermark, potentialWM)
    if (this.newEventArriveIndicator) {
      return watermarkForNewEventArrive(prevWatermark, potentialWM)
    } else {
      if (maxWatermark == prevWatermark) {
        return watermarkForIdleTimeExceed(prevWatermark)
      }
    }
    null
  }

  /**
    * if last emitted watermark is still the same as before, so we believe that they may be
    *  no element emit, so in order to make sure that session window will be triggered periodically,
    *  we trying to increment the watermark, once that the watermarkInvoke time is much greater
    *  than the latestEmittedWatermark at the session window gap granularity, new watermark should
    *  be emitted.
    *  but this watermark arise just once manually after a new event comes,otherwise, nothing should do.
    *
    * @param prevWatermark
    * @return
    */
  private def watermarkForIdleTimeExceed(prevWatermark: Long): Watermark = {
    this.latestIdleInvokeTime += incrementalStep
    val idleInvokeInterval = this.latestIdleInvokeTime - this.latestMaxEventTimestamp
    val maxOutOfOrderWatermark = this.latestMaxEventTimestamp + maxOutOfOrderness

    // check if idleInvokeInterval is in the maxOutOfOrderness gap.
    if (this.latestIdleInvokeTime < maxOutOfOrderWatermark - incrementalStep) {
      println(
        s"[Robby] no new element emitted after $idleInvokeInterval, but still not " +
          s"greater than maxOutOfOrderness : $maxOutOfOrderness," +
          s" wait util the interval reaching the threshold to increasing the watermark."
      )
    } else if (this.latestIdleInvokeTime >= maxOutOfOrderWatermark - incrementalStep
      && this.latestIdleInvokeTime <= maxOutOfOrderWatermark + incrementalStep) {
      if (maxOutOfOrderWatermark > this.latestEmittedWatermark) {
        latestEmittedWatermark = maxOutOfOrderWatermark
        println(
          s"[Robby] no new element emitted after $idleInvokeInterval, that is already greater " +
            s"than maxOutOfOrderness : $maxOutOfOrderness, " +
            s"so emit new watermark at the time:$latestEmittedWatermark, " +
            s"the previous watermark is: $prevWatermark"
        )
        return new Watermark(latestEmittedWatermark)
      }
    } else {
      return watermarkForIdleTimeExceedMaxAllowedTime(
        prevWatermark,
        idleInvokeInterval
      )
    }
    null
  }

  private def watermarkForIdleTimeExceedMaxAllowedTime(
                                                        prevWatermark: Long,
                                                        idleInvokeInterval: Long
                                                      ): Watermark = {
    // using the latest event-timestamp + maxAllowedTime as the next watermark
    val exceedTimeWatermark = latestMaxEventTimestamp + maxAllowedTimeExceeded
    if (this.latestIdleInvokeTime < exceedTimeWatermark) {
      println(
        s"[Robby] no new element emitted after $idleInvokeInterval, but still not " +
          s"greater than maxAllowedTimeExceeded : $maxAllowedTimeExceeded," +
          s" wait util the interval reaching the threshold to increasing the watermark."
      )
    } else {
      if (exceedTimeWatermark > this.latestEmittedWatermark) {
        latestEmittedWatermark = exceedTimeWatermark
        println(
          s"[Robby] no new element emitted after $idleInvokeInterval, that is already greater " +
            s"than maxAllowedTimeExceeded : $maxAllowedTimeExceeded, " +
            s"so emit new watermark at the time:$latestEmittedWatermark, " +
            s"the previous watermark is: $prevWatermark"
        )
        return new Watermark(latestEmittedWatermark)
      }
    }
    null
  }

  private def watermarkForNewEventArrive(prevWatermark: Long,
                                         potentialWM: Long): Watermark = {
    if (potentialWM <= prevWatermark) {
      println(
        s"[Robby] a lower event-based timestamp: $latestEventTimestamp arrived," +
          s"and the watermark should be:$potentialWM," +
          s"but is lower than the curWatermark:$prevWatermark," +
          s"just ignore it."
      )
      newEventArriveIndicator = false
      null
    } else {
      // this guarantees that the watermark never goes backwards.
      latestEmittedWatermark = potentialWM
      newEventArriveIndicator = false
      println(s"[Robby] emit a event-based watermark:$latestEmittedWatermark")
      new Watermark(latestEmittedWatermark)
    }
  }

  override def extractTimestamp(element: T,
                                previousElementTimestamp: Long): Long = {
    val timestamp = extractFromField(element)
    this.newEventArriveIndicator = true
    this.latestEventTimestamp = timestamp
    this.latestMaxEventTimestamp =
      math.max(this.latestMaxEventTimestamp, timestamp)
    this.latestIdleInvokeTime =
      math.max(this.latestIdleInvokeTime, this.latestMaxEventTimestamp)
    println(
      s"[Robby] extractTimestamp from element with currentTimestamp: $latestEventTimestamp"
    )
    timestamp
  }
}
