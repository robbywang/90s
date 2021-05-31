package ddu.flink.util

object SourceTypeEnum extends Enumeration {
  type sourceType = Value
  val LOCAL, SOCKET, KAFKA = Value
}
