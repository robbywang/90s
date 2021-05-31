package ddu.flink.util;

import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

public enum WindowsTypeEnum {
  /**
   * 时间以事件中的时间为准，窗口区间是固定的.
   * window_start: 0, end: 5.
   * window_start: 5, end: 10.
   */
  TUMBLING_EVENT_TIME_WINDOWS {
    @Override
    public WindowAssigner<Object, TimeWindow> WindowAssigner(long size, long offset, long slide) {
      return TumblingEventTimeWindows.of(Time.milliseconds(size), Time.milliseconds(offset));
    }
  },
  /**
   * 时间以系统时间为准，窗口区间是固定的.
   * window_start: 1577339341150, end: 1577339341155
   * window_start: 1577339342685, end: 1577339342690.
   */
  TUMBLING_PROCESSING_TIME_WINDOWS {
    @Override
    public WindowAssigner<Object, TimeWindow> WindowAssigner(long size, long offset, long slide) {
      return TumblingProcessingTimeWindows.of(Time.milliseconds(size), Time.milliseconds(offset));
    }
  },
//  TumblingEventTimeWindowsWithOffset,

  /**
   * 输入：
   * a,1,1
   * a,2,1
   * a,3,1
   * a,4,1
   * a,5,1
   * a,6,1
   * a,7,1
   * a,8,1
   * a,9,1
   * a,10,1
   *
   * 输出：
   * window_start: -2, end: 3.
   * List(SourceEventData(a,1,1), SourceEventData(a,2,1))
   *
   * window_start: 0, end: 5.
   * List(SourceEventData(a,1,1), SourceEventData(a,2,1), SourceEventData(a,3,1), SourceEventData(a,4,1))
   *
   * window_start: 2, end: 7.
   * List(SourceEventData(a,2,1), SourceEventData(a,3,1), SourceEventData(a,4,1), SourceEventData(a,5,1), SourceEventData(a,6,1))
   */
  SLIDING_EVENT_TIME_WINDOWS {
    @Override
    public WindowAssigner<Object, TimeWindow> WindowAssigner(long size, long offset, long slide) {
      return SlidingEventTimeWindows.of(Time.milliseconds(size), Time.milliseconds(slide));
    }
  },
  SLIDING_PROCESSING_TIME_WINDOWS {
    @Override
    public WindowAssigner<Object, TimeWindow> WindowAssigner(long size, long offset, long slide) {
      return SlidingProcessingTimeWindows.of(Time.milliseconds(size), Time.milliseconds(offset));
    }
  },
//  SlidingProcessingTimeWindowsWithOffset,

  /**
   * 窗口是非固定的
   * 输入：
   * a,1,1
   * a,2,1
   * a,3,1
   * a,4,1
   * a,10,1
   * 输出：
   *  window_start: 1, end: 9.
   * List(SourceEventData(a,1,1), SourceEventData(a,2,1), SourceEventData(a,3,1), SourceEventData(a,4,1))
   */
  EVENT_TIME_SESSION_WINDOWS_WITH_GAP {
    @Override
    public WindowAssigner<Object, TimeWindow> WindowAssigner(long size, long offset, long slide) {
      return EventTimeSessionWindows.withGap(Time.milliseconds(size));
    }
  };
//  EventTimeSessionWindowsWithDynamicGap,
//  ProcessingTimeSessionWindows;

  public abstract WindowAssigner<Object, TimeWindow> WindowAssigner(long size, long offset, long slide);

}
