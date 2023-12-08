package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Scanner;

public class InertiaRideMain {

	private final char HORIZONTAL = '_';
	private final char AIR = '.';
	private final int HORIZONTAL_FRICTION = -1;
	private final int ASCENDING_FRICTION = -10;
	private final int DESCENDING_FRICTION = 9;

	private String track;
	private int inertia;
	private int index;

	public InertiaRideMain() {
		collect();
	}

	public void roll() {
		boolean stopped = false;
		while (!stopped) {
			char currentSlope = track.charAt(index);
			System.err.print(index + ") " + track.charAt(index) + " " + inertia);
			updateInertia(currentSlope);
			if (inertia == 0 && currentSlope == HORIZONTAL)
				stopped = true;
			else
				move(currentSlope);
			if (index == 0 || index == track.length() - 1)
				stopped = true;
			System.err.println(" => (" + index + ") " + inertia + " " + stopped);
		}
	}

	private void updateInertia(char slope) {
		int friction = HORIZONTAL_FRICTION;
		if (slope != HORIZONTAL) {
			if (inertia == 0) {
				friction = (slope == '/' ? 1 : -1) * ASCENDING_FRICTION;
			} else if ((slope == '/' && inertia > 0) || (slope == '\\' && inertia < 0))
				friction = ASCENDING_FRICTION;
			else
				friction = DESCENDING_FRICTION;
		}
		if (inertia != 0)
			friction *= Math.signum(inertia);
		inertia += friction;
	}

	private void move(char slope) {
		index += Math.signum(inertia);
	}

	/**
	 * Collects all data for the puzzle and initializes all variables.
	 */
	private void collect() {
		Scanner in = new Scanner(System.in);
		inertia = in.nextInt();
		int W = in.nextInt();
		int H = in.nextInt();
		StringBuilder builder = new StringBuilder(String.format("%" + W + "s", " "));
		in.nextLine();
		// Flattens the rows
		for (int i = 0; i < H; i++) {
			String row = in.nextLine();
			for (int c = 0; c < W; c++) {
				if (row.charAt(c) != AIR)
					builder.setCharAt(c, row.charAt(c));
			}
		}
		track = builder.toString();
		System.err.println(track);
		in.close();
	}

	public int getIndex() {
		return index;
	}

	public static void main(String args[]) {
		InertiaRideMain main = new InertiaRideMain();
		main.roll();
		System.out.println(main.getIndex());
	}

}
