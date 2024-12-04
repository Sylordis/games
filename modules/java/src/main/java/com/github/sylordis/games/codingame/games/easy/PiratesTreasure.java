package com.github.sylordis.games.codingame.games.easy;

import java.util.Arrays;
import java.util.Scanner;

public class PiratesTreasure {

	enum Direction {

		NORTH(0, -1),
		NORTHEAST(1, -1),
		EAST(1, 0),
		SOUTHEAST(1, 1),
		SOUTH(0, 1),
		SOUTHWEST(-1, 1),
		WEST(-1, 0),
		NORTHWEST(-1, -1);

		private final int dx, dy;

		private Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		public int dx() {
			return dx;
		}

		public int dy() {
			return dy;
		}

	}

	static class PirateMap {
		private final int[][] map;

		public PirateMap(int[][] map) {
			this.map = map;
		}

		public String findTreasure() {
			String treasure = null;
			for (int y = 0; y < map.length && treasure == null; y++) {
				for (int x = 0; x < map[0].length && treasure == null; x++) {
					System.err.println(x + "," + y + "=" + map[y][x]);
					if (map[y][x] == 0) {
						int fy = y;
						int fx = x;
						boolean found = Arrays.stream(Direction.values()).mapToInt(d -> {
							try {
								return map[fy + d.dy()][fx + d.dx()];
							} catch (ArrayIndexOutOfBoundsException e) {
								return 1;
							}
						}).allMatch(i -> i == 1);
						if (found)
							treasure = x + " " + y;
					}
				}
			}
			return treasure;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int W = in.nextInt();
		int H = in.nextInt();
		int[][] mymap = new int[H][W];
		for (int y = 0; y < H; y++)
			for (int x = 0; x < W; x++)
				mymap[y][x] = in.nextInt();
		in.close();
		PirateMap map = new PirateMap(mymap);
		System.out.println(map.findTreasure());
	}
}
