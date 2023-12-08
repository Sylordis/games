package com.github.sylordis.games.codingame.games.easy;

import java.util.Scanner;

public class GuessingAndCheating {

	private static final int MIN_BOT = 1;
	private static final int MAX_TOP = 100;

	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			int nrounds = in.nextInt();
			if (in.hasNextLine())
				in.nextLine();
			int bot = MIN_BOT - 1;
			int top = MAX_TOP + 1;
			boolean cheated = false;
			int round = 1;
			for (; round <= nrounds && !cheated; round++) {
				int guess = in.nextInt();
				String answer = in.nextLine().trim();
				switch (answer) {
					case "too high":
						if (guess <= top)
							top = guess;
						if (top <= bot + 1 || guess <= MIN_BOT)
							cheated = true;
						break;
					case "too low":
						if (guess >= bot)
							bot = guess;
						if (bot + 1 >= top || guess >= MAX_TOP)
							cheated = true;
						break;
					case "right on":
						if (guess <= bot || guess >= top)
							cheated = true;
						break;
				}
			}
			System.out.println(cheated ? "Alice cheated in round " + --round : "No evidence of cheating");
		}
	}

}
