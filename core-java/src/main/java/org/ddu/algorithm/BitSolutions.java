package org.ddu.algorithm;

/**
 * 编写一个函数，输入是一个无符号整数，返回其二进制表达式中 数字位数为 ‘1’ 的个数（也被称为汉明重量）。
 */
public class BitSolutions {

  public static void main(String[] args) {
    int n = 5;
    System.out.println(String.format("%d -> %s", n, Integer.toBinaryString(n)));
    BitSolutions instance = new BitSolutions();
    System.out.println(String.format("hammingWeight : %s", instance.hammingWeight(n)));
    System.out.println(String.format("reverseBits : %s", instance.reverseBits(n)));

    byte[] bytes = "11111111111111111111111111111101".getBytes();

  }

  // you need to treat n as an unsigned value
  public int hammingWeight(int n) {
    int mode = 1;
    int sum = 0;
    for (int i = 1; i <= 32; i++) {
      if ((mode & n) != 0) {
        sum++;
      }
      mode <<= 1;
    }
    return sum;
  }

  // you need treat n as an unsigned value
  public int reverseBits(int n) {
    int ans = 0;
    for (int bitsSize = 31; n != 0; n = n >>> 1, bitsSize--) {
      ans += (n & 1) << bitsSize;
    }
    return ans;
  }
}
