package com.github.sylordis.games.codingame.games.easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class XMLMDF2016 {

	static class XMLWeightCalculator {
		private String line;
		private Map<Character, Double> weights;

		public XMLWeightCalculator(String xml) {
			this.line = xml;
			weights = new HashMap<>();
		}

		public void compute() {
			boolean open = true;
			int index = 1;
			for (char ch : line.toCharArray()) {
				if (ch == '-')
					open = false;
				else {
					if (!open) {
						index--;
						open = true;
					} else {
						weights.computeIfAbsent(ch, c -> 0.0);
						final int level = index;
						weights.compute(ch, (c, w) -> w + 1.0 / level);
						index++;
					}
				}
			}
		}

		public Character getHeaviest() {
			return weights.entrySet().stream().max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue())).get()
					.getKey();
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		XMLWeightCalculator calc = new XMLWeightCalculator(in.nextLine());
		in.close();
		calc.compute();
		System.out.println(calc.getHeaviest());
	}

}
