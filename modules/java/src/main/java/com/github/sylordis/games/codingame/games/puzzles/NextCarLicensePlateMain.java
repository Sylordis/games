package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Arrays;
import java.util.Scanner;

public class NextCarLicensePlateMain {

	private final int RADIX_LETTERS = 26;
	private final int MAX_LETTERS = abcToInt("ZZ");
	private final int MAX_NUMBERS = 999;

	private int abcToInt(String abc) {
		int sum = 0;
		System.err.println("abcToInt(" + abc + "): ");
		for (int i = 0; i < abc.length(); i++) {
			int value = abc.charAt(abc.length() - 1 - i) - 'A';
			System.err.print(abc.charAt(abc.length() - 1 - i) + " " + value + "^" + i);
			value *= Math.pow(26, i);
			System.err.println(" => " + value);
			sum += value;
		}
		return sum;
	}

	private String intToAbc(int number, int nletters) {
		int remaining = number;
		String abc = "";
		int rank = nletters - 1;
		System.err.println("intToAbc(" + number + ", " + nletters + "): ");
		while (remaining > RADIX_LETTERS && rank >= 0) {
			int coeff = RADIX_LETTERS * (int) Math.pow(RADIX_LETTERS, rank - 1);
			int div = remaining / coeff;
			char c = intToAlpha(div);
			abc += c;
			System.err.print(remaining + "/" + coeff + "=" + div + " => '" + c + "'");
			remaining -= div * coeff;
			System.err.println(" (" + remaining + ")");
			rank--;
		}
		System.err.println("remaining: " + remaining + " = '" + intToAlpha(remaining) + "'");
		abc += (char) (remaining + 'A');
		for (int i = 0; i + abc.length() < nletters; i++)
			abc = 'A' + abc;
		return abc;
	}

	private char intToAlpha(int value) {
		return (char) (value + 'A');
	}

	private String calculate(String first, int increase) {
		String[] letters = first.split("-");
		// Convert to numbers
		// 0=numbers, 1=letters, 2=letters
		int[] numbers = new int[3];
		numbers[2] = abcToInt(letters[0]);
		numbers[1] = abcToInt(letters[2]);
		numbers[0] = Integer.parseInt(letters[1]);
		System.err.println("calculate(" + first + "," + increase + "): " + Arrays.toString(numbers));
		int dividend = increase;
		for (int i = 0; i < numbers.length; i++) {
			int limit = i == 0 ? MAX_NUMBERS : MAX_LETTERS;
			System.err.print("calculate: [" + i + "] " + numbers[i] + "+" + dividend + "/" + limit);
			numbers[i] += dividend;
			if (numbers[i] > limit) {
				dividend = numbers[i] / limit;
				numbers[i] %= limit;
			} else
				dividend = 0;
			System.err.println(" => " + numbers[i] + " || +" + dividend);
		}
		// Convert back to letters
		letters[0] = intToAbc(numbers[2], 2);
		letters[2] = intToAbc(numbers[1], 2);
		letters[1] = String.format("%03d", numbers[0]);
		return String.join("-", letters);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String x = in.nextLine();
		int n = in.nextInt();
		in.close();
		NextCarLicensePlateMain main = new NextCarLicensePlateMain();
		System.out.println(main.calculate(x, n));
	}

}
