package com.github.sylordis.games.aoc.aoc2023;

import static com.github.sylordis.commons.utils.StringUtils.replaceAnyOf;

import java.util.List;
import java.util.Map;

import com.github.sylordis.games.aoc.AoCPuzzle;

public class Day1 extends AoCPuzzle {

	public static void main(String[] args) {
		new Day1().run(args);
	}

	@Override
	public void puzzle1() {
		List<String> data = loadInput();
		long sum = 0L;
		for (String s : data) {
			String numberStr = s.replaceAll("[^0-9]", "");
//			print("{}, {}", s, numberStr);
			String transformed = "" + numberStr.charAt(0) + numberStr.charAt(numberStr.length() - 1);
			int theNumber = Integer.parseInt(transformed);
			sum += theNumber;
		}
		print("{}", sum);
	}

	@Override
	public void puzzle2() {
		List<String> data = loadInput();
		long sum = 0L;
		final Map<String, String> replacements = Map.of("one", "1", "two", "2", "three", "3", "four", "4", "five", "5",
		        "six", "6", "seven", "7", "eight", "8", "nine", "9");
		final String replKeys = ".*(" + String.join("|", replacements.keySet()) + ").*";
		for (String s : data) {
//			print("start {}|{}|", s, s.length());
			StringBuilder rame = new StringBuilder();
			int latestIndex = 0;
			for (int i = 1; i <= s.length(); i++) {
				String cut = s.substring(latestIndex, i);
				if (cut.matches(replKeys)) {
//					print("match on #{} ({})", i, cut);
					rame.append(replaceAnyOf(cut, replacements));
					latestIndex = i - 1;
				}
//				print("{}/{} rame1='{}' rame2='{}'", i, latestIndex, cut, rame);
			}
			rame.append(s.substring(latestIndex));
			String numberStr = rame.toString();
//			print("rame={}, ", numberStr);
			numberStr = numberStr.replaceAll("[^0-9]", "");
			String transformed = "" + numberStr.charAt(0) + numberStr.charAt(numberStr.length() - 1);
			int theNumber = Integer.parseInt(transformed);
//			print("replaced {}, number={}", numberStr, theNumber);
			sum += theNumber;
		}
		print("{}", sum);
	}

}
