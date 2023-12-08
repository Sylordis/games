package com.github.sylordis.games.codingame.games.puzzles.maxsurfacebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TheMaxSurfaceBox {

	/**
	 * Number of given cubes
	 */
	private int cubes;

	/**
	 * All possibles shapes.
	 */
	private List<SimpleCube> shapes;

	public TheMaxSurfaceBox(int n) {
		this.cubes = n;
		this.shapes = new ArrayList<>();
	}

	public void pollPlainCubes() {
		shapes = SimpleCube.pollPlainCubes(cubes);
		System.err.println(Arrays.toString(shapes.toArray()));
	}

	private int getMaxSurface() {
		if (shapes.isEmpty())
			return 0;
		return shapes.stream().mapToInt(c -> c.getSurface()).max().getAsInt();
	}

	private int getMinSurface() {
		if (shapes.isEmpty())
			return 0;
		return shapes.stream().mapToInt(c -> c.getSurface()).min().getAsInt();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.close();
		TheMaxSurfaceBox solution = new TheMaxSurfaceBox(n);
		solution.pollPlainCubes();
		System.out.println(solution.getMinSurface() + " " + solution.getMaxSurface());
	}

}
