package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class MagicSquare {

	// --- FIELDS

	private int[][] square;
	private int backDiag;
	private int size;
	private Map<Integer, Integer> values;

	// --- CONSTRUCTORS

	/**
	 * Constructs a new Square from the lines. It assumes the given lines are well formatted.
	 *
	 * @param lines
	 */
	public MagicSquare(String[] lines) {
		square = new int[lines.length + 1][lines.length + 1];
		size = lines.length;
		values = new HashMap<>();
		fill(lines);
	}

	// --- MODIFIERS

	/**
	 * Fills the matrix from lines.
	 *
	 * @param line
	 *            Lines to fill the matrix with
	 */
	private void fill(String[] lines) {
		for (int i = 0; i < lines.length; i++) {
			int pos = 0;
			StringTokenizer tokens = new StringTokenizer(lines[i]);
			while (tokens.hasMoreTokens()) {
				String token = tokens.nextToken();
				int value = Integer.parseInt(token);
				setValue(value, pos, i);
				pos++;
			}
		}
	}

	// --- GETTERS & SETTERS

	/**
	 * Gets if the square is magic. Needs to be computed first.
	 *
	 * @return
	 */
	public boolean isMagic() {
		boolean magic = !values.isEmpty();
		System.err.println(values);
		// Check that numbers should be in [1,n2]
		if (magic)
			magic = !values.keySet().stream().anyMatch(k -> k < 1 || k > size * size);
		// Check that each number is unique
		if (magic)
			magic = !values.values().stream().anyMatch(e -> e > 1);
		// Check borders
		if (magic) {
			Collection<Integer> endValues = Arrays.stream(square).map(s -> s[size]).collect(Collectors.toList());
			endValues.addAll(Arrays.stream(square[size]).boxed().collect(Collectors.toList()));
			endValues.add(backDiag);
			System.err.println(endValues);
			magic = endValues.stream().distinct().count() == 1;
		}
		return magic;
	}

	/**
	 * Sets a value of the square and sets borders.
	 *
	 * @param value
	 * @param x
	 * @param y
	 */
	public void setValue(int value, int x, int y) {
		// Modify old value
		int old = square[y][x];
		if (values.get(old) != null)
			values.put(old, values.get(old) - 1);
		// Set new value
		square[y][x] = value;
		values.putIfAbsent(value, 0);
		values.put(value, values.get(value) + 1);
		square[size][x] = square[size][x] - old + value;
		square[y][size] = square[y][size] - old + value;
		if (x == y)
			square[size][size] = square[size][size] - old + value;
		if (x == size - 1 - y)
			backDiag = backDiag - old + value;
	}

	// --- MAIN

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.nextLine();
		String[] lines = new String[n];
		for (int i = 0; i < n; i++)
			lines[i] = in.nextLine();
		MagicSquare ms = new MagicSquare(lines);
		System.out.println(ms.isMagic() ? "MAGIC" : "MUGGLE");
		in.close();
	}

}
