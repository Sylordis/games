package com.github.sylordis.games.aoc.aoc2022;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.Scanner;

import com.github.sylordis.commons.utils.StringUtils;

public class Day6 {

	private final int MARKER_SIZE = 4;
	private final int MSG_MARKER_SIZE = 14;

	public static void main(String[] args) {
		new Day6().part2();
	}

	private void part1() {
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String line = in.nextLine();
				String marker = detectMarker(line, MARKER_SIZE);
				print("{} : [{}]{}", line, marker, line.indexOf(marker) + MARKER_SIZE);
			}
		}
	}
	
	private void part2() {
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String line = in.nextLine();
				String marker = detectMarker(line, MSG_MARKER_SIZE);
				print("{} : [{}]{}", line, marker, line.indexOf(marker) + MSG_MARKER_SIZE);
			}
		}
	}

	private String detectMarker(String line, int size) {
		String marker = null;
		for (int i = 0; i < line.length() - size + 1; i++) {
			String markerTry = line.substring(i, i + size);
			if (StringUtils.strToSet(markerTry).size() == size) {
				marker = markerTry;
				break;
			}
		}
		return marker;
	}

}
