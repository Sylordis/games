package com.github.sylordis.games.aoc.aoc2022;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 {

	private List<LinkedList<String>> stacks;

	public static void main(String[] args) {
		new Day5().part2();
	}

	private void part1() {
		List<String> stackLines = new ArrayList<>();
		String order = null;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String line = in.nextLine();
				if (!line.isEmpty())
					stackLines.add(line);
				else
					break;
			}
			createStacksFromInput(stackLines);
			Pattern orderPattern = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");
			while (in.hasNext()) {
				order = in.nextLine();
				Matcher matcher = orderPattern.matcher(order);
				if (matcher.matches()) {
					int n = Integer.valueOf(matcher.group(1));
					int colFrom = Integer.valueOf(matcher.group(2));
					int colTo = Integer.valueOf(matcher.group(3));
//					print("{}, {}=[{}]=>{}", order, colFrom, n, colTo);
					for (int i = 0; i < n; i++)
						stacks.get(colTo - 1).add(stacks.get(colFrom - 1).removeLast());
				}
//				printStacks();
			}
		}
		StringBuilder rame = new StringBuilder();
		stacks.forEach(s -> rame.append(s.size() > 0 ? s.getLast() : ""));
		print("{}", rame.toString());
	}
	
	private void part2() {
		List<String> stackLines = new ArrayList<>();
		String order = null;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				String line = in.nextLine();
				if (!line.isEmpty())
					stackLines.add(line);
				else
					break;
			}
			createStacksFromInput(stackLines);
			Pattern orderPattern = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");
			while (in.hasNext()) {
				order = in.nextLine();
				Matcher matcher = orderPattern.matcher(order);
				if (matcher.matches()) {
					int n = Integer.valueOf(matcher.group(1));
					int colFrom = Integer.valueOf(matcher.group(2));
					int colTo = Integer.valueOf(matcher.group(3));
//					print("{}, {}=[{}]=>{}", order, colFrom, n, colTo);
					LinkedList<String> moving = new LinkedList<>();
					for (int i = 0; i < n; i++)
						moving.add(stacks.get(colFrom - 1).removeLast());
					Collections.reverse(moving);
					stacks.get(colTo - 1).addAll(moving);
				}
//				printStacks();
			}
		}
		StringBuilder rame = new StringBuilder();
		stacks.forEach(s -> rame.append(s.size() > 0 ? s.getLast() : ""));
		print("{}", rame.toString());
	}

	private void createStacksFromInput(List<String> stackLines) {
		stacks = new ArrayList<>();
		// Get number of stacks
		String lastLine = stackLines.get(stackLines.size() - 1);
		String[] parts = lastLine.split(" +");
		int nstacks = Integer.valueOf(parts[parts.length - 1]);
		// Create stacks bases
		for (int i = 0; i < nstacks; i++)
			stacks.add(new LinkedList<>());
		// Fill stacks from the top
		for (int i = 0; i < stackLines.size() - 1; i++) {
			String line = stackLines.get(i);
			// Filter the line to remove parasites
			String filteredLine = line.replaceAll(" ?\\[([A-Z])\\] ?", "$1").replaceAll("   ", ".").replace(" ", "");
//			print("line={}, filtered={}", line, filteredLine);
			for (int j = 0; j < stacks.size(); j++) {
				char letter = filteredLine.charAt(j);
				if (letter != '.')
					stacks.get(j).addFirst("" + letter);
			}
		}
//		printStacks();
	}

	private void printStacks() {
		print("{}", "Base stacks___");
		for (int i = 0; i < stacks.size(); i++)
			print("{}: {}", i + 1, stacks.get(i));
	}
}
