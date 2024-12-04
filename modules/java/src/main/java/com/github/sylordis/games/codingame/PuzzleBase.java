package com.github.sylordis.games.codingame;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.Scanner;

public class PuzzleBase {

	public static void main(String[] args) {
		new PuzzleBase().run();
	}

	private void run() {
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				// TODO
			}
		}
		print("{}", "Hello world");
	}

}
