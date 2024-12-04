package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Collections;
import java.util.Scanner;

public class HighestTruncatedPyramid {

	private int blocks;

	public HighestTruncatedPyramid(int n) {
		this.blocks = n;
	}

	private long getSum(int n) {
		return (n * (n + 1)) / 2;
	}

	@Override
	public String toString() {
		if (blocks < 0)
			return "";
		int base = 1;
		int top = 0;
		long total = 0;
		while (getSum(base) < blocks)
			base++;
		total = getSum(base);
		if (total > blocks) {
			while (total - getSum(top) > blocks)
				top++;
		}
		System.err.println(top + " (" + getSum(top) + ") " + base + " (" + getSum(base) + ") " + total);
		if (getSum(base) - getSum(top) != blocks) {
			base = blocks;
			top = base - 1;
			System.err.println(top + " (" + getSum(top) + ") " + base + " (" + getSum(base) + ") ");
		}
		StringBuilder rame = new StringBuilder();
		for (int i = top + 1; i <= base; i++) {
			rame.append(String.join("", Collections.nCopies(i, "*"))).append("\n");
		}
		if (rame.length() == 0)
			return "";
		else
			return rame.toString().substring(0, rame.length() - 1);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println(new HighestTruncatedPyramid(in.nextInt()));
		in.close();
	}

}
