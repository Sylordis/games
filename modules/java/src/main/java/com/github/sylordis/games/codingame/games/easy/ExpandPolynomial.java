package com.github.sylordis.games.codingame.games.easy;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpandPolynomial {

	static class Polynomial {

		private static final String TERM_FORMAT = "+%dx^%d";

		private static final ToIntFunction<String> POWER_OR_1 = t -> {
			try {
				return Integer.parseInt(t);
			} catch (NumberFormatException e) {
				return 1;
			}
		};

		private int[] factors;

		private static String mgod(Matcher matcher, int group, String def) {
			return matcher.group(group) == null ? def : matcher.group(group);
		}

		private static int toPower(String str) {
			int power = 0;
			if (str != null) {
				if (!str.contains("^"))
					power = 1;
				else {
					power = Integer.parseInt(str.substring(2));
				}
			}
			return power;
		}

		public static Polynomial createFrom(String expression) {
			List<String> terms = Arrays.asList(expression.split("(?=(-|\\+))", -1));
			int maxPower = terms.stream().filter(s -> s.contains("x")).map(s -> s.replaceAll("[0-9]*x\\^?", ""))
					.mapToInt(POWER_OR_1).max().getAsInt();
			int[] factors = new int[maxPower + 1];
			Pattern patt = Pattern.compile("([+-])?([0-9]+)?(x(\\^([0-9]+))?)?");
			for (String term : terms) {
				Matcher matcher = patt.matcher(term);
				matcher.matches();
				int factor = Integer.parseInt(mgod(matcher, 1, "+") + mgod(matcher, 2, "1"));
				int power = toPower(matcher.group(3));
				factors[power] = factor;
			}
			return new Polynomial(factors);
		}

		public Polynomial(int[] factors) {
			this.factors = factors;
		}

		public Polynomial multiply(Polynomial p) {
			final int[] fp = p.getFactors();
			int[] nfactors = new int[fp.length + factors.length - 1];
			for (int i1 = 0; i1 < fp.length; i1++) {
				for (int i2 = 0; i2 < factors.length; i2++) {
					nfactors[i1 + i2] += fp[i1] * factors[i2];
				}
			}
			final Polynomial poly = new Polynomial(nfactors);
			System.err.println("(" + this + ")(" + p + ")=" + poly);
			return poly;
		}

		@Override
		public String toString() {
			StringBuffer rame = new StringBuffer();
			for (int i = factors.length - 1; i >= 0; i--)
				if (factors[i] != 0)
					rame.append(String.format(TERM_FORMAT, factors[i], i));
			return rame.toString().substring(1).replaceAll("\\+-", "-").replaceAll("x\\^0", "").replaceAll("1x", "x")
					.replaceAll("x\\^1", "x");
		}

		public int[] getFactors() {
			return factors;
		}

		public void setFactors(int[] factors) {
			this.factors = factors;
		}

	}

	private final Deque<Polynomial> expressions;

	public ExpandPolynomial(String expression) {
		this.expressions = new LinkedList<>();
		Pattern patt = Pattern.compile("\\([^)]+\\)(\\^[0-9]+)?");
		Matcher matcher = patt.matcher(expression);
		while (matcher.find()) {
			String group = matcher.group();
			System.err.println("group: " + group);
			int repeat = 1;
			if (!group.endsWith(")")) {
				final int powerIndex = group.lastIndexOf("^");
				repeat = Integer.parseInt(group.substring(powerIndex + 1));
				group = group.substring(0, powerIndex);
			}
			group = group.substring(1, group.length() - 1);
			for (int i = 0; i < repeat; i++)
				this.expressions.add(Polynomial.createFrom(group));
		}
		System.err.println(this.expressions);
	}

	public String expand() {
		Deque<Polynomial> polys = new LinkedList<>(expressions);
		while (polys.size() > 1) {
			Polynomial p1 = polys.pollFirst();
			polys.offerFirst(p1.multiply(polys.pollFirst()));
		}
		return polys.pop().toString();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		final ExpandPolynomial poly = new ExpandPolynomial(in.nextLine());
		System.out.println(poly.expand());
		in.close();
	}
}
