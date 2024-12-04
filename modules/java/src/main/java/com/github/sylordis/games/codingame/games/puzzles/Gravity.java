package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Scanner;

public class Gravity {

	// --- CONSTANTS

	public static final char EMPTY = '.';
	public static final char BLOCK = '#';

	// --- FIELDS

	// --- CONSTRUCTORS

	// --- MODIFIERS

	// --- GETTERS & SETTERS

	// --- STATIC

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int width = in.nextInt();
		int height = in.nextInt();
		in.nextLine();
		int[] columns = new int[width];
		for (int h = 0; h < height; h++) {
			String line = in.nextLine();
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == BLOCK)
					columns[i]++;
			}
		}
		for (int r = height; r > 0; r--) {
			for (int c = 0; c < width; c++) {
				System.out.print(columns[c] < r ? EMPTY : BLOCK);
			}
			System.out.println();
		}
		in.close();
	}

}
