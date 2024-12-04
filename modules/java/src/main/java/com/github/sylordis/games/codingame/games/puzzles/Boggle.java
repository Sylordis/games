package com.github.sylordis.games.codingame.games.puzzles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.github.sylordis.commons.Pair;

public class Boggle {

	// --- CONSTANTS

	public static int SIZE = 4;

	// --- FIELDS

	/**
	 * Grid of letters of the game.
	 */
	private char[][] grid;
	/**
	 * All available characters with their mapping
	 */
	private Collection<Pair<Pair<Integer, Integer>, Character>> letters;
	/**
	 * Dictionary of words to find.
	 */
	private Map<String, Boolean> dictionary;

	// --- CONSTRUCTORS

	/**
	 * Builds a new Boggle.
	 */
	public Boggle() {
		grid = new char[0][0];
		dictionary = new LinkedHashMap<>();
		letters = new ArrayList<>();
	}

	// --- MODIFIERS

	/**
	 * Adds one word to the dictionary.
	 *
	 * @param word
	 */
	public void addDictionaryEntry(String word) {
		this.dictionary.put(word, false);
	}

	/**
	 * Computes the grid to find words.
	 */
	public void compute() {
		for (String word : dictionary.keySet()) {
			System.err.println("Looking for '" + word + "'");
			Collection<Pair<boolean[][], Pair<Integer, Integer>>> permutations = null;
			int index = 0;
			boolean feasible = true;
			while (feasible && index < word.length()) {
				char next = word.charAt(index);
				System.err.println("  Checking letter '" + next + "'");
				Collection<Pair<Integer, Integer>> nextLetters = letters.stream()
						.filter(t -> t.getSecond().charValue() == next).map(t -> t.getFirst())
						.collect(Collectors.toList());
				if (nextLetters.isEmpty()) {
					// No letter was found
					System.err.println("    Could not find it =(");
					feasible = false;
				} else {
					System.err.println("    Letters found=" + nextLetters);
					System.err.println("    Permutations=" + permutations);
					Collection<Pair<boolean[][], Pair<Integer, Integer>>> newPermutations = new ArrayList<>();
					// For each letter found
					for (Pair<Integer, Integer> pos : nextLetters) {
						boolean[][] parsed;
						if (index == 0) {
							parsed = new boolean[SIZE][SIZE];
							parsed[pos.getFirst()][pos.getSecond()] = true;
							newPermutations.add(new Pair<>(parsed, pos));
						} else {
							for (Pair<boolean[][], Pair<Integer, Integer>> permutation : permutations) {
								System.err.println("      Checking permutation " + pos + "x" + permutation.getSecond());
								// Check if any is nearby current position for each permutation to put it as new
								// permutation
								if (isNeighbour(pos, permutation)
										&& !permutation.getFirst()[pos.getFirst()][pos.getSecond()]) {
									System.err.println("        Accepted");
									// Clone parsed
									parsed = deepMatrixCopy(permutation.getFirst());
									parsed[pos.getFirst()][pos.getSecond()] = true;
									newPermutations.add(new Pair<>(parsed, pos));
								}
							}
						}
					}
					if (permutations != null)
						permutations.clear();
					permutations = newPermutations;
					System.err.println("    Permutations=" + permutations);
					if (permutations.isEmpty() && index < word.length())
						feasible = false;
					index++;
				}
			}
			if (feasible)
				dictionary.put(word, true);
		}
	}

	/**
	 * Deep copies one 2D matrix.
	 *
	 * @param matrix
	 * @return
	 */
	private boolean[][] deepMatrixCopy(boolean[][] matrix) {
		final boolean[][] result = new boolean[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
			result[i] = Arrays.copyOf(matrix[i], matrix[i].length);
		return result;
	}

	/**
	 * Checks if position 1 is around position 2 and that it was not already parsed.
	 *
	 * @param p1
	 * @param p2
	 * @return true
	 */
	private boolean isNeighbour(Pair<Integer, Integer> p1, Pair<boolean[][], Pair<Integer, Integer>> p2) {
		return !p2.getFirst()[p1.getFirst()][p1.getSecond()] && Math.abs(p1.getFirst() - p2.getSecond().getFirst()) <= 1
				&& Math.abs(p1.getSecond() - p2.getSecond().getSecond()) <= 1;
	}

	// --- OVERRIDEN METHODS

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				builder.append((char) grid[y][x]).append(" ");
			}
			builder.append("\n");
		}
		builder.append("Dictionary=").append(dictionary);
		builder.append(" Letters=").append(letters);
		builder.append("\n").append("Found      =").append(getFoundWords());
		return builder.toString();
	}

	// --- GETTERS & SETTERS

	/**
	 * @return the letters
	 */
	public char[][] getLetters() {
		return grid;
	}

	/**
	 * @param letters
	 *            the letters to set
	 */
	public void setLetters(char[][] letters) {
		this.grid = letters;
	}

	/**
	 * Sets the grid of letters.
	 *
	 * @param grid
	 */
	public void setLetters(String[] grid) {
		if (grid.length == 0 || grid[1] == null || grid[1].isEmpty())
			return;
		this.grid = new char[grid.length][grid.length];
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length(); x++) {
				this.grid[y] = grid[y].toCharArray();
				letters.add(new Pair<>(new Pair<>(y, x), grid[y].charAt(x)));
			}
		}
	}

	/**
	 * @return the foundWords
	 */
	public Collection<String> getFoundWords() {
		return dictionary.entrySet().stream().filter(e -> e.getValue()).map(e -> e.getKey())
				.collect(Collectors.toList());
	}

	/**
	 * @return the dictionary
	 */
	public Collection<String> getDictionary() {
		return dictionary.keySet();
	}

	// --- MAIN

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Boggle boggle = new Boggle();
		// Set letters grid
		String[] grid = new String[SIZE];
		for (int i = 0; i < SIZE; i++)
			grid[i] = in.nextLine();
		boggle.setLetters(grid);
		// Set dictionary
		int n = in.nextInt();
		for (int i = 0; i < n; i++)
			boggle.addDictionaryEntry(in.next());
		System.err.println(boggle);
		// Compute
		boggle.compute();
		System.err.println(boggle);
		// Analyse
		boggle.getDictionary().forEach(s -> System.out.println(boggle.getFoundWords().contains(s) ? "true" : "false"));
		in.close();
	}

}
