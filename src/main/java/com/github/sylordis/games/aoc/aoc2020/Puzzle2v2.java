package com.github.sylordis.games.aoc.aoc2020;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Puzzle2v2 implements Runnable {

	public static void main(String[] args) {
		new Puzzle2v2().run();
	}

	@Override
	public void run() {
		try (Scanner in = new Scanner(System.in)) {
			int validPasswd = 0;
			int min, max;
			while (in.hasNext()) {
				StringTokenizer tokenizer = new StringTokenizer(in.nextLine());
				String range = tokenizer.nextToken();
				String[] ranges = range.split("-");
				min = Integer.parseInt(ranges[0]) - 1;
				max = Integer.parseInt(ranges[1]) - 1;
				String letterRaw = tokenizer.nextToken();
				char letter = letterRaw.charAt(0);
				String password = tokenizer.nextToken();
				int count = 0;
				if (min < password.length() && password.charAt(min) == letter)
					count++;
				if (max < password.length() && password.charAt(max) == letter)
					count++;
				if (count == 1) {
					validPasswd++;
					print("{}, valid: {} [{},{}]", password, letter, min, max);
				}
			}
			print(validPasswd);
		}
	}

}
