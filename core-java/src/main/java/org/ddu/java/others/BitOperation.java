package org.ddu.java.others;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitOperation {

  private static final int BYTE_BITS = Byte.SIZE;
  private static final int SHORT_BITS = Short.SIZE;
  private static final int INT_BITS = Integer.SIZE;
  private static final int FLOAT_BITS = Float.SIZE;
  private static final int LONG_BITS = Long.SIZE;
  private static final int DOUBLE_BITS = Double.SIZE;

  private static final int int1 = (1 << 1);
  private static final int int10 = (10 >> 1);
  private static final int OR12 = (1 | 2);
  private static final int AND12 = (1 & 2);
  private static final int NOT12 = (1 ^ 2);
  private static final int NOT22 = (2 ^ 2);

  private static final Logger logger = LoggerFactory.getLogger(BitOperation.class);

  public static void main(String[] args) {
    logger.info("{}, {}, {}, {}, {}, {}", BYTE_BITS, SHORT_BITS, INT_BITS, FLOAT_BITS, LONG_BITS,
        DOUBLE_BITS);
    logger.info("{}, {}, {}, {}, {}, {}", int1, int10, OR12, AND12, NOT12, NOT22);
    getBooleanArray((byte) 2);
    System.out.println(byteToBit((byte) 127));

    System.out.println("--- binaryString() ---");
    binaryString();
  }

  /**
   * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
   */
  public static byte[] getBooleanArray(byte b) {
    byte[] array = new byte[8];
    for (int i = 7; i >= 0; i--) {
      array[i] = (byte) (b & 1);
      b = (byte) (b >> 1);
    }
    return array;
  }

  /**
   * 把byte转为字符串的bit
   */
  public static String byteToBit(byte b) {
    return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1)
        + (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) (
        (b >> 1) & 0x1)
        + (byte) ((b >> 0) & 0x1);
  }

  private static void binaryString() {

    String pp = "000000000000000000000000"; // 24位
    String np = "100000000000000000000000"; // 24位

    /*
     * Binary -> Int : 00000000000000000000000000000000 -> 0
     */
    String binaryString = pp + "00000000";
    binaryStringToIntValue(binaryString);

    /*
     * Binary -> Int : 00000000000000000000000000000110 -> 6
     */
    binaryString = pp + "00000110";
    binaryStringToIntValue(binaryString);

    binaryString = np + "00000010";
    binaryStringToIntValue(binaryString);

    /*
     * 结果是补码：
     * Int -> Binary : -2 -> 11111111111111111111111111111110
     *  = 取反               10000000000000000000000000000001
     *  +1                  10000000000000000000000000000010 (原码)
     */
    int intValue = -2;
    intValueToBinaryString(intValue);

    /*
     * Int -> Binary :  2147483646 -> 1111111111111111111111111111110
     * Int -> Binary : -2147483648 -> 10000000000000000000000000000000
     */
    intValueToBinaryString((int) Math.pow(2, 32) - 1);
    intValueToBinaryString((int) -Math.pow(2, 32));





    /**
     *  1、<< : 左移运算符:符号位不变，高位溢出，低位补0,对补码进行操作
     *  2、十进制--> 二进制补码 --->操作补码 --> 将补码取补码 -->十进制
     *  3、过程(二进制)：
     *    a、6 的二进制补码（32位）: 00000000 00000000 00000000 00000110;
     *    b、   向左偏移两位后补码 : 00000000 00000000 00000000 00011000;
     *    c、补码-->补码-->十进制：24
     *  4、打印结果为： 24
     */
    System.out.println("6 << 2 ===== " + (6 << 2));

    /**
     *  1、<< : 左移运算符: 符号位不变，高位溢出，低位补0,对补码进行操作
     *  2、十进制--> 二进制补码 --->操作补码 --> 将补码取补码 -->十进制
     *  3、过程(二进制)：
     *    a、6 的二进制补码（32位）: 11111111 11111111 11111111 11111010;
     *    b、向左偏移两位:          11111111 11111111 11111111 11101000;
     *    c、补码-->补码-->十进制：-24
     *  4、打印结果为： -24
     */
    System.out.println("-6 << 2 ===== " + (-6 << 2));

    /**
     *  1、>> : 右移运算符: 符号位不变，低位溢出，高位用符号位补高位,对补码进行操作
     *  2、十进制--> 二进制补码 --->操作补码 --> 将补码取补码 -->十进制
     *  3、过程(二进制)：
     *    a、6 的二进制补码（32位）:00000000 00000000 00000000 00000110;
     *    b、向左偏移两位：00000000 00000000 00000000 00000001;
     *    c、补码-->补码-->十进制：1
     *  4、打印结果为：1
     */
    System.out.println("6 >> 2 ===== " + (6 >> 2));

    /**
     *  1、>> : 右移运算符: 符号位不变，低位溢出，高位用符号位补高位,对补码进行操作
     *  2、十进制--> 二进制补码 --->操作补码 --> 将补码取补码 -->十进制
     *  3、过程(二进制)：
     *    a、-6 的二进制补码（32位）:11111111 11111111 11111111 11111010;
     *    b、向左偏移两位：11111111 11111111 11111111 11111110;
     *    c、补码-->补码-->十进制：-2
     *  4、打印结果为：-2
     */
    System.out.println("-6 >> 2 ===== " + (-6 >> 2));

    /**
     *  1、>>> : 无符号右移(无符号的意思是将符号位当作数字位看待): 低位溢出，高位补0,对补码进行操作
     *  2、十进制--> 二进制补码 --->操作补码 --> 十进制
     *  3、过程(二进制)：
     *    a、6 的二进制补码（32位）:00000000 00000000 00000000 00000110;
     *    b、向左偏移两位：00000000 00000000 00000000 00000001;
     *    c、补码-->十进制：1
     *  4、打印结果为：1
     */
    System.out.println("6 >>> 2 ===== " + (6 >>> 2));

    /**
     *  1、>>> : 无符号右移(无符号的意思是将符号位当作数字位看待): 低位溢出，高位补0,对补码进行操作
     *  2、十进制--> 二进制补码 --->操作补码 -->十进制
     *  3、过程(二进制)：
     *     a、-6 的二进制补码（32位）:11111111 11111111 11111111 11111010;
     *    b、向左偏移28位：00000000 00000000 00000000 00001111;
     *    c、补码-->十进制：15
     *  4、打印结果为：15
     */
    System.out.println("-6 >>> 28 ===== " + (-6 >>> 28));
  }

  /**
   * 打印二进制字符串代表的Int值.
   */
  private static void binaryStringToIntValue(String str) {
    System.out
        .println(String.format("Binary -> Int : %s -> %d", str, Integer.valueOf(str, 2)));
  }

  /**
   * 打印二进制字符串代表的Int值.
   */
  private static void intValueToBinaryString(int intValue) {
    System.out
        .println(String
            .format("Int -> Binary : %d -> %s", intValue, Integer.toBinaryString(intValue)));
  }


}
