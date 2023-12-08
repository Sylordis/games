package com.github.sylordis.games.codingame.games.puzzles.appletree;

public class Apple {

	// --- FIELDS

	private final int x, y, z;
	private final int radius;

	// --- CONSTRUCTORS

	public Apple(int x, int y, int z, int radius) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
	}

	// --- MODIFIERS

	// --- GETTERS & SETTERS

	/**
	 * Checks whether or not an apple, if it were falling, would collide with this apple.
	 *
	 * @param apple
	 * @return true if the falling apple collides, false otherwise.
	 */
	public boolean collideWith(Apple apple) {
		return apple.z() >= z && getDistance(apple) <= radius + apple.getRadius();
	}

	/**
	 * Gets the square of the distance to a given point. This method is simpler calculation wise.
	 *
	 * @param apple
	 *            Another point to measure the distance to.
	 * @return the distance between the two points.
	 */
	public double getDistanceSq(Apple apple) {
		double xs = apple.x() - x();
		double ys = apple.y() - y();
		return xs * xs + ys * ys;
	}

	/**
	 * Calculates the distance to another point.
	 *
	 * @param apple
	 *            Another point to measure the distance to.
	 * @return the distance between the two points.
	 */
	public double getDistance(Apple apple) {
		return Math.sqrt(getDistanceSq(apple));
	}

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
	 * @return the z
	 */
	public int z() {
		return z;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

}
