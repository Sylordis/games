package com.github.sylordis.games.aoc.aoc2021;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.Arrays;
import java.util.Scanner;

public class Day7 {

	public static void main(String[] args) {
		new Day7().run();
	}

	private void run() {
		long[] crabs = null;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String crabsStr = in.nextLine();
				crabs = Arrays.stream(crabsStr.split(",")).mapToLong(Long::parseLong).toArray();
			}
		}
		print("{}", crabs);
		Arrays.sort(crabs);
		print("{}", crabs);
		final int median = (int) getMedian(crabs);
		long fuel = Arrays.stream(crabs).map(p -> Math.abs(p - median)).sum();
		print("{}", fuel);
	}

	private double getMedian(long[] numArray) {
		double median;
		if (numArray.length % 2 == 0)
			median = ((double) numArray[numArray.length / 2] + (double) numArray[numArray.length / 2 - 1]) / 2;
		else
			median = numArray[numArray.length / 2];
		return median;
	}

}
