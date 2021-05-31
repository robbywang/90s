package org.ddu.java.others;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class LoopMain {

	public static void main(String[] args) {

	}

	public int iteratorMaxInteger(List<Integer> integers) {
		int max = Integer.MIN_VALUE;
		for (Iterator<Integer> it = integers.iterator(); it.hasNext();) {
			max = Integer.max(max, it.next());
		}
		return max;
	}

	public int forEachLoopMaxInteger(List<Integer> integers) {
		int max = Integer.MIN_VALUE;
		for (Integer n : integers) {
			max = Integer.max(max, n);
		}
		return max;
	}

	public int forMaxInteger(List<Integer> integers) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < integers.size(); i++) {
			max = Integer.max(max, integers.get(i));
		}
		return max;
	}

	public int parallelStreamMaxInteger(List<Integer> integers) {
		Optional<Integer> max = integers.parallelStream().reduce(Integer::max);
		return max.get();
	}

	public int lambdaMaxInteger(List<Integer> integers) {
		return integers.stream().reduce(Integer.MIN_VALUE, (a, b) -> Integer.max(a, b));
	}

	public int forEachLambdaMaxInteger(List<Integer> integers) {
		final Wrapper wrapper = new Wrapper();
		wrapper.inner = Integer.MIN_VALUE;

		integers.forEach(i -> helper(i, wrapper));
		return wrapper.inner.intValue();
	}

	public static class Wrapper {
		public Integer inner;
	}

	private int helper(int i, Wrapper wrapper) {
		wrapper.inner = Math.max(i, wrapper.inner);
		return wrapper.inner;
	}

}
