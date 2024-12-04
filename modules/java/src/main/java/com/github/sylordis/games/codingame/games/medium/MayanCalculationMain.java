package com.github.sylordis.games.codingame.games.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.github.sylordis.commons.math.calculus.LongOperation;
import com.github.sylordis.commons.symbols.Symbol;
import com.github.sylordis.commons.symbols.SymbolDictionary;

public class MayanCalculationMain {

	private static final int NUMBERS = 20;
	private SymbolDictionary<Integer> dictionary;

	public MayanCalculationMain() {
		Scanner in = new Scanner(System.in);
		int L = in.nextInt();
		int H = in.nextInt();
		createDictionary(in, L, H);
		List<Symbol> signs1 = collectNextNumber(in, H, L, in.nextInt());
		List<Symbol> signs2 = collectNextNumber(in, H, L, in.nextInt());
		String operation = in.next();
		in.close();
		System.err.println("Number A:");
		int numberA = mayanToInt(signs1);
		System.err.println("Number B:");
		int numberB = mayanToInt(signs2);
		long result = calculate(numberA, numberB, operation);
		System.err.println("Operation: " + numberA + operation + numberB + "=" + result);
		List<Symbol> mayanResult = longToMayan(result);
		StringBuilder rame = new StringBuilder();
		mayanResult.forEach(g -> rame.append(g));

		System.out.println(rame.toString());
	}

	private long calculate(int numberA, int numberB, String operation) {
		return LongOperation.signToOperation(operation).apply((long) numberA, (long) numberB);
	}

	private List<Symbol> collectNextNumber(Scanner in, int H, int L, int n) {
		List<Symbol> list = new ArrayList<>();
		Symbol sign = new Symbol(H);
		for (int i = 0; i < n; i++) {
			if (i % L == 0) {
				sign = new Symbol(H);
				list.add(sign);
			}
			sign.setRow(i % L, in.next());
		}
		return list;
	}

	private void createDictionary(Scanner in, int L, int H) {
		this.dictionary = new SymbolDictionary<>(L, H);
		for (int i = 0; i < H; i++) {
			String numeral = in.next();
			for (int w = 0; w < L * NUMBERS; w += L) {
				dictionary.setGraphicRow(w / L, i, numeral.substring(w, w + L));
			}
		}
	}

	private List<Symbol> longToMayan(long result) {
		String toBase = Long.toString(result, NUMBERS);
		System.err.println("Convertion: " + result + " =(20) " + toBase);
		toBase.chars().map(c -> Integer.parseInt("" + (char) c, Character.MAX_RADIX))
				.forEach(n -> System.err.print(Integer.toString(n, 36) + "=>" + n + " "));
		System.err.println();
		return toBase.chars().map(c -> Integer.parseInt("" + (char) c, Character.MAX_RADIX)).boxed()
				.map(n -> dictionary.getGraphic(n)).collect(Collectors.toList());
	}

	private int mayanToInt(List<Symbol> signs) {
		int sum = 0;
		for (int i = 0; i < signs.size(); i++) {
			int toInt = dictionary.getMatching(signs.get(i)) * (int) Math.pow(20, signs.size() - 1 - i);
			sum += toInt;
			System.err.println(dictionary.getMatching(signs.get(i)) + "*20^" + (signs.size() - 1 - i) + "=" + toInt
					+ " (" + sum + ")");
		}
		return sum;
	}

	public static void main(String[] args) {
		new MayanCalculationMain();
	}

}
