package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class SimpleSafeCracking {

	public static final Map<Integer, String> DICTIONARY = new HashMap<>();

	static {
		DICTIONARY.put(0, "zero");
		DICTIONARY.put(1, "one");
		DICTIONARY.put(2, "two");
		DICTIONARY.put(3, "three");
		DICTIONARY.put(4, "four");
		DICTIONARY.put(5, "five");
		DICTIONARY.put(6, "six");
		DICTIONARY.put(7, "seven");
		DICTIONARY.put(8, "eight");
		DICTIONARY.put(9, "nine");
	}

	// --- FIELDS

	private String msg;
	private int cypher;
	private String decrypted;

	// --- CONSTRUCTORS

	public SimpleSafeCracking(String msg) {
		this.msg = msg;
		this.cypher = 0;
		this.decrypted = null;
	}

	// --- MODIFIERS

	public void findCypher() {
		System.err.println(Arrays.asList(msg.split(": ")[1].split("-")));
		Collection<String> numbers = new HashSet<>(
				Arrays.asList(msg.split(": ")[1].split("-")).stream().map(s -> s.trim()).collect(Collectors.toList()));
		System.err.println(numbers);
		for (String cyphered : numbers) {
			List<String> matches = DICTIONARY.values().stream().filter(e -> e.length() == cyphered.length())
					.collect(Collectors.toList());
			int match = 0;
			if (matches.size() == 1) {
				cypher = cyphered.charAt(0) - matches.get(0).charAt(0);
				break;
			} else {
				for (String entry : matches) {
					int tmatch = cyphered.charAt(0) - entry.charAt(0);
					boolean flag = true;
					for (int i = 0; i < cyphered.length(); i++) {

					}
				}
			}
		}
		// TODO
	}

	private int wordToNumber(String nextToken) {
		// TODO Auto-generated method stub
		return -1;
	}

	// --- GETTERS & SETTERS

	public String getDecryptedCombination() {
		String combination = null;
		if (decrypted != null) {
			StringBuilder builder = new StringBuilder();
			String[] parts = decrypted.split(": ");
			StringTokenizer tokenizer = new StringTokenizer(parts[1], "-");
			while (tokenizer.hasMoreTokens())
				builder.append(wordToNumber(tokenizer.nextToken()));
			combination = builder.toString();
		}
		return combination;
	}

	public String getDecryptedMessage() {
		return decrypted;
	}

	// --- MAIN

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String msg = in.nextLine();
		SimpleSafeCracking safe = new SimpleSafeCracking(msg);
		safe.findCypher();
		System.out.println(safe.getDecryptedCombination());
		in.close();
	}

}
