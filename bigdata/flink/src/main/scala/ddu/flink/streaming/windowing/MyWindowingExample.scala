package ddu.flink.streaming.windowing

import ddu.flink.streaming.windowing.functions.{BoundedOutOfOrdernessGenerator, MyProcessWindowFunction, TimestampWaterMarker}
import ddu.flink.util.SourceTypeEnum._
import ddu.flink.util.{SourceEventData, SourceTypeEnum, WindowsTypeEnum}
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  * nc -l <port> to start a SocketServer
  */
object MyWindowingExample {

  // 测试变量
  var sourceType: SourceTypeEnum.Value = SourceTypeEnum.LOCAL
  var timeCharacteristic = TimeCharacteristic.EventTime
  var windowsType: WindowsTypeEnum = WindowsTypeEnum.TUMBLING_EVENT_TIME_WINDOWS

  // 测试常量
  val WINDOW_SIZE = 5L
  val OFFSET = 0L
  val SLIDE = 2L
  val HOST_NAME = "localhost"
  val PORT: Int = 10000
  val AUTO_WATERMARK_INTERVAL: Long = 30 * 1000L
  val MAX_OUT_OF_ORDERNESS = 1L
  val PARALLELISM = 1

  def main(args: Array[String]): Unit = {

    initJobFromArgs(args)

    println(s"--------------   Test Configuration ---------------")
    println(s"sourceType: $sourceType | timeCharacteristic: $timeCharacteristic | windowsType: $windowsType")

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(timeCharacteristic)
    // 默认： 200ms
    env.getConfig.setAutoWatermarkInterval(AUTO_WATERMARK_INTERVAL)
    env.setParallelism(PARALLELISM)

    val source: DataStream[SourceEventData] = createDataStream(env, sourceType)

    val aggregated: DataStream[List[SourceEventData]] = source
      .assignTimestampsAndWatermarks(
        new BoundedOutOfOrdernessGenerator(MAX_OUT_OF_ORDERNESS)
      )
      .keyBy(0)
      .window(windowsType.WindowAssigner(WINDOW_SIZE, OFFSET, SLIDE))
      //      .window(EventTimeSessionWindows.withGap(Time.milliseconds(3000L)))
      //      .aggregate(new MyListBufferAggregateFunction())
      //      .timeWindow(Time.milliseconds(3000L))
      .process(new MyProcessWindowFunction)

    aggregated.print()
    env.execute()
  }

  /**
    * 通过传入的参数，来初始化测试的类型.
    *
    * @param args
    */
  private def initJobFromArgs(args: Array[String]): Unit = {
    val params = ParameterTool.fromArgs(args)

    if (params.has("sourceType")) {
      sourceType = SourceTypeEnum.withName(params.get("sourceType"))
    }

    if (params.has("timeCharacteristic")) {
      timeCharacteristic = TimeCharacteristic.valueOf(params.get("timeCharacteristic"))
    }

    if (params.has("windowsType")) {
      windowsType = WindowsTypeEnum.valueOf(params.get("windowsType"))
    }

  }

  private def createDataStream(env: StreamExecutionEnvironment, sourceTypeEnum: SourceTypeEnum.sourceType): DataStream[SourceEventData] = {
    sourceTypeEnum match {
      case SOCKET =>
        env.socketTextStream(HOST_NAME, PORT, '\n')
          .map(line => {
            val params: Array[String] = line.split(",")
            params
          })
          .filter(_.length == 3)
          .map(params => SourceEventData(params(0), params(1).toLong, params(2).toInt))

      case KAFKA =>
        null
      case _ =>
        val input = List(
          SourceEventData("a", 1L, 1),
          SourceEventData("b", 8L, 1)
        )
        env.addSource(
          new SourceFunction[SourceEventData]() {
            override def run(ctx: SourceContext[SourceEventData]): Unit = {
              input.foreach(value => ctx.collect(value))
            }

            override def cancel(): Unit = {}
          })
    }
  }

  private def eventWaterMarker[T](
                                   timestampExtractor: T => Long,
                                   maxAllowedTimeExceeded: Long
                                 ): TimestampWaterMarker[T] = {
    new TimestampWaterMarker[T](
      3L,
      maxAllowedTimeExceeded,
      1L,
      timestampExtractor
    )
  }
}
