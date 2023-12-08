package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class RugbyScore {

	private final static int TRY = 5;
	private final static int TRANSFORMATION = 2;
	private final static int PENALTY = 3;

	static class Scoring implements Comparable<Scoring> {
		private int tries;
		private int transformations;
		private int penalties;

		public int getSum() {
			return tries + transformations + penalties;
		}

		public Scoring(int tries, int transformations, int penalties) {
			this.transformations = transformations;
			this.tries = tries;
			this.penalties = penalties;
		}

		public static Scoring compute(int tries, int transformations) {
			return null;
		}

		@Override
		public int compareTo(Scoring o) {
			int compare = tries - o.tries;
			if (compare == 0)
				compare = transformations - o.transformations;
			if (compare == 0)
				compare = penalties - o.penalties;
			return compare;
		}

		@Override
		public String toString() {
			return tries + " " + transformations + " " + penalties;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int score = in.nextInt();
		Collection<Scoring> scorings = new ArrayList<>();
		for (int tries = 0; tries * TRY <= score; tries++) {
			for (int trans = 0; trans * TRANSFORMATION + tries * TRY <= score && trans <= tries; trans++) {
				int left = score - tries * TRY - trans * TRANSFORMATION;
				int pen = 0;
				if (left % PENALTY == 0) {
					pen = left / PENALTY;
					scorings.add(new Scoring(tries, trans, pen));
				}
			}
		}
		in.close();
		scorings.stream().sorted().forEach(System.out::println);
	}

}
