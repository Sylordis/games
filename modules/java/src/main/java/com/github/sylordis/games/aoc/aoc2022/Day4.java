package com.github.sylordis.games.aoc.aoc2022;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.Scanner;

import com.github.sylordis.commons.math.SimpleRange;

public class Day4 {

	private final String RANGE_SEPARATOR = "-";
	private final String GROUP_SEPARATOR = ",";
	
	public static void main(String[] args) {
		new Day4().part2();
	}

	private void part1() {
		int containsSum = 0;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String[] pair = in.nextLine().split(GROUP_SEPARATOR);
				SimpleRange<Integer> r1 = SimpleRange.fromStr(pair[0], RANGE_SEPARATOR, Integer::valueOf);
				SimpleRange<Integer> r2 = SimpleRange.fromStr(pair[1], RANGE_SEPARATOR, Integer::valueOf);
				boolean contains = r1.contains(r2) || r2.contains(r1);
				if (contains)
					containsSum++;
				print("r1={}, r2={}, contains? {}", r1, r2, contains);
			}
		}
		print("{}", containsSum);
	}

	private void part2() {
		int overlapsSum = 0;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String[] pair = in.nextLine().split(GROUP_SEPARATOR);
				SimpleRange<Integer> r1 = SimpleRange.fromStr(pair[0], RANGE_SEPARATOR, Integer::valueOf);
				SimpleRange<Integer> r2 = SimpleRange.fromStr(pair[1], RANGE_SEPARATOR, Integer::valueOf);
				boolean overlaps = r1.overlaps(r2) || r2.overlaps(r1);
				if (overlaps)
					overlapsSum++;
				print("r1={}, r2={}, overlaps? {}", r1, r2, overlaps);
			}
		}
		print("{}", overlapsSum);
	}

}
