package com.github.sylordis.games.codingame.games.easy;

import java.util.Arrays;
import java.util.Scanner;

public class TextFormatter {

	private static final String PUNCTUATION = "[.,:;]";

	public String correctSpacing(String txt) {
		return txt.replaceAll("(" + PUNCTUATION + ")", "$1 ");
	}

	public String removeSpacing(String txt) {
		return txt.replaceAll(" *(" + PUNCTUATION + ") *", "$1");
	}

	public String correctRepetitions(String txt) {
		return txt.replaceAll("(" + PUNCTUATION + ")+", "$1");
	}

	public String capitalise(String txt) {
		return txt.substring(0, 1).toUpperCase() + txt.substring(1).toLowerCase();
	}

	public String humanFormat(String txt) {
		final String[] operations = Arrays.stream(txt.split("[.]")).parallel().filter(s -> !s.isEmpty())
				.map(this::removeSpacing).map(this::correctRepetitions).map(String::trim).map(this::capitalise)
				.map(this::correctSpacing).toArray(String[]::new);
		return String.join(". ", operations) + (txt.endsWith(".") ? "." : "");
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println(new TextFormatter().humanFormat(in.nextLine()));
		in.close();
	}
}
