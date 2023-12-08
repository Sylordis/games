package com.github.sylordis.games.codingame.games.puzzles;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SumOfDivisors {

	/**
	 * Resolves the problem
	 *
	 * @param n
	 * @return
	 */
	public long resolve(int n) {
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= Math.sqrt(n); i++) {
			for (int j = 1; j <= Math.sqrt(n); j++) {
				list.add(i * j);
				System.err.println(list);
			}
		}
		return list.stream().mapToLong(e -> e).sum();
	}

	public static void main(String[] args) {
		SumOfDivisors sum = new SumOfDivisors();
		Scanner in = new Scanner(System.in);
		System.out.println(sum.resolve(in.nextInt()));
		in.close();
	}

}
