package com.github.sylordis.games.aoc.aoc2022;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Day2 {

	enum SignName {
		ROCK,
		PAPER,
		SCISSORS;
	}

	record Sign(SignName name, String enemyCall, String selfCall, int score, SignName beats, SignName isBeatenBy) {
	}

	private final int SCORE_WIN = 6;
	private final int SCORE_DRAW = 3;
	private final int SCORE_LOSS = 0;
	private final List<Sign> signs = List.of(new Sign(SignName.ROCK, "A", "X", 1, SignName.SCISSORS, SignName.PAPER),
	        new Sign(SignName.PAPER, "B", "Y", 2, SignName.ROCK, SignName.SCISSORS),
	        new Sign(SignName.SCISSORS, "C", "Z", 3, SignName.PAPER, SignName.ROCK));

	public static void main(String[] args) {
		new Day2().part2();
	}

	private void part1() {
		int score = 0;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String round = in.nextLine();
				Sign enemy = getSignByEnemyCall(round.split(" ")[0]);
				Sign self = getSignBySelfCall(round.split(" ")[1]);
				if (self.beats().equals(enemy.name))
					score += SCORE_WIN;
				else if (self.name.equals(enemy.name))
					score += SCORE_DRAW;
				else
					score += SCORE_LOSS;
				score += self.score();
			}
		}
		print("{}", score);
	}

	private void part2() {
		int score = 0;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String[] round = in.nextLine().split(" ");
				Sign enemy = getSignByEnemyCall(round[0]);
				Sign self = null;
				switch (round[1]) {
					// Lose
					case "X":
						self = getSign(s -> enemy.beats().equals(s.name()));
						score += SCORE_LOSS;
						break;
					// Draw
					case "Y":
						self = getSign(s -> s.name().equals(enemy.name()));
						score += SCORE_DRAW;
						break;
					// Win
					case "Z":
						self = getSign(s -> enemy.isBeatenBy().equals(s.name()));
						score += SCORE_WIN;
						break;
				}
				score += self.score();
			}
		}
		print("{}", score);
	}
	
	private Sign getSign(Predicate<? super Sign> filter) {
		return signs.stream().filter(filter).findFirst().get();
	}
	
	private Sign getSignByEnemyCall(String call) {
		return getSign(s -> s.enemyCall().equals(call));
	}

	private Sign getSignBySelfCall(String call) {
		return getSign(s -> s.selfCall().equals(call));
	}

}
