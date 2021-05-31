package org.ddu.algorithm;

public class SumNArray {

	public static void main(String[] args) {
		calc(15);
	}
	
	public static void calc(int n) {
		int sum = 1;
		int min = 1;
		int max = 1;
		
		while (min < n/2-1) {
			if (sum == n) {
				for (int k=min; k<=max; k++) {
					System.out.print(k + " ");
				}
				System.out.println();
				
				sum = sum -min;
				min++;
				max++;
				sum = sum + max;
			}
			
			if (sum > n) {
				sum = sum -min;
				min ++;
			}
			
			if (sum < n) {
				max ++;
				sum = sum + max; 
			}
		}
	}
}
