package com.github.sylordis.games.aoc.aoc2020;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Puzzle6v2 {

	private List<List<String>> groups;

	public Puzzle6v2() {
		groups = new ArrayList<>();
		load();
		List<String> allAnswered = processGroups();
		print(allAnswered.stream().mapToInt(e -> e.length()).sum());
	}

	public void load() {
		try (Scanner in = new Scanner(System.in)) {
			String line;
			List<String> group = new ArrayList<>();
			while (in.hasNext()) {
				line = in.nextLine();
				if (line.isEmpty()) {
					groups.add(group);
					group = new ArrayList<>();
				} else {
					group.add(line);
				}
			}
			groups.add(group);
		}
	}

	public List<String> processGroups() {
		List<String> filtered = new ArrayList<>();
		for (List<String> group : groups) {
			Map<Character, Integer> counts = new HashMap<>();
			for (String s : group) {
				for (int i = 0; i < s.length(); i++) {
					counts.compute(s.charAt(i), (k, v) -> (v == null) ? 0 : v + 1);
				}
			}
			StringBuffer buffer = new StringBuffer();
			for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
				if (entry.getValue() == group.size() - 1)
					buffer.append(entry.getKey());
			}
			filtered.add(buffer.toString());
		}
		return filtered;
	}

	public static void main(String[] args) {
		new Puzzle6v2();

	}

}
