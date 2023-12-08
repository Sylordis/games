package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Scanner;

import com.github.sylordis.commons.Direction;
import com.github.sylordis.commons.boards.Board;

public class ZerglingRush {

	private enum TileType {
		VOID('.', false), WALL('#', true), BUILDING('B', true), ZERGLING('z', true);

		private char symbol;
		private boolean blocking;

		private TileType(char c, boolean blocking) {
			this.symbol = c;
			this.blocking = blocking;
		}

		public char getSymbol() {
			return symbol;
		}

		public boolean isBlocking() {
			return blocking;
		}

		public static TileType getBySymbol(char symbol) {
			TileType type = null;
			for (TileType t : TileType.values()) {
				if (t.getSymbol() == symbol) {
					type = t;
					break;
				}
			}
			return type;
		}
	}

	private Board base;

	public ZerglingRush(int width, int height, String[] map) {
		this.base = new Board(width, height);
		this.base.fill(map);
	}

	/**
	 * Establishes a map of reachable tiles.
	 *
	 * @return a 2-dimensionnal array of booleans, with for each tile if it's reachable or not.
	 */
	public boolean[][] checkReachability() {
		boolean[][] reachArr = new boolean[base.getHeight()][base.getWidth()];
		// Parse each tiles on borders
		for (int y = 0; y < base.getHeight(); y++) {
			for (int x : new int[] { 0, base.getWidth() - 1 }) {
				forceFloodReachable(y, x, reachArr);
			}
		}
		return reachArr;
	}

	/**
	 * Floods automatically one tile.
	 *
	 * @param y
	 * @param x
	 * @param reachArr
	 */
	private void forceFloodReachable(int y, int x, boolean[][] reachArr) {
		TileType type = TileType.getBySymbol(base.getAsChar(x, y));
		// If type is not blocking or not already flooded
		if (!type.isBlocking() && !reachArr[y][x]) {
			// Flood it
			reachArr[y][x] = true;
			tryfloodReachableFrom(x, y, reachArr);
		}
	}

	/**
	 * Recursive method to flood from a point.
	 *
	 * @param x
	 * @param y
	 * @param reachArr
	 */
	private void tryfloodReachableFrom(int x, int y, boolean[][] reachArr) {
		if (reachArr[y][x]) {
			for (Direction d : Direction.CARDINAL_4) {
				try {
					final int nx = x + d.dx();
					final int ny = y + d.dy();
					forceFloodReachable(ny, nx, reachArr);
				} catch (ArrayIndexOutOfBoundsException e) {
					// Do nothing : out of the map
				}
			}
		}
	}

	public String resolve() {
		// System.err.println(base.toStringAsChar());
		boolean[][] reachArr = checkReachability();
		// Parse each tile
		for (int y = 0; y < base.getHeight(); y++) {
			for (int x = 0; x < base.getWidth(); x++) {
				// Check if it's a building
				if (base.get(x, y) == TileType.BUILDING.getSymbol()) {
					// If yes, check if every tile around is reachable and not set
					for (Direction d : Direction.CARDINAL_8) {
						try {
							// Check if reachable and not blocking
							if (reachArr[y + d.dy()][x + d.dx()]
									&& !TileType.getBySymbol(base.getAsChar(x + d.dx(), y + d.dy())).isBlocking()) {
								// Set it as zergling
								base.set(x + d.dx(), y + d.dy(), (int) TileType.ZERGLING.getSymbol());
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							// Do nothing
						}
					}
				}
			}
		}
		String repr = base.toStringDefault();
		return repr.substring(0, repr.length() - 1);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int width = in.nextInt();
		int height = in.nextInt();
		in.nextLine();
		String[] map = new String[height];
		for (int i = 0; i < height; i++) {
			map[i] = in.nextLine();
		}
		ZerglingRush rush = new ZerglingRush(width, height, map);
		System.out.println(rush.resolve());
		in.close();
	}

}
