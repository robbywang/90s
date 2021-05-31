package org.ddu.algorithm;

public class BigLittleSort {

	public static void main(String[] args) {
		char[] input = {'s', 'A', 'B', 'c', 'd', 'E', 'f', 'G' };
		sort(input);
		System.out.println(input);
	}

	// [A,b,c,D,E,f,g]
	private static void sort(char[] input) {
		int littleNum = 0;
		for (char c : input) {
			if (isLittle(c)) {
				littleNum++;
			}
		}

		int i = 0, j = input.length - 1;
		int littleMaxIndex = littleNum-1;

		while (i <= littleMaxIndex) {
			while (i <= littleMaxIndex) {
				if (isLittle(input[i])) {
					i++;
				} else {
					break;
				}
			}

			while (j >= littleNum) {
				if (isBig(input[j])) {
					j--;
				} else {
					break;
				}
			}

			char temp = input[i];
			for (int k = i; k < littleMaxIndex; k++) {
				input[k] = input[k + 1];
			}

			input[littleMaxIndex] = input[j];
			input[j] = temp;	
			littleMaxIndex--;
		}
	}

	private static int findBigInLittle(char[] input, int li, int lj) {
		while (li < lj) {
			if (isLittle(input[li])) {
				li++;
			} else {
				break;
			}
		}
		return li;
	}
	
	private static boolean isLittle(char c) {
		if (c >= 'a' && c <= 'z') {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isBig(char c) {
		if (c >= 'A' && c <= 'Z') {
			return true;
		} else {
			return false;
		}
	}
}
