package com.github.sylordis.games.aoc.aoc2020;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Puzzle2 implements Runnable {

	public static void main(String[] args) {
		new Puzzle2().run();
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
				min = Integer.parseInt(ranges[0]);
				max = Integer.parseInt(ranges[1]);
				String letterRaw = tokenizer.nextToken();
				final String letter = letterRaw.substring(0, letterRaw.indexOf(':'));
				String password = tokenizer.nextToken();
				long nchars = password.chars().filter(c -> c == letter.charAt(0)).count();
				if (nchars >= min && nchars <= max) {
					validPasswd++;
					print("{}, valid: {} [{},{}]", password, letter, min, max);
				}
			}
			print(validPasswd);
		}
	}

}
