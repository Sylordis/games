package com.github.sylordis.games.codingame.games.hard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.function.BiFunction;

public class BinaryPermutation {

	static class BinaryUtils {

		public static final int RIGHT = 1;
		public static final int LEFT = -1;

		public static String shift(String number, int rank, int direction) {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < number.length(); i++) {
				buffer.append(number.charAt((i + rank * direction + number.length()) % number.length()));
			}
			return buffer.toString();
		}
	}

	static class BinaryPermutationSolver {
		private Collection<BiFunction<String, String, String>> permutations;

		public BinaryPermutationSolver() {
			permutations = new ArrayList<>();
		}

		public void processClue(int number, int result) {
			final String numberBin = Integer.toBinaryString(number);
			final String resultBin = Integer.toBinaryString(result);
			// Check for rotation
			if (resultBin == BinaryUtils.shift(numberBin, 1, BinaryUtils.LEFT)
					|| resultBin == BinaryUtils.shift(numberBin, -1, BinaryUtils.RIGHT)) {
				// TODO
			}
			// TODO check for differences
			for (int i = 0; i < numberBin.length(); i++) {

			}
		}

		public int apply(int number) {
			return Integer.parseInt(apply(Integer.toBinaryString(number)), 2);
		}

		private String apply(String binary) {
			String result = binary;
			for (BiFunction<String, String, String> perm : permutations) {
				result = perm.apply(result, binary);
			}
			return result;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int nbits = in.nextInt();
		int nclues = in.nextInt();
		BinaryPermutationSolver solver = new BinaryPermutationSolver();
		for (int i = 0; i < nclues; i++)
			solver.processClue(in.nextInt(), in.nextInt());
		in.close();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < nbits; i++) {
			if (buffer.length() > 0)
				buffer.append(" ");
			buffer.append(solver.apply((int) Math.pow(2, i)));
		}
		System.out.println(buffer);
	}

}
