package com.github.sylordis.games.aoc.aoc2021;

import java.util.Scanner;

public class Day2 {

	public static void main(String[] args) {
		new Day2().run1();
	}

	public void run1() {
		int x = 0;
		int y = 0;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String instruction = in.nextLine();
				String[] parts = instruction.split(" ");
				final int units = Integer.parseInt(parts[1]);
				switch (parts[0]) {
					case "down":
						y += units;
						break;
					case "forward":
						x += units;
						break;
					case "up":
						y -= units;
						break;
				}
			}
			System.out.println(x * y);
		}
	}

	public void run2() {
		long x = 0;
		long y = 0;
		long aim = 0;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String instruction = in.nextLine();
				String[] parts = instruction.split(" ");
				final int units = Integer.parseInt(parts[1]);
				switch (parts[0]) {
					case "down":
						aim += units;
						break;
					case "forward":
						x += units;
						y += aim * units;
						break;
					case "up":
						aim -= units;
						break;
				}
			}
			System.out.println(x * y);
		}
	}
}
