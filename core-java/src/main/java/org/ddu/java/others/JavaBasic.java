package org.ddu.java.others;

public class JavaBasic {
	public static void main(String[] args) {
		test1();
	}

	/**
	 * += 隐式的将加操作的结果类型强制转换为持有结果的类型。
	 * 如果两这个整型相加，如 byte、short 或者 int，首
	 * 先会将它们提升到 int 类型，然后在执行加法操作。
	 * 如果加法操作的结果比 a 的最大值要大，则 a+b 会出现
	 * 编译错误，但是 a += b 没问题
	 */
	private static void test1() {
		byte a = 127;
		byte b = 127;
		
		// 编译错：can not convert from int to byte.
		//b = a + b; 
		b += a;
		System.out.println("b value: " + b);
		System.out.println("b byte : " + Integer.toBinaryString(b));
		
		/**
		 * 5的原码： 101
		 * 5的反码： 11111111111111111111111111111010
		 * 5的补码： 11111111111111111111111111111011  (反码+1)
		 * -5 = 5的补码
		 */
		int i = 5;
		System.out.println(" 5的原码    : " + Integer.toBinaryString(i));
		System.out.println("-5=5的反码+1: " + Integer.toBinaryString(-i));
	}
}
