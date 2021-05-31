package ddu.flink.util

import org.junit.Test

class UtilTest {

  @Test
  def testSourceTypeEnum(): Unit = {
    val kafkaId1 = SourceTypeEnum1.KAFKA.id
    val maxId1 = SourceTypeEnum1.maxId
    val socket1 = SourceTypeEnum1.withName("SOCKET")
    println(s"kafkaId1: $kafkaId1 , SourceTypeEnumMaxId: $maxId1 , socket1: $socket1")

    /*
    kafkaId1: 2 , SourceTypeEnumMaxId: 3 , socket1: SOCKET
     */

    val kafkaId2 = SourceTypeEnum2.KAFKA.id
    val maxId2 = SourceTypeEnum2.maxId
    val socket2 = SourceTypeEnum2.withName("test")
    println(s"kafkaId2: $kafkaId2 , SourceTypeEnumMaxId2: $maxId2 , socket2: $socket2")

    println(SourceTypeEnum2.LOCAL.id)
    println(SourceTypeEnum2.SOCKET.id)
    println(SourceTypeEnum2.KAFKA.id)

    /**
      * kafkaId2: 101 , SourceTypeEnumMaxId2: 102 , socket2: test
      * 0
      * 100
      * 101
      */
  }

}


object SourceTypeEnum1 extends Enumeration {
  type sourceType = Value
  val LOCAL, SOCKET, KAFKA = Value
}

object SourceTypeEnum2 extends Enumeration {
  type sourceType = Value
  val LOCAL = Value
  val SOCKET= Value(100, "test")
  val KAFKA = Value
}