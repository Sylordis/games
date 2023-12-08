package com.github.sylordis.games.aoc.aoc2020;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Puzzle6 {

	private List<Set<Character>> groups;

	public Puzzle6() {
		groups = new ArrayList<>();
		load();
		print(groups.stream().mapToInt(Set::size).sum());
	}

	public void load() {
		try (Scanner in = new Scanner(System.in)) {
			String line;
			Set<Character> group = new HashSet<>();
			while (in.hasNext()) {
				line = in.nextLine();
				if (line.isEmpty()) {
					groups.add(group);
					group = new HashSet<>();
				} else {
					final Set<Character> groupTemp = group;
					line.chars().distinct().forEach(c -> groupTemp.add((char) c));
				}
			}
			groups.add(group);
		}
	}

	public static void main(String[] args) {
		new Puzzle6();

	}

}
