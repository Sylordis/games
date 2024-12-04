package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class TheGreatestNumber {

	public TheGreatestNumber() {
	}

	private String compute(String input) {
		System.err.println("compute(" + input + ")");
		String line = input;
		String result = "";
		// Get special characters
		boolean isNegative = line.contains("-");
		boolean isDecimal = line.contains(".");
		line = line.replaceAll(" -|- ", "");
		line = line.replaceAll(" \\.|\\. ", "");
		System.err.println("Filtered: " + line + " = " + Arrays.toString(line.split(" ")));
		Stream<Integer> stream = Arrays.stream(line.split(" ")).map(s -> Integer.parseInt(s));
		StringJoiner join = new StringJoiner("");
		// Sort according to negativity
		if (isNegative)
			stream.sorted().forEach(n -> join.add("" + n));
		else
			stream.sorted((n1, n2) -> n2.intValue() - n1.intValue()).forEach(n -> join.add("" + n));
		result = join.toString();
		System.err.println("joined: " + result);
		// Take care of decimal point
		if (isDecimal) {
			if (isNegative)
				result = result.charAt(0) + "." + result.substring(1);
			else
				result = result.substring(0, result.length() - 1) + "." + result.substring(result.length() - 1);
			System.err.println("decimal: " + result + "(" + result.endsWith(".0") + ")");
			if (result.endsWith("0"))
				result = result.replaceAll("\\.?0+$", "");
		} else {
			int tempRes = Integer.parseInt(result);
			result = "" + tempRes;
		}
		// Add negative signe if negative and not zero
		if (isNegative && Float.parseFloat(result) != 0)
			result = "-" + result;
		return result;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		input = in.nextLine();
		in.close();
		TheGreatestNumber tgn = new TheGreatestNumber();
		System.out.println(tgn.compute(input));
	}

}
