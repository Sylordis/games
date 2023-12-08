package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Scanner;

public class TriforceBeWithYou {

	private char filling;
	private int size;
	private String[] triangle;

	public TriforceBeWithYou(char filling, int size) {
		this.filling = filling;
		this.size = size;
	}

	public String createTriForce() {
		getTriangle(size);
		// System.err.println(Arrays.toString(triangle));
		int fullSize = (size * 2 - 1) * 2 + 1;
		StringBuilder rame = new StringBuilder();
		// Put upper part
		for (int r = 0; r < triangle.length; r++) {
			// Center it
			int padding = (fullSize - triangle[r].length()) / 2;
			// System.err.println("upper: [" + r + "] pad=" + padding);
			rame.append(String.format("%" + (padding + triangle[r].length()) + "s", triangle[r])).append("\n");
			// System.err.println("[" + rame.toString() + "]");
		}
		// Put other parts
		for (int r = 0; r < triangle.length; r++) {
			int leftPad = size - (r + 1);
			int midPad = 2 * (size - r) - 1 - leftPad;
			final String trRow = String.format("%" + (leftPad + triangle[r].length()) + "s", triangle[r]);
			// System.err.println("lower[" + r + "] leftPad=" + leftPad + " midPad=" + midPad);
			rame.append(trRow).append(String.format("%" + midPad + "s", "")).append(trRow).append("\n");
		}
		return rame.toString().replaceFirst(" ", ".");
	}

	public String getStars(int size) {
		StringBuilder stars = new StringBuilder();
		for (int i = 0; i < size; i++)
			stars.append(filling);
		return stars.toString();
	}

	public void getTriangle(int size) {
		triangle = new String[size];
		for (int i = 0; i < size; i++)
			triangle[i] = getStars(1 + i * 2);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		in.close();
		TriforceBeWithYou force = new TriforceBeWithYou('*', N);
		System.out.println(force.createTriForce());
	}

}
