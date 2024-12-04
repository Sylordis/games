package com.github.sylordis.games.codingame.games.puzzles;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LongestPalindrome {

	public LongestPalindrome() {
	}

	private String parsePalindrome(String word, int index, int coreSize) {
		int l = index;
		// Right limit, be careful with multiple letters cores
		int r = index + coreSize - (coreSize > 1 ? 2 : 1);
		while (l > 0 && r < word.length() - 1 && word.charAt(l - 1) == word.charAt(r + 1)) {
			l--;
			r++;
		}
		return word.substring(l, r + 1);
	}

	public List<String> searchLongest(String word) {
		List<String> results = new ArrayList<>();
		int index;
		// First we get all palindromes
		for (int i = 1; i < word.length() - 1; i++) {
			index = i;
			// case 'AX+XB'
			if (word.charAt(i - 1) == word.charAt(i)) {
				index--;
				char patt = word.charAt(i);
				while (word.charAt(i) == patt)
					i++;
				results.add(parsePalindrome(word, index, i + 1 - index));
			} else if (word.charAt(i - 1) == word.charAt(i + 1)) {
				// case 'AXB'
				results.add(parsePalindrome(word, index, 1));
			}
		}
		System.err.println(results);
		// And now we get all longest palindromes
		int max = results.stream().mapToInt(s -> s.length()).max().getAsInt();
		return results.stream().filter(s -> s.length() == max).collect(Collectors.toList());
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		LongestPalindrome lp = new LongestPalindrome();
		List<String> answers = lp.searchLongest(in.nextLine());
		in.close();
		for (String answer : answers) {
			System.out.println(answer);
		}
	}

}
