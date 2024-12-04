package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class RookMovements {

	enum Direction {

		NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

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

	enum Action {
		MOVE("-"), ATTACK("x");
		private String str;

		private Action(String str) {
			this.str = str;
		}

		@Override
		public String toString() {
			return str;
		}
	}

	static class Position {
		private final int x, y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int x() {
			return x;
		}

		public int y() {
			return y;
		}

		@Override
		public String toString() {
			return "" + (char) ('a' + x) + (y + 1);
		}

	}

	static class Movement {

		private final Position start;
		private Action action;
		private final Position stop;

		public Movement(Position start, Action action, Position stop) {
			this.start = start;
			this.action = action;
			this.stop = stop;
		}

		/**
		 * @return the start
		 */
		public Position getStart() {
			return start;
		}

		/**
		 * @return the action
		 */
		public Action getAction() {
			return action;
		}

		/**
		 * @return the stop
		 */
		public Position getStop() {
			return stop;
		}

		/**
		 * @param action
		 *            the action to set
		 */
		public void setAction(Action action) {
			this.action = action;
		}

		@Override
		public String toString() {
			return "" + start + action + stop;
		}

	}

	private static final int BOARD_SIZE = 8;
	private static final Predicate<Integer> IN_BOARD = i -> i >= 0 && i < BOARD_SIZE;
	private static final int WHITE = 1;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String rookPosition = in.next();
		Position rook = new Position(rookPosition.charAt(0) - 'a', rookPosition.charAt(1) - '1');
		System.err.println(rook + " " + rook.x() + "," + rook.y());
		int nbPieces = in.nextInt();
		// 0 = nothing, 1 = white piece, 2 = black piece
		int[][] board = new int[8][8];
		for (int i = 0; i < nbPieces; i++) {
			int colour = in.nextInt();
			String onePiece = in.next();
			board[onePiece.charAt(1) - '1'][onePiece.charAt(0) - 'a'] = colour + 1;
		}
		in.close();
		List<Movement> positions = new ArrayList<>();
		for (Direction dir : Direction.values()) {
			System.err.println(dir);
			for (int i = 1; i < BOARD_SIZE; i++) {
				int dx = dir.dx() * i + rook.x();
				int dy = dir.dy() * i + rook.y();
				System.err.println("dx,dy=" + dx + "," + dy + " " + new Position(dx, dy).toString());
				if (!IN_BOARD.test(dx) || !IN_BOARD.test(dy) || board[dy][dx] == WHITE)
					break;
				Movement move = new Movement(rook, Action.MOVE, new Position(dx, dy));
				if (board[dy][dx] == WHITE + 1)
					move.setAction(Action.ATTACK);
				positions.add(move);
				if (move.getAction() == Action.ATTACK)
					break;
			}
		}
		positions.stream().map(p -> p.toString()).sorted().forEach(p -> System.out.println("R" + p));
	}
}
