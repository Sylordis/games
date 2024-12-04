package com.github.sylordis.games.codingame.games.easy;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class HowTimeFlies {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public String diff(String periodBegin, String periodEnd) {
		StringBuffer rame = new StringBuffer();
		LocalDate begin = LocalDate.parse(periodBegin, FORMAT);
		LocalDate end = LocalDate.parse(periodEnd, FORMAT);
		Period period = Period.between(begin, end);
		if (period.getYears() > 0)
			normalise(rame, period.getYears(), "%d year");
		if (period.getMonths() % 12 > 0)
			normalise(rame, period.getMonths() % 12, "%d month");
		normalise(rame, ChronoUnit.DAYS.between(begin, end), "total %d days", false);
		return rame.toString();
	}

	private void normalise(StringBuffer buffer, long number, String format) {
		normalise(buffer, number, format, true);
	}

	// Welcome to lazy town
	private void normalise(StringBuffer buffer, long number, String format, boolean plural) {
		if (buffer.length() > 0)
			buffer.append(", ");
		buffer.append(String.format(format, number));
		if (number > 1 && plural)
			buffer.append("s");
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println(new HowTimeFlies().diff(in.nextLine(), in.nextLine()));
		in.close();
	}
}
