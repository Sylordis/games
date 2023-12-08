package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Arrays;
import java.util.Scanner;

public class GravityTumbler {

	// --- CONSTANTS

	public static final char EMPTY = '.';
	public static final char BLOCK = '#';

	// --- FIELDS

	private int[] columns;
	private int width;
	private int height;

	// --- CONSTRUCTORS

	// --- MODIFIERS

	/**
	 * Tumbles once.
	 */
	public void tumble() {
		boolean horizontal = this.columns.length == width;
		int[] columns = new int[horizontal ? height : width];
		for (int i = 0; i < columns.length; i++) {
			final int count = columns.length - i;
			columns[i] = (int) Arrays.stream(this.columns).filter(c -> c >= count).count();
			System.err.println("i=" + i + " count=" + count + " " + columns[i]);
		}
		int twidth = width;
		this.width = height;
		this.height = twidth;
		this.columns = columns;
	}

	// --- OVERRIDEN

	@Override
	public String toString() {
		StringBuilder rame = new StringBuilder();
		for (int r = height; r > 0; r--) {
			for (int c = 0; c < width; c++)
				rame.append(columns[c] < r ? EMPTY : BLOCK);
			rame.append("\n");
		}
		return rame.toString();
	}

	// --- GETTERS & SETTERS

	/**
	 * Fills the tumbler.
	 *
	 * @param cols
	 */
	public void fill(String[] cols) {
		height = cols.length;
		width = cols[0].length();
		columns = new int[width];
		for (int h = 0; h < height; h++) {
			for (int i = 0; i < width; i++) {
				if (cols[h].charAt(i) == BLOCK)
					columns[i]++;
			}
		}
	}

	// --- STATIC

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int width = in.nextInt();
		int height = in.nextInt();
		int tumble = in.nextInt();
		in.nextLine();
		String[] lines = new String[height];
		for (int i = 0; i < height; i++) {
			lines[i] = in.nextLine();
		}
		GravityTumbler tumbler = new GravityTumbler();
		tumbler.fill(lines);
		for (int i = 0; i < tumble; i++) {
			tumbler.tumble();
		}
		String repr = tumbler.toString();
		System.out.println(repr.substring(0, repr.length() - 1));
		in.close();
	}

}
