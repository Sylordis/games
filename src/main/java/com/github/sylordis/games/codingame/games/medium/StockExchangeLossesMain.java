package com.github.sylordis.games.codingame.games.medium;

import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class StockExchangeLossesMain {

	public static void main(String args[]) {
		try (Scanner in = new Scanner(System.in)) {
			int n = in.nextInt();
			int gDecrease = 0;
			int cDecrease = 0;
			int max = in.nextInt();
			System.err.println("<" + max + "> peek");
			int latestValue = max;
			for (int i = 1; i < n; i++) {
				int v = in.nextInt();
				System.err.print("<" + v + ">");
				if (v < latestValue) {
					cDecrease = max - v;
					System.err.print(" decrease[" + cDecrease + "]");
					if (cDecrease > gDecrease) {
						System.err.print(" max1[" + cDecrease + "/" + gDecrease + "]");
						gDecrease = cDecrease;
					}
				}
				if (max < v) {
					System.err.print(" peek");
					max = v;
					if (cDecrease > gDecrease) {
						System.err.print(" max2[" + cDecrease + "/" + gDecrease + "]");
						gDecrease = cDecrease;
					}
					cDecrease = 0;
				}
				latestValue = v;
				System.err.println();
			}
			gDecrease = 0 - gDecrease;
			System.out.println(gDecrease);
		}
	}
}