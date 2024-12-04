package com.github.sylordis.games.aoc.aoc2020;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Puzzle3 {

	private final char EMPTY = '.';
	private final char TREE = '#';
	private final char EMPTY_VISITED = 'O';
	private final char TREE_VISITED = 'X';

	private List<String> map = new ArrayList<>();

	private int travel;
	private int slope;

	public Puzzle3(int travel, int slope, List<String> map) {
		this.travel = travel;
		this.slope = slope;
		this.map = map;
	}

	public static void main(String[] args) {
		List<String> map = load();
		Collection<Puzzle3> toboggans = new ArrayList<>();
		toboggans.add(new Puzzle3(1, 1, map));
		toboggans.add(new Puzzle3(3, 1, map));
		toboggans.add(new Puzzle3(5, 1, map));
		toboggans.add(new Puzzle3(7, 1, map));
		toboggans.add(new Puzzle3(1, 2, map));
		long result = 1;
		for (Puzzle3 puzzle : toboggans) {
			final int slide = puzzle.slide();
			result *= slide;
			print("{}, {}", slide, result);
		}
		print("{}", result);
	}

	private static List<String> load() {
		List<String> map = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext())
				map.add(in.nextLine());
		}
		return map;
	}

	public int slide() {
		int index = 0;
		int trees = 0;
		for (int i = slope; i < map.size(); i += slope) {
			index = (index + travel) % map.get(0).length();
			if (map.get(i).charAt(index) == TREE)
				trees++;
		}
		return trees;
	}

}
