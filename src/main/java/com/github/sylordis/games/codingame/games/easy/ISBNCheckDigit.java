package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ISBNCheckDigit {

	static class ISBN {

		private final String isbn;
		private final int dividend;
		private Function<Integer, Integer> sumOp;

		public ISBN(String isbn) {
			this.isbn = isbn;
			this.sumOp = isbn.length() == 13 ? this::sum13 : this::sum10;
			this.dividend = isbn.length() == 13 ? 10 : 11;
		}

		public boolean isNotValid() {
			return !isValid();
		}

		private int sum13(int i) {
			return (isbn.charAt(i) - '0') * (2 - (int) Math.pow(-1, i));
		}

		private int sum10(int i) {
			return (isbn.charAt(i) - '0') * (10 - i);
		}

		public boolean isValid() {
			boolean valid = false;
			if (isbn.length() == 13 || isbn.length() == 10) {
				int sum = 0;
				int check = isbn.charAt(isbn.length() - 1) == 'X' ? 10 : isbn.charAt(isbn.length() - 1) - '0';
				for (int i = 0; i < isbn.length() - 1; i++)
					sum += sumOp.apply(i);
				final int rest = (dividend - (sum % dividend)) % dividend;
				valid = check == rest;
				System.err.println(isbn + "(" + isbn.length() + ")" + ": check=" + check + " sum=" + sum + " div="
						+ dividend + " rest=" + rest);
			}
			return valid;
		}

		@Override
		public String toString() {
			return isbn;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		Collection<ISBN> list = new ArrayList<>();
		for (int i = 0; i < N; i++)
			list.add(new ISBN(in.nextLine()));
		in.close();
		List<ISBN> invalids = list.parallelStream().filter(ISBN::isNotValid).collect(Collectors.toList());
		System.out.println(invalids.size() + " invalid:");
		invalids.forEach(System.out::println);
	}
}
