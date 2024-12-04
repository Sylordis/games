package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Spreadsheet {

	private static final int VALUE_A = 65;
	private static final int SIGNS = 26;

	public String convert(String text) {
		System.err.println(text);
		try {
			return nToL(Long.parseLong(text));
		} catch (NumberFormatException e) {
			return Long.toString(lToN(text));
		}
	}

	private long lToN(String text) {
		int power = 0;
		long value = 0;
		long tvalue = 0;
		final char[] chars = new StringBuilder(text).reverse().toString().toCharArray();
		for (char c : chars) {
			tvalue = c - VALUE_A + 1;
			value += tvalue * Math.pow(SIGNS, power);
			System.err.print(c + "=" + tvalue + "*" + SIGNS + "^" + power + " ");
			power++;
		}
		System.err.println(" => " + value);
		return value;
	}

	private String nToL(long number) {
		final StringBuilder builder = new StringBuilder();
		long num = number - 1;
		while (num >= 0) {
			long numChar = num % SIGNS;
			builder.append((char) (numChar + VALUE_A));
			System.err.println("  " + num + "%" + SIGNS + "=" + numChar + " => " + (char) (numChar + VALUE_A));
			num = (num / SIGNS) - 1;
		}
		return builder.reverse().toString();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Spreadsheet sheet = new Spreadsheet();
		in.nextLine();
		StringTokenizer tokenizer = new StringTokenizer(in.nextLine());
		StringBuilder builder = new StringBuilder();
		while (tokenizer.hasMoreTokens()) {
			builder.append(sheet.convert(tokenizer.nextToken())).append(' ');
		}
		System.out.println(builder.toString().trim());
		in.close();
	}
}
