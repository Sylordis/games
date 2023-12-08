package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Scanner;

public class Rubik {

	// --- FIELDS

	private int size;

	// --- CONSTRUCTORS

	public Rubik(int n) {
		size = n;
	}

	// --- MODIFIERS

	// --- GETTERS & SETTERS

	public int getNumberOfVisibleCubes() {
		if (size == 1 || size == 0)
			return size;
		int alter = size - 2;
		return 4 * size * (size - 1) + 2 * alter * alter;
	}

	// --- MAIN

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		Rubik rubik = new Rubik(size);
		System.out.println(rubik.getNumberOfVisibleCubes());
		in.close();
	}
}
