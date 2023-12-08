package com.github.sylordis.games.codingame.games.puzzles.elevator;

import java.util.Scanner;

public class ElevatorMain {

	// --- FIELDS

	private int floorsNumber;
	private Elevator elevator;

	// --- CONSTRUCTORS

	/**
	 * Builds a new puzzle;
	 *
	 * @param n
	 */
	public ElevatorMain(int n) {
		this.floorsNumber = n;
	}

	// --- MODIFIERS

	public int getSolution(int origin, int target) {
		int opDown, opUp;
		// TODO
		return 0;
	}

	// --- GETTERS & SETTERS

	public void setElevator(int down, int up) {
		this.elevator = new Elevator(down, up);
	}

	// --- MAIN

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n, a, b, k, m;
		n = in.nextInt();
		a = in.nextInt();
		b = in.nextInt();
		k = in.nextInt();
		m = in.nextInt();
		in.close();
		ElevatorMain puzzle = new ElevatorMain(n);
		puzzle.setElevator(b, a);
		int solution = puzzle.getSolution(k, m);
		System.out.println(solution < 0 ? "IMPOSSIBLE" : solution);
	}
}
