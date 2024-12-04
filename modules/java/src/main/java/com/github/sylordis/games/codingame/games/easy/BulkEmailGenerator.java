package com.github.sylordis.games.codingame.games.easy;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BulkEmailGenerator {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		final int N = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < N; i++) {
			buffer.append(in.nextLine()).append("\n");
		}
		in.close();

		String email = buffer.toString();

		// Get all matching patterns
		Pattern pattern = Pattern.compile("[(][^)]*[)]");
		Matcher matcher = pattern.matcher(email);
		int choice = 0;
		while (matcher.find()) {
			String matched = matcher.group();
			String[] options = matched.substring(1, matched.length() - 1).split(Pattern.quote("|"), -1);
			System.err.println(": " + Arrays.toString(options));
			email = email.replaceFirst(Pattern.quote(matched),
					Matcher.quoteReplacement(options[choice % options.length]));
			choice++;
		}

		System.out.println(email);
	}
}
