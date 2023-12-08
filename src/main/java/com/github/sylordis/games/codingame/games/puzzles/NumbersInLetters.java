package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Scanner;

public class NumbersInLetters {

	public static int[] dictionary = new int[] { 4, 3 };

	public NumbersInLetters() {
	}

	public long calculate(long start, long n) {
		System.err.println("calculate(" + start + "," + n + ")");
		long term = start; // S0
		long previous = -1;
		for (long i = 1; i <= n; i++) {
			String termS = Long.toBinaryString(term);
			previous = term;
			term = convert(termS);
			if (previous == term) {
				break;
			}
			System.err.println("S(" + i + ") " + termS + " => " + term);
		}
		return term;
	}

	private long convert(String termS) {
		long sum = 0;
		for (char c : termS.toCharArray())
			sum += dictionary[Integer.parseInt("" + c)];
		return sum;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long start = in.nextLong();
		long n = in.nextLong();
		in.close();
		NumbersInLetters nil = new NumbersInLetters();
		System.out.println(nil.calculate(start, n));
	}

}
