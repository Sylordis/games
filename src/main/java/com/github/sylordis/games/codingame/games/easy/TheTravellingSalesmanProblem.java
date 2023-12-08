package com.github.sylordis.games.codingame.games.easy;

import java.util.LinkedList;
import java.util.Scanner;

public class TheTravellingSalesmanProblem {

	static class City {
		private final int x, y;

		public City(int x, int y) {
			this.x = x;
			this.y = y;
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

	}

	private static double distance(City c1, City c2) {
		return Math.sqrt(Math.pow(c2.x() - c1.x(), 2) + Math.pow(c2.y() - c1.y(), 2));
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		LinkedList<City> cities = new LinkedList<>();
		for (int i = 0; i < N; i++)
			cities.add(new City(in.nextInt(), in.nextInt()));
		in.close();
		double distance = 0L;
		final City start = cities.pop();
		City current = start;
		while (!cities.isEmpty()) {
			final City fcurr = current;
			City next = cities.stream().min((c1, c2) -> Double.compare(distance(fcurr, c1), distance(fcurr, c2))).get();
			distance += distance(current, next);
			cities.remove(next);
			current = next;
		}
		distance += distance(start, current);
		System.out.println(Math.round(distance));
	}
}
