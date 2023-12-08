package com.github.sylordis.games.codingame.games.puzzles.floodingexample;

import com.github.sylordis.commons.Pair;
import com.github.sylordis.commons.id.IdentifiableEntity;

public class FloodingVertex implements IdentifiableEntity<Pair<Character, Integer>> {

	// --- FIELDS

	private final int x, y;
	private Pair<Character, Integer> id;

	// --- CONSTRUCTORS

	public FloodingVertex(int x, int y) {
		this.x = x;
		this.y = y;
		this.id = null;
	}

	public FloodingVertex(int x, int y, char id, int n) {
		this.x = x;
		this.y = y;
		this.id = new Pair<>(id, n);
	}

	public FloodingVertex(int x, int y, FloodingVertex v) {
		this.x = x;
		this.y = y;
		this.id = v.getId();
	}

	// --- MODIFIERS

	// --- GETTERS & SETTERS

	/**
	 * @return the x
	 */
	public int x() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int y() {
		return y;
	}

	/**
	 * @return the id
	 */
	@Override
	public Pair<Character, Integer> getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 * @param n
	 *            the serial
	 */
	public void setId(char id, int n) {
		this.id = new Pair<>(id, n);
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Pair<Character, Integer> id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id + "(" + x + "," + y + ")";
	}
}
