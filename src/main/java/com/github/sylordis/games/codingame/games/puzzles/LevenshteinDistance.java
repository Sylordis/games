package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Scanner;

public class LevenshteinDistance {

	/**
	 * Calculates the Levenshtein Distance between two strings.
	 *
	 * @param w1
	 * @param w2
	 */
	public static int calculate(String w1, String w2) {
		int[][] dist = new int[w1.length() + 1][w2.length() + 1];
		for (int i = 1; i < w1.length() + 1; i++) {
			dist[i][0] = i;
		}
		for (int j = 1; j < w2.length() + 1; j++) {
			dist[0][j] = j;
		}
		for (int j = 1; j < w2.length() + 1; j++) {
			for (int i = 1; i < w1.length() + 1; i++) {
				int sub = 0;
				if (w1.charAt(i - 1) != w2.charAt(j - 1))
					sub = 1;
				dist[i][j] = Math.min(dist[i - 1][j - 1] + sub, Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1));
			}
		}
		return dist[w1.length() - 1][w2.length() - 1];
	}

	/**
	 * Main
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		final String w1 = in.nextLine();
		final String w2 = in.nextLine();
		System.out.println(calculate(w1, w2));
		in.close();
	}

}
