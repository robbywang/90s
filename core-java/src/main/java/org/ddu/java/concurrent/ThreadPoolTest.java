package org.ddu.java.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolTest {

	private static ExecutorService executor = Executors.newFixedThreadPool(5);

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		ThreadPoolExecutor tpExecutor = (ThreadPoolExecutor) executor;
		System.out.println(tpExecutor.toString());
		tpExecutor.execute(new TestRunnable());
		System.out.println(tpExecutor.toString());
	}

	
	static class TestRunnable implements Runnable {
		@Override
		public void run() {
			System.out.println("Running...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
