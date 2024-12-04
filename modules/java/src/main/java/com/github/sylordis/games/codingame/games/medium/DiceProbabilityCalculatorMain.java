package com.github.sylordis.games.codingame.games.medium;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.github.sylordis.games.codingame.games.medium.specific.DiceProbabilityCalculator;

public class DiceProbabilityCalculatorMain {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String expression = in.nextLine();
		in.close();
		DiceProbabilityCalculator dpc = new DiceProbabilityCalculator();
		dpc.parseExpression(expression);
		Map<Integer, Float> probabilities = new TreeMap<>(dpc.getOutcomes());
		probabilities.entrySet().forEach(e -> System.out.printf("%d %.2f\n", e.getKey(), e.getValue() * 100));
	}

}
