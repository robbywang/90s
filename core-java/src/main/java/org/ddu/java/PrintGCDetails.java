package org.ddu.java;

import java.util.ArrayList;
import java.util.List;

public class PrintGCDetails {

	public static void main(String[] args) {
		List<byte[]> list = new ArrayList<byte[]>();
		while (true) {
			System.out.print(".");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			byte[] b = new byte[10240];
			list.add(b);
		}
		
	}
}
