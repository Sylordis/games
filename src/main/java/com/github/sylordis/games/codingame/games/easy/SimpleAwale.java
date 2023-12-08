package com.github.sylordis.games.codingame.games.easy;

import java.util.*;

public class SimpleAwale {

	static class Awale {
		private static final int SIZE = 7;
		private int[] board;

		public Awale(int[] player, int[] opponent) {
			board = new int[SIZE * 2];
			Arrays.parallelSetAll(board, i -> i < SIZE ? player[i] : opponent[i - SIZE]);
		}

		public boolean play(int bowl) {
			boolean replay = (bowl + board[bowl]) % 13 == SIZE - 1;
			int amount = board[bowl];
			board[bowl] = 0;
			for (int i = bowl + 1; i <= bowl + amount; i++)
				board[i % (SIZE * 2 - 1)]++;
			return replay;
		}

		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			// Opp. row
			for (int i = 7; i < SIZE * 3; i++)
				buffer.append(i % SIZE == SIZE - 1 ? "[" + board[i % (SIZE * 2)] + "]\n" : board[i % (SIZE * 2)] + " ");
			// Player row
			return buffer.toString().substring(0, buffer.length() - 1);
		}

	}

	public static int[] convert(String row) {
		return Arrays.stream(row.split(" ")).mapToInt(Integer::parseInt).toArray();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String opBowls = in.nextLine();
		String myBowls = in.nextLine();
		int num = in.nextInt();
		in.close();
		Awale awale = new Awale(convert(myBowls), convert(opBowls));
		System.err.println(awale);
		boolean replay = awale.play(num);
		System.out.println(awale);
		if (replay)
			System.out.println("REPLAY");
	}
}
