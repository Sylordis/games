package com.github.sylordis.games.aoc.aoc2021;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Day1 {

	private final static int WINDOWS_SIZE = 3;

	public static void main(String[] args) {
		new Day1().run1();
	}

	public void run1() {
		int previous = Integer.MAX_VALUE;
		int numberOfIncreases = 0;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				int current = in.nextInt();
				if (current > previous) {
					numberOfIncreases++;
					print("{} ({})", current, current > previous ? "increased" : "decreased");
				}
				previous = current;
			}
			print("{}", numberOfIncreases);
		}
	}

	public void run2() {
		int previousWindow = Integer.MAX_VALUE;
		int numberOfIncreases = 0;
		Deque<Integer> lastWindows = new ArrayDeque<>();
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				lastWindows.add(in.nextInt());
				if (lastWindows.size() == WINDOWS_SIZE) {
					int currentWindow = lastWindows.stream().mapToInt(i -> i).sum();
					if (currentWindow > previousWindow) {
						numberOfIncreases++;
					}
					previousWindow = currentWindow;
					lastWindows.pollFirst();
				}
			}
			System.out.println(numberOfIncreases);
		}
	}

}
