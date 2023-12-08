package com.github.sylordis.games.aoc.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.github.sylordis.commons.boards.BingoBoard;
import com.github.sylordis.commons.utils.PrintUtils;

public class Day4 {

	private final static int BINGO_HEIGHT = 5;
	private final static int BINGO_WIDTH = 5;

	public static void main(String[] args) {
		new Day4().run2();
	}

	public void run1() {
		int[] numbers = null;
		List<BingoBoard> boards = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			boolean isFirstLine = true;
			String line;
			while (in.hasNext()) {
				if (isFirstLine) {
					// First line: Numbers coming up
					line = in.nextLine();
					numbers = Arrays.stream(line.split(",")).mapToInt(n -> Integer.parseInt(n)).toArray();
					isFirstLine = false;
				} else {
					// Then 5x5 boards of numbers
					//					System.out.println("Setting board #" + boards.size());
					BingoBoard board = new BingoBoard(BINGO_WIDTH, BINGO_HEIGHT);
					for (int i = 0; i < 5; i++) {
						line = in.nextLine().trim();
						board.setRowFromString(i, line, " +");
					}
					boards.add(board);
					//					System.out.println(MatrixUtils.printMatrixes(board.getTiles(), board.getMarked()));
				}
				// 1 empty line between each
				if (in.hasNext())
					in.nextLine();
			}
		}
		PrintUtils.print("#boards: {}", boards.size());
		int i = 0;
		boolean bingo = false;
		while (!bingo && i < numbers.length) {
			final int n = numbers[i];
			//			System.out.println(n + " ____________________________________________________________");
			boards.forEach(b -> b.mark(n));
			//			boards.forEach(b -> System.out.println(MatrixUtils.printMatrixes(b.getTiles(), b.getMarked())));
			bingo = boards.stream().anyMatch(b -> b.hasBingo());
			i++;
		}
		BingoBoard winning = boards.stream().filter(b -> b.hasBingo()).findFirst().get();
		int sumUnmarked = 0;
		for (int y = 0; y < winning.getHeight(); y++) {
			for (int x = 0; x < winning.getWidth(); x++) {
				if (!winning.isMarked(x, y))
					sumUnmarked += winning.get(x, y);
			}
		}
		System.out.println(sumUnmarked * numbers[i - 1]);
	}

	public void run2() {
		int[] numbers = null;
		List<BingoBoard> boards = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			boolean isFirstLine = true;
			String line;
			while (in.hasNext()) {
				if (isFirstLine) {
					// First line: Numbers coming up
					line = in.nextLine();
					numbers = Arrays.stream(line.split(",")).mapToInt(n -> Integer.parseInt(n)).toArray();
					isFirstLine = false;
				} else {
					// Then 5x5 boards of numbers
					// System.out.println("Setting board #" + boards.size());
					BingoBoard board = new BingoBoard(BINGO_WIDTH, BINGO_HEIGHT);
					for (int i = 0; i < 5; i++) {
						line = in.nextLine().trim();
						board.setRowFromString(i, line, " +");
					}
					boards.add(board);
					// System.out.println(MatrixUtils.printMatrixes(board.getTiles(), board.getMarked()));
				}
				// 1 empty line between each
				if (in.hasNext())
					in.nextLine();
			}
		}
		PrintUtils.print("#boards: {}", boards.size());
		int i = 0;
		List<BingoBoard> previousBoards = null;
		while (!boards.isEmpty() && i < numbers.length) {
			final int n = numbers[i];
			boards.forEach(b -> b.mark(n));
			previousBoards = new ArrayList<>(boards);
			// boards.forEach(b -> System.out.println(MatrixUtils.printMatrixes(b.getTiles(), b.getMarked())));
			boards.removeAll(boards.stream().filter(b -> b.hasBingo()).collect(Collectors.toList()));
			i++;
		}
		BingoBoard lastWinning = previousBoards.stream().filter(b -> b.hasBingo()).findFirst().get();
		int sumUnmarked = 0;
		for (int y = 0; y < lastWinning.getHeight(); y++) {
			for (int x = 0; x < lastWinning.getWidth(); x++) {
				if (!lastWinning.isMarked(x, y))
					sumUnmarked += lastWinning.get(x, y);
			}
		}
		System.out.println(sumUnmarked * numbers[i - 1]);
	}

}
