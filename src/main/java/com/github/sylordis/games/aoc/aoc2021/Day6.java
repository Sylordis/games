package com.github.sylordis.games.aoc.aoc2021;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.Arrays;
import java.util.Scanner;

public class Day6 {

	private static final int N_DAYS_SAMPLE = 18;
	private static final int N_DAYS = 80;
	private static final int N_DAYS_V2 = 256;
	private static final int MAX_GESTATION = 9;

	public static void main(String[] args) {
		new Day6().run(N_DAYS_V2);
	}

	private void run(int ndays) {
		long[] fishes = new long[MAX_GESTATION];
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String fishesStr = in.nextLine();
				int[] fishesInput = Arrays.stream(fishesStr.split(",")).mapToInt(Integer::parseInt).toArray();
				for (int i = 0; i < fishesInput.length; i++)
					fishes[fishesInput[i]]++;
			}
		}
		print("{}", fishes);
		for (int d = 0; d < ndays; d++) {
			long newBorns = fishes[0];
			for (int i = 1; i < MAX_GESTATION; i++)
				fishes[i - 1] = fishes[i];
			fishes[6] += newBorns;
			fishes[8] = newBorns;
			//			print("{}: {} (+{})", d, Arrays.toString(fishes), newBorns);
		}
		print("{}", Arrays.stream(fishes).sum());
	}

}
