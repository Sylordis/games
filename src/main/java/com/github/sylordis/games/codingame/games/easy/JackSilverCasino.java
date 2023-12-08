package com.github.sylordis.games.codingame.games.easy;

import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class JackSilverCasino {

	enum Call {
		EVEN((c, b) -> c % 2 == 0 && c != 0), ODD((c, b) -> c % 2 == 1), PLAIN((c, b) -> c == b);

		private BiPredicate<Integer, Integer> success;

		private Call(BiPredicate<Integer, Integer> success) {
			this.success = success;
		}

		public boolean succeeds(int current, int betOn) {
			return success.test(current, betOn);
		}

	}

	static class Player {
		private int cash;
		private BiFunction<Integer, Call, Integer> bettingStrategy;

		public Player(int cash, BiFunction<Integer, Call, Integer> bettingStrategy) {
			this.bettingStrategy = bettingStrategy;
			this.cash = cash;
		}

		public int bet(Call call) {
			final int amount = bettingStrategy.apply(cash, call);
			cash -= amount;
			return amount;
		}

		public void addCash(int amount) {
			cash += amount;
		}

		public int getCash() {
			return cash;
		}

		@Override
		public String toString() {
			return Integer.toString(cash);
		}
	}

	static class Casino {

		public void play(Player player, String line) {
			String[] parts = line.split(" ");
			Call call = Call.valueOf(parts[1]);
			int bet = player.bet(call);
			int betOn = call == Call.PLAIN ? Integer.parseInt(parts[2]) : 0;
			int current = Integer.parseInt(parts[0]);
			if (call.succeeds(current, betOn))
				player.addCash(bet + getWinnings(call, bet));
		}

		public int getWinnings(Call call, int bet) {
			return call == Call.PLAIN ? bet * 35 : bet;
		}

	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int ROUNDS = in.nextInt();
		Player player = new Player(in.nextInt(), (cash, call) -> (int) Math.ceil((double) cash / 4));
		Casino house = new Casino();
		if (in.hasNextLine())
			in.nextLine();
		for (int i = 0; i < ROUNDS; i++)
			house.play(player, in.nextLine());
		in.close();
		System.out.println(player);
	}

}
