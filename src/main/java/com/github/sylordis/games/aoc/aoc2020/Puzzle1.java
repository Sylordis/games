package com.github.sylordis.games.aoc.aoc2020;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Puzzle1 implements Runnable {

	private static int YEAR = 2020;

	public static void main(String[] args) {
		new Puzzle1().run();
	}

	@Override
	public void run() {
		try (Scanner in = new Scanner(System.in)) {
			List<Integer> data = new ArrayList<>();
			while (in.hasNext())
				data.add(in.nextInt());
			for (int i = 0; i < data.size(); i++)
				for (int j = 0; j < data.size(); j++) {
					for (int k = 0; k < data.size(); k++) {
						int a = data.get(i);
						int b = data.get(j);
						int c = data.get(k);
						if (i != j && i != k && j != k && a + b + c == YEAR) {
							print("{} * {} * {} = {}", a, b, c, a * b * c);
						}
					}
				}
		}
	}

}
