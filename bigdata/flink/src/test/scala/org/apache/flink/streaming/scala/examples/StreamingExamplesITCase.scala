/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.streaming.scala.examples

import ddu.flink.streaming.windowing.MyWindowingExample
import org.apache.flink.streaming.scala.examples.windowing.{SessionWindowing, SocketWindowWordCount, WindowWordCount}
import org.junit.Test

/**
  * Integration test for streaming programs in Scala examples.
  */
class StreamingExamplesITCase {

  @Test
  def testSessionWindowing(): Unit = {
    SessionWindowing.main(Array())
  }

  @Test
  def testWindowWordCount(): Unit = {
    val windowSize = "2"
    val slideSize = "1"

    WindowWordCount.main(Array(
      //      "--input", textPath,
      //      "--output", resultPath,
      "--window", windowSize,
      "--slide", slideSize
    ))
  }

  @Test
  def testMyWindowingExample(): Unit = {
    MyWindowingExample.main(Array(
      "--sourceType", "SOCKET",
//      "--windowsType", "TUMBLING_EVENT_TIME_WINDOWS"
//      "--windowsType", "TUMBLING_PROCESSING_TIME_WINDOWS"
//      "--windowsType", "SLIDING_EVENT_TIME_WINDOWS"
      "--windowsType", "EVENT_TIME_SESSION_WINDOWS_WITH_GAP"
    ))
  }

  @Test
  def testSocketWindowWordCount(): Unit = {
    SocketWindowWordCount.main(Array(
      "--port", "10000"
    ))
  }

}
