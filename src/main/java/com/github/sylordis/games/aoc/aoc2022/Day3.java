package com.github.sylordis.games.aoc.aoc2022;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.sylordis.commons.utils.StringUtils;

public class Day3 {

	public static void main(String[] args) {
		new Day3().part2();
	}

	private void part1() {
		long prioritiesSum = 0L;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String rucksack = in.nextLine();
				String[] rucksackHalves = { rucksack.substring(0, rucksack.length()/2), rucksack.substring(rucksack.length()/2, rucksack.length()) };
				Set<Character> common = StringUtils.getCommonDistinctCharacters(rucksackHalves[0], rucksackHalves[1]);
				int priority = common.stream().mapToInt(this::getPriority).sum();
				print("{}, common={}, priority={}", Arrays.toString(rucksackHalves), common, priority);
				prioritiesSum += priority;
			}
		}
		print("{}", prioritiesSum);
	}
	
	private void part2() {
		long prioritiesSum = 0L;
		try (Scanner in = new Scanner(System.in)) {
			List<String> group = new ArrayList<>();
			while (in.hasNext()) {
				String rucksack = in.nextLine();
				group.add(rucksack);
				if (group.size() == 3) {
					Set<Character> common = StringUtils.getCommonDistinctCharacters(group.toArray(String[]::new));
					int priority = common.stream().mapToInt(this::getPriority).sum();
					prioritiesSum += priority;
					print("{}, common={}, priority={}", group, common, priority);
					group.clear();
				}
			}
		}
		print("{}", prioritiesSum);
	}

	private int getPriority(char c) {
		int temp;
		if (c <= 'Z')
			// Uppercase
			temp = c - 'A' + 27;
		else
			// Lowercase
			temp = c - 'a' + 1;
		return temp;
	}

	private void checkConversion() {
		Map<Character,Integer> letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".chars().mapToObj(c -> (Character) (char) c)
				.collect(Collectors.toMap(Character::valueOf, c -> getPriority((char) c)));
		print("{}", letters);
	}

}
