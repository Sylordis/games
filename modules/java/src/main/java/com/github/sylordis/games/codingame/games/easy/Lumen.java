package com.github.sylordis.games.codingame.games.easy;

//import static utils.DebugUtils.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

//import utils.MatrixUtils;

public class Lumen {

	private static final char CANDLE = 'C';

	private class Candle {
		private final int x, y;

		public Candle(int x, int y) {
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

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}

	}

	/**
	 *
	 * @param lumen
	 * @param strmap
	 * @return
	 */
	public long resolve(int lumen, String[] strmap) {
		Collection<Candle> candles = getLights(strmap);
		// debug("Candles: {}", candles);
		int[][] map = new int[strmap.length][strmap.length];
		for (Candle candle : candles) {
			// debug("Candle {}", candle);
			final int minY = Math.max(0, candle.y() - lumen);
			final int maxY = Math.min(strmap.length, candle.y() + lumen);
			final int minX = Math.max(0, candle.x - lumen);
			final int maxX = Math.min(strmap.length, candle.x() + lumen);
			for (int y = minY; y < maxY; y++) {
				for (int x = minX; x < maxX; x++) {
					int luminosity = Math.min(lumen - Math.abs(x - candle.x()), lumen - Math.abs(y - candle.y()));
					map[y][x] = Math.max(map[y][x], luminosity);
				}
			}
			// debug(MatrixUtils.printMatrixes(map));
		}
		long darkSpots = Arrays.stream(map).flatMapToInt(a -> Arrays.stream(a)).filter(this::isDark).count();
		return darkSpots;
	}

	private Collection<Candle> getLights(String[] map) {
		Collection<Candle> candles = new ArrayList<>();
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length(); x++) {
				if (map[y].charAt(x) == CANDLE) {
					candles.add(new Candle(x, y));
				}
			}
		}
		return candles;
	}

	private boolean isDark(int luminosity) {
		return luminosity == 0;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int lumen = in.nextInt();
		in.nextLine();
		String[] room = new String[n];
		for (int i = 0; i < n; i++)
			room[i] = in.nextLine().replace(" ", "");
		in.close();
		Lumen problem = new Lumen();
		System.out.println(problem.resolve(lumen, room));
	}

}
