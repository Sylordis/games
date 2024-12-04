package com.github.sylordis.games.codingame.games.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TheGiftMain {

	private int[] budgets;

	public TheGiftMain() {
		Scanner in = new Scanner(System.in);
		int numberOfOods = in.nextInt();
		budgets = new int[numberOfOods];
		int price = in.nextInt();
		for (int i = 0; i < numberOfOods; i++) {
			budgets[i] = in.nextInt();
		}
		in.close();

		if (Arrays.stream(budgets).parallel().sum() < price)
			System.out.println("IMPOSSIBLE");
		else {
			List<Integer> oods = new ArrayList<>(Arrays.stream(budgets).boxed().collect(Collectors.toList()));
			List<Integer> payments = new ArrayList<>();
			while (price > 0) {
				int average = (int) Math.floor((double) price / oods.size());
				System.err.println("A: " + average);
				List<Integer> lessFortunates = oods.stream().filter(o -> o <= average).collect(Collectors.toList());
				if (0 < lessFortunates.size()) {
					// Some oods cannot contribute to the same amount
					price -= lessFortunates.stream().mapToInt(n -> n).sum();
					oods.removeAll(lessFortunates);
					payments.addAll(lessFortunates);
				} else {
					// Everyone remaining can contribute to the same amount
					price -= average * oods.size();
					int[] remPayments = new int[oods.size()];
					Arrays.fill(remPayments, average);
					for (int i = 0; i < price; i++) {
						remPayments[remPayments.length - 1 - i]++;
					}
					price = 0;
					payments.addAll(Arrays.stream(remPayments).boxed().collect(Collectors.toList()));
				}
				System.err.println("new price=" + price);
			} // end while
			payments.sort(Integer::compare);
			payments.forEach(p -> System.out.println(p));
		}
	}

	public static void main(String args[]) {
		new TheGiftMain();
	}
}