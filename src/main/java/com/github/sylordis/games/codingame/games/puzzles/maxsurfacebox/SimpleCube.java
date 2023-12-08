package com.github.sylordis.games.codingame.games.puzzles.maxsurfacebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleCube {

	// --- PRIVATE FIELDS

	private int depth, length, width;

	// --- CONSTRUCTORS

	/**
	 * Simple constructor.
	 *
	 * @param width
	 * @param length
	 * @param depth
	 */
	public SimpleCube(int width, int length, int depth) {
		this.width = width;
		this.length = length;
		this.depth = depth;
	}

	/**
	 * Constructs a simple cube out of three (max) measurements.
	 *
	 * @param measurements
	 */
	public SimpleCube(int[] measurements) {
		if (null != measurements) {
			if (measurements.length > 0)
				this.width = measurements[0];
			else
				this.width = 1;
			if (measurements.length > 1)
				this.length = measurements[1];
			else
				this.length = 1;
			if (measurements.length > 2)
				this.depth = measurements[2];
			else
				this.depth = 1;
		}
	}

	// --- MODIFIERS

	/**
	 * Constructs a list of plain cubes according to a number of smaller cubes of 1x1x1.
	 *
	 * @param cubes
	 *            number of cubes given
	 * @return a list of possible cubes
	 */
	public static List<SimpleCube> pollPlainCubes(int cubes) {
		List<SimpleCube> list = new ArrayList<>();
		if (cubes <= 3 && cubes > 0) {
			list.add(new SimpleCube(cubes, 1, 1));
		} else {
			// Depth is calculated from length, width and remaining cubes
			for (int length = 1; length < cubes - 2; length++) {
				for (int width = 1; (width * length) < cubes - 1; width++) {
					int depth = cubes / (length * width);
					if (depth == 0)
						depth = 1;
					// Only allow plain cubes and non-negative depth.
					if (depth > 0 && cubes == length * width * depth) {
						SimpleCube cube = new SimpleCube(width, length, depth);
						// Eliminate
						if (!list.contains(cube))
							list.add(cube);
					}
				}
			}
		}
		return list;
	}

	// --- OVERRIDEN METHODS

	@Override
	public String toString() {
		return width + "x" + length + "x" + depth;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleCube other = (SimpleCube) obj;
		int[] me = getMeasurements();
		int[] her = other.getMeasurements();
		Arrays.sort(me);
		Arrays.sort(her);
		return Arrays.equals(me, her);
	}

	// --- GETTERS & SETTERS

	public int getSurface() {
		final int surface = 2 * (width * length) + 2 * (length * depth) + 2 * (width * depth);
		// System.err.println(this + ": " + surface);
		return surface;
	}

	protected int[] getMeasurements() {
		return new int[] { width, length, depth };
	}

}
