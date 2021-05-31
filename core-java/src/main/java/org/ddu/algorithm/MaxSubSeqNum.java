package org.ddu.algorithm;

public class MaxSubSeqNum {

	/**
	 * Give array: 1 2 3
	 * The sequence of 2 chars:
	 * 12
	 * 13
	 * 21
	 * 23
	 * 31
	 * 32
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(String.format("%s - %s : %d", 3, 1, calc(3, 1)));
		System.out.println(String.format("%s - %s : %d", 3, 2, calc(3, 2)));
		System.out.println(String.format("%s - %s : %d", 5, 2, calc(5, 2)));
		System.out.println(String.format("%s - %s : %d", 5, 3, calc(5, 3)));
	}
	
	private static int calc(int arrayLength, int subCharNum) {
		if (subCharNum == 1) {
			return arrayLength;
		} else {
			return arrayLength * calc(arrayLength-1, subCharNum-1);
		}
	}
}
