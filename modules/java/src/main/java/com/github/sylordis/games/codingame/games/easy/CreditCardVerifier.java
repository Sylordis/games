package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class CreditCardVerifier {

	static class CreditCard {

		private String numbers;

		public CreditCard(String numbers) {
			this.numbers = numbers;
		}

		public boolean checkValidity() {
			final String filtered = new StringBuilder(numbers.replace(" ", "")).reverse().toString();
			int step1 = getNumbers(filtered, i -> i % 2 == 1).map(i -> i * 2).map(i -> i > 9 ? i - 9 : i).sum();
			int step2 = getNumbers(filtered, i -> i % 2 == 0).sum();
			return (step1 + step2) % 10 == 0;
		}

		private IntStream getNumbers(String filtered, IntPredicate filter) {
			return IntStream.range(0, filtered.length()).filter(filter).boxed().map(filtered::charAt)
					.map(c -> Character.toString(c)).mapToInt(Integer::parseInt);
		}

	}

	public static void main(String[] args) {
		Collection<CreditCard> cards = new ArrayList<>();
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		if (in.hasNextLine())
			in.nextLine();
		for (int i = 0; i < n; i++)
			cards.add(new CreditCard(in.nextLine()));
		in.close();
		cards.stream().map(c -> c.checkValidity() ? "YES" : "NO").forEach(System.out::println);
	}

}
