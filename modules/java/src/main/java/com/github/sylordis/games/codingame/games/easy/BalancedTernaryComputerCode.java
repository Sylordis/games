package com.github.sylordis.games.codingame.games.easy;

import java.util.Scanner;

public class BalancedTernaryComputerCode {

	public static int sumTo(int power) {
		return 1 - (int) Math.pow(3, power) / -2;
	}

	public static String inverse(String btcc) {
		StringBuffer buffer = new StringBuffer();
		for (char c : btcc.toCharArray()) {
			if (c == '1')
				buffer.append('T');
			else if (c == 'T')
				buffer.append('1');
			else
				buffer.append(c);
		}
		return buffer.toString();
	}

	public static String decToBtcc(int decimal) {
		// Confirm for 0
		if (decimal == 0)
			return "0";
		// Otherwise
		StringBuffer btcc = new StringBuffer();
		int leftOver = Math.abs(decimal);
		while (leftOver != 0) {
			int rem = ((leftOver % 3) + 3) % 3;
			if (rem == 0 || rem == 1) {
				btcc.insert(0, rem);
				leftOver /= 3;
			} else if (rem == 2) {
				btcc.insert(0, "T");
				leftOver = (leftOver + 1) / 3;
			}
		}
		return decimal >= 0 ? btcc.toString() : inverse(btcc.toString());
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println(decToBtcc(in.nextInt()));
		in.close();
	}

}
