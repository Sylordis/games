package com.github.sylordis.games.codingame.games.puzzles.dominoespath;

import com.github.sylordis.commons.Pair;

public class Domino extends Pair<Integer, Integer> {

	// --- FIELDS

	// --- CONSTRUCTORS

	/**
	 * Constructs a new domino.
	 *
	 * @param a
	 *            First value
	 * @param b
	 *            Second value
	 */
	public Domino(Integer a, Integer b) {
		super(a, b);
	}

	@Override
	public Domino exchange() {
		return new Domino(this.getSecond(), this.getFirst());
	}

	// --- MODIFIERS

	// --- GETTERS & SETTERS

	// --- OVERRIDEN

	@Override
	public String toString() {
		return "[" + getFirst() + "," + getSecond() + "]";
	}
}
