package com.github.sylordis.games.codingame.games.puzzles.elevator;

public class Elevator {

	// --- FIELDS

	private int down;
	private int up;

	// --- CONSTRUCTORS

	public Elevator(int down, int up) {
		this.down = down;
		this.up = up;
	}

	// --- MODIFIERS

	// --- GETTERS & SETTERS

	/**
	 * @return the down
	 */
	public int getDown() {
		return down;
	}

	/**
	 * @param down
	 *            the down to set
	 */
	public void setDown(int down) {
		this.down = down;
	}

	/**
	 * @return the up
	 */
	public int getUp() {
		return up;
	}

	/**
	 * @param up
	 *            the up to set
	 */
	public void setUp(int up) {
		this.up = up;
	}

	/**
	 * Returns the direction's tendancy of the elevator (up - down).
	 */
	public int getTendancy() {
		return up - down;
	}

}
