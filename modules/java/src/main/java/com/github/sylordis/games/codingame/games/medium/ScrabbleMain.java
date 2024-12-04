package com.github.sylordis.games.codingame.games.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ScrabbleMain {

	private static final char[] VALUE_1 = new char[] { 'e', 'a', 'i', 'o', 'n', 'r', 't', 'l', 's', 'u' };
	private static final char[] VALUE_2 = new char[] { 'd', 'g' };
	private static final char[] VALUE_3 = new char[] { 'b', 'c', 'm', 'p' };
	private static final char[] VALUE_4 = new char[] { 'f', 'h', 'v', 'w', 'y' };
	private static final char[] VALUE_5 = new char[] { 'k' };
	private static final char[] VALUE_8 = new char[] { 'j', 'x' };
	private static final char[] VALUE_10 = new char[] { 'q', 'z' };

	private List<String> dictionary;
	private Map<Character, Integer> alphabet;
	private String letters;

	public ScrabbleMain() {
		prepareDictionaries();
		collectData();
		System.out.println(dictionary.parallelStream().max((a, b) -> getWordValue(a) - getWordValue(b)).get());
	}

	private void collectData() {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		in.nextLine();
		for (int i = 0; i < N; i++) {
			dictionary.add(in.nextLine());
		}
		letters = in.nextLine();
		in.close();
	}

	private int getWordValue(String s) {
		boolean flag = true;
		int i = 0;
		int score = 0;
		StringBuilder stock = new StringBuilder(letters);
		while (i < s.length() && flag) {
			String letter = "" + s.charAt(i);
			int pos = stock.indexOf(letter);
			if (pos >= 0) {
				stock.deleteCharAt(stock.indexOf(letter));
				score += alphabet.get(s.charAt(i));
			} else
				flag = false;
			i++;
		}
		return (flag ? score : 0);
	}

	private void prepareDictionaries() {
		dictionary = new ArrayList<>();
		alphabet = new HashMap<>();
		setLettersValue(1, VALUE_1);
		setLettersValue(2, VALUE_2);
		setLettersValue(3, VALUE_3);
		setLettersValue(4, VALUE_4);
		setLettersValue(5, VALUE_5);
		setLettersValue(8, VALUE_8);
		setLettersValue(10, VALUE_10);
	}

	private void setLettersValue(int value, char[] letters) {
		for (char c : letters) {
			alphabet.put(c, value);
		}
	}

	public static void main(String[] args) {
		new ScrabbleMain();
	}

}
