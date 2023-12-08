package com.github.sylordis.games.codingame.games.easy;

import static com.github.sylordis.commons.utils.PrintUtils.debug;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/the-dart-101
 */
public class TheDart101 {

	static class WinException extends Exception {
		private final Player winner;

		public WinException(Player winner) {
			this.winner = winner;
		}

		public Player getWinner() {
			return winner;
		}
	}

	static class Player {
		private final String name;
		private int score;

		public Player(String name) {
			this.name = name;
			this.score = 0;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	static class Game {
		private static final int SCORE_CONS_MISSED = -10;
		private static final int SCORE_MISS = -20;
		private static final int SCORE_WIN = 101;
		private static final String SHOOT_MISS = "X";
		private static final int SHOOTS_PER_ROUND = 3;
		private static final String PATT_SHOT_MULT = "[0-9]\\*[0-9]+";

		private Map<Player, Deque<String>> shots;

		public Game() {
			shots = new LinkedHashMap<>();
		}

		public void addPlayer(Player player, List<String> shots) {
			this.shots.put(player, new LinkedList<>(shots));
		}

		public int analyseShot(String dart) {
			int points = SCORE_MISS;
			if (!SHOOT_MISS.equals(dart)) {
				if (dart.matches(PATT_SHOT_MULT)) {
					String parts[] = dart.split("\\*");
					points = Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
				} else
					points = Integer.parseInt(dart);
			}
			return points;
		}

		public Player play() {
			Player winner = null;
			try {
				while (!shots.keySet().stream().anyMatch(p -> p.getScore() == SCORE_WIN)) {
					for (Player player : shots.keySet()) {
						int darts = 0;
						int[] scores = new int[SHOOTS_PER_ROUND];
						int tempScore = player.getScore();
						boolean overflow = false;
						debug("{}, score={}", player, tempScore);
						while (darts < SHOOTS_PER_ROUND && !overflow) {
							final String shot = shots.get(player).pop();
							scores[darts] = analyseShot(shot);
							tempScore += scores[darts];
							if (darts > 0 && scores[darts] == SCORE_MISS && scores[darts - 1] == SCORE_MISS)
								tempScore += SCORE_CONS_MISSED;
							if (tempScore > SCORE_WIN)
								overflow = true;
							debug("[{}]: {}={} score={}", darts, shot, scores[darts], tempScore);
							if (tempScore == SCORE_WIN)
								throw new WinException(player);
							darts++;
						}
						if (Arrays.stream(scores).allMatch(i -> i == -20))
							player.setScore(0);
						else if (!overflow)
							player.setScore(tempScore);

					}
				}
			} catch (WinException e) {
				winner = e.getWinner();
			}
			return winner;
		}

	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.nextLine();
		Game game = new Game();
		// Players
		Player[] players = new Player[n];
		for (int i = 0; i < n; i++)
			players[i] = new Player(in.nextLine());
		// Shots
		for (int i = 0; i < n; i++) {
			List<String> shots = Arrays.asList(in.nextLine().split(" "));
			game.addPlayer(players[i], shots);
		}
		in.close();
		System.out.println(game.play());
	}

}
