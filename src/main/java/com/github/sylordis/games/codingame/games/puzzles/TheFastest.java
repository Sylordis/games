package com.github.sylordis.games.codingame.games.puzzles;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TheFastest {

	private static String TIME_FORMAT = "HH:mm:SS";
	private static DateFormat DATE_FORMAT = new SimpleDateFormat(TIME_FORMAT);

	private List<Date> times;

	public TheFastest(String[] times, String format) throws ParseException {
		this.times = new ArrayList<>();
		for (String s : times) {
			this.times.add(DATE_FORMAT.parse(s));
		}
	}

	public Date getMin() {
		return times.stream().min((d1, d2) -> d1.compareTo(d2)).get();
	}

	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			int N = in.nextInt();
			String[] times = new String[N];
			for (int i = 0; i < N; i++)
				times[i] = in.next();
			TheFastest solution;
			solution = new TheFastest(times, TIME_FORMAT);
			System.out.println(DATE_FORMAT.format(solution.getMin()));
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

}
