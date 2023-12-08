package com.github.sylordis.games.aoc.aoc2022;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day1 {

	public static void main(String[] args) {
		new Day1().run1();
	}

	public void run1() {
		List<List<Long>> elves = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			List<Long> elf = new ArrayList<>();
			while (in.hasNext()) {
				String token = in.nextLine();
				if (token.isEmpty()) {
					elves.add(elf);
					elf = new ArrayList<>();
				} else
					elf.add(Long.parseLong(token));
			}
			// Add last elf (no empty line at the end)
			elves.add(elf);
		}
		long[] elvesMax = elves.stream().mapToLong(e -> e.stream().reduce(0L, Long::sum)).sorted().toArray();
//		print("{}", elvesMax);
		final int range = 3;
		long[] maxX = Arrays.copyOfRange(elvesMax, elvesMax.length-range, elvesMax.length);
		
//		print("{}", maxX);
		print("{}", Arrays.stream(maxX).sum());
	}

}
