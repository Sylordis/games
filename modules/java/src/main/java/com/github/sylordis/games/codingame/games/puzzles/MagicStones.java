package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class MagicStones {

	// --- FIELDS

	private SortedMap<Integer, Integer> stones;

	// --- CONSTRUCTORS

	public MagicStones() {
		stones = new TreeMap<>();
	}

	// --- MODIFIERS

	/**
	 * Adds a stone into the collective.
	 *
	 * @param level
	 *            Level of the stone.
	 */
	public void addStone(int level) {
		stones.putIfAbsent(level, 0);
		stones.put(level, stones.get(level) + 1);
	}

	/**
	 * Applies the merging on the different stones.
	 */
	public void mergeAll() {
		final SortedMap<Integer, Integer> result = new TreeMap<>();
		while (!stones.isEmpty()) {
			System.err.println(stones);
			int level = stones.firstKey();
			int nstones = stones.get(level);
			if (nstones % 2 == 1) {
				result.put(level, 1);
				System.err.println("result=" + result);
			}
			if (nstones / 2 > 0) {
				stones.putIfAbsent(level + 1, 0);
				stones.put(level + 1, stones.get(level + 1) + nstones / 2);
			}
			stones.remove(level);
		}
		stones = result;
	}

	// --- GETTERS & SETTERS

	/**
	 * Sums the number of stones.
	 * 
	 * @return
	 */
	public int getNumberOfStones() {
		return stones.values().stream().mapToInt(e -> e).sum();
	}

	// --- MAIN

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		MagicStones stones = new MagicStones();
		final int N = in.nextInt();
		for (int i = 0; i < N; i++)
			stones.addStone(in.nextInt());
		stones.mergeAll();
		System.out.println(stones.getNumberOfStones());
		in.close();
	}
}
