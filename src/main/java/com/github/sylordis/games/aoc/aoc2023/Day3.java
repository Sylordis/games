package com.github.sylordis.games.aoc.aoc2023;

import java.util.ArrayList;
import java.util.List;

import com.github.sylordis.commons.Pair;
import com.github.sylordis.commons.boards.Board;
import com.github.sylordis.commons.utils.CharUtils;
import com.github.sylordis.games.aoc.AoCPuzzle;

record NumberOnBoard(int xstart, int xend, int y, long value) {
}

public class Day3 extends AoCPuzzle {

	@Override
	public void puzzle1() {
		List<String> lines = nomzInto(new ArrayList<>(), (s, l) -> l.add(s));
//		print(lines);
		Board board = new Board(lines.get(0).length(), lines.size());
		board.setBoardFrom(lines);
//		print(BoardUtils.printBoards(board));
		List<NumberOnBoard> numbers = findNumbers(board);
//		print(numbers);
		long sum = numbers.stream().filter(n -> hasSymbolAround(board, n)).mapToLong(NumberOnBoard::value).sum();
		print(sum);
	}

	@Override
	public void puzzle2() {
		List<String> lines = nomzInto(new ArrayList<>(), (s, l) -> l.add(s));
		Board board = new Board(lines.get(0).length(), lines.size());
		board.setBoardFrom(lines);
		List<NumberOnBoard> numbers = findNumbers(board);
		List<Pair<Integer, Integer>> stars = board.find((int) '*', (a, b) -> a == b);
//		print(stars);
		long total = 0L;
		for (Pair<Integer, Integer> star : stars) {
			List<NumberOnBoard> adjacentNumbers = getNumbersAround(star, numbers);
//			print(adjacentNumbers);
			if (adjacentNumbers.size() == 2)
				total += adjacentNumbers.get(0).value() * adjacentNumbers.get(1).value();
		}
		print(total);
	}

	/**
	 * Checks if A is adjacent to a number on the board.
	 * 
	 * @param x column on the board
	 * @param y row on the board
	 * @param n
	 * @return
	 */
	private boolean isAdjacentToNumber(int x, int y, NumberOnBoard n) {
		return x <= n.xend() + 1 && x >= n.xstart() - 1 && y <= n.y() + 1 && y >= n.y() - 1;
	}

	private List<NumberOnBoard> getNumbersAround(Pair<Integer, Integer> symbol, List<NumberOnBoard> numbers) {
		return numbers.stream().filter(n -> isAdjacentToNumber(symbol.first(), symbol.second(), n)).toList();
	}

	private boolean hasSymbolAround(Board board, NumberOnBoard number) {
//		print(number);
		boolean has = false;
		for (int y = number.y() - 1; y <= number.y() + 1; y++) {
			for (int x = number.xstart() - 1; x <= number.xend() + 1; x++) {
//				print("({},{})=" + (board.isInLimits(x, y) ? board.getAsChar(x, y) : 'ø'), x, y);
				if (board.isInLimits(x, y) && isSymbol(board.getAsChar(x, y))) {
//					print("is symbol");
					has = true;
					break;
				}
			}
		}
		return has;
	}

	private boolean isSymbol(char c) {
		return !CharUtils.isDigit(c) && c != '.';
	}

	/**
	 * Find all numbers in the board. It is assumed numbers are only written from left to right and
	 * horizontally.
	 * 
	 * @param board
	 * @return
	 */
	public List<NumberOnBoard> findNumbers(Board board) {
		List<NumberOnBoard> numbers = new ArrayList<>();
		// Find number start
		for (int y = 0; y < board.getHeight(); y++) {
			int xstart = -1;
			int xend = -1;
			for (int x = 0; x < board.getWidth(); x++) {
				char c = board.getAsChar(x, y);
//				print("({},{})=" + c + " ({})", x, y, isNumber(c));
				// Case 1: is current square a number.
				if (CharUtils.isDigit(c)) {
					// Case 2a: was there a number in progress? Update current/end index.
					// Case 3a: no number in progress? Start a new number.
					if (xstart == -1) {
						xstart = x;
//						print("Number start: ({},{})", x, y);
					}
					xend = x;
//					print("Number in progress: ({},{})", x, y);
				} else {
					// Case 2b: is there a number in progress? Then end the number.
					if (xstart != -1) {
						numbers.add(new NumberOnBoard(xstart, xend, y, getNumberValue(board, xstart, xend, y)));
						xstart = -1;
						xend = -1;
//						print("Number end: ({},{})", x - 1, y);
					}
					// Case 3b: no number in progress? Do nothing.
				}
			}
			// If number in progress: end number.
			if (xstart != -1) {
				numbers.add(new NumberOnBoard(xstart, xend, y, getNumberValue(board, xstart, xend, y)));
//				print("End of line, number added");
			}
		}
		return numbers;
	}

	private int getNumberValue(Board board, int xstart, int xend, int y) {
		StringBuilder rame = new StringBuilder();
		for (int x = xstart; x <= xend; x++) {
			rame.append(board.getAsChar(x, y));
		}
		return Integer.parseInt(rame.toString());
	}

	public static void main(String[] args) {
		new Day3().run(args);
	}

}
