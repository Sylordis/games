package com.github.sylordis.games.codingame.games.medium;

import static com.github.sylordis.commons.utils.PrintUtils.debug;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ElementaryCellularAutomatonMain {

	private static final int WOLFRAM_MAX_VALUE = 255;

	private Map<Integer, Integer> evolutionRules;

	public ElementaryCellularAutomatonMain() {
		evolutionRules = new HashMap<>();
	}

	public ElementaryCellularAutomatonMain(int rule) {
		this();
		setRulesFromWolfram(rule);
	}

	public long evolve(long cell, int length) {
		long newCell = 0;
		for (int i = length; i >= 0; i--) {
			int[] indices = { (i + 1) % length, i, (length + i - 1) % length };
			int rule = 0;
			for (int y = 0; y < 3; y++) {
				long val = (long) Math.pow(2, indices[y]);
				if ((cell & val) == val) {
					rule += (int) Math.pow(2, 2 - y);
				}
			}
			newCell += evolutionRules.get(rule) * (long) Math.pow(2, i);
		}
		return newCell;
	}

	/**
	 * Sets rules according to Wolfram number.
	 * 
	 * @param number
	 */
	public void setRulesFromWolfram(int number) {
		if (number > WOLFRAM_MAX_VALUE)
			throw new IllegalArgumentException("Wolfram code value cannot exceed " + WOLFRAM_MAX_VALUE);
		evolutionRules.clear();
		for (int i = 7; i >= 0; i--) {
			int val = (int) Math.pow(2, i);
			evolutionRules.put(i, (number & val) == val ? 1 : 0);
		}
		debug(evolutionRules);
	}

	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			int rule = in.nextInt();
			int lines = in.nextInt();
			String pattern = in.next();
			ElementaryCellularAutomatonMain eca = new ElementaryCellularAutomatonMain(rule);
			long cell = pattToCell(pattern);
			int length = pattern.length();
			for (int i = 0; i < lines; i++) {
				System.out.println(cellToPatt(cell, length));
				cell = eca.evolve(cell, length);
			}
		}
	}

	public static long pattToCell(String pattern) {
		String binary = pattern.replace('.', '0').replace('@', '1');
		return Integer.valueOf(binary, 2);
	}

	public static String cellToPatt(long cell, int length) {
		StringBuffer buff = new StringBuffer();
		for (int i = length - 1; i >= 0; i--) {
			int val = (int) Math.pow(2, i);
			buff.append(((cell & val) == val) ? '@' : '.');
		}
		return buff.toString();
	}

}
