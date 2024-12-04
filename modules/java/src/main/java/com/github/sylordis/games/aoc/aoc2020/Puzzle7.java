package com.github.sylordis.games.aoc.aoc2020;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Puzzle7 {

	private Map<String, Set<Bag>> bagRules;

	public Puzzle7() {
		bagRules = new TreeMap<>();
		load();
		// v1
		Set<String> bags = searchWhatCanContain("shiny gold");
		print("{}", bags.size());
		//		print(getNumberOfBagsFor("shiny gold"));
	}

	public void load() {
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String line = in.nextLine();
				Pattern pattern = Pattern.compile("([0-9]* ?[^0-9.,]* bags?)");
				Matcher matcher = pattern.matcher(line);
				String key = null;
				while (matcher.find()) {
					final String group = matcher.group();
					String name = group.substring(0, group.indexOf("bag") - 1);
					if (key == null) {
						bagRules.put(name, new HashSet<>());
						key = name;
					} else {
						name = name.replaceAll("[0-9]+ ", "");
						bagRules.get(key).add(new Bag(name, Integer.parseInt(group.replaceAll("[^0-9]+", ""))));
					}
				}
				print("{}={}", key, bagRules.get(key));
			}
		}
	}

	public Set<String> searchWhatCanContain(String bag) {
		Set<String> bags = new HashSet<>();
		List<String> temp = bagRules.entrySet().stream().filter(e -> e.getValue().contains(bag)).map(Map.Entry::getKey)
				.collect(Collectors.toList());
		if (!temp.isEmpty()) {
			bags.addAll(temp);
			temp.forEach(b -> bags.addAll(searchWhatCanContain(b)));
		}
		return bags;
	}

	public int getNumberOfBagsFor(String bag) {
		return 0;
	}

	public static void main(String[] args) {
		new Puzzle7();
	}

	private class Bag {
		private final String id;
		private final int number;

		public Bag(String id, int n) {
			this.id = id;
			this.number = n;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @return the number
		 */
		public int getNumber() {
			return number;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (obj.getClass() == String.class && obj.equals(this.id))
				return true;
			if (getClass() != obj.getClass())
				return false;
			Bag other = (Bag) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}

		private Puzzle7 getEnclosingInstance() {
			return Puzzle7.this;
		}

	}
}
