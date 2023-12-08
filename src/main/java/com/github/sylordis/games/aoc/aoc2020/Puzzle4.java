package com.github.sylordis.games.aoc.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.function.Function;

import com.github.sylordis.commons.functional.validators.HexValidator;
import com.github.sylordis.commons.functional.validators.NumberStringValidator;
import com.github.sylordis.commons.utils.PrintUtils;

public class Puzzle4 {

	private static Function<String, Boolean> HEIGHT_VALIDATOR = new Function<String, Boolean>() {

		@Override
		public Boolean apply(String t) {
			boolean valid = true;
			if (!t.matches("[0-9]+(cm|in)"))
				valid = false;
			else {
				final int number = Integer.parseInt(t.substring(0, t.length() - 2));
				final String system = t.substring(t.length() - 2);
				if (system.equals("cm") && (number < 150 || number > 193))
					valid = false;
				if (system.equals("in") && (number < 59 || number > 76))
					valid = false;
			}
			return valid;
		}

	};

	private Map<String, Function<String, Boolean>> fields;

	public Puzzle4(Map<String, Function<String, Boolean>> fields) {
		this.fields = new HashMap<>(fields);
	}

	public boolean isValid(Map<String, String> passport) {
		boolean valid = true;
		for (Map.Entry<String, Function<String, Boolean>> field : fields.entrySet()) {
			boolean instantValid = passport.containsKey(field.getKey())
					&& field.getValue().apply(passport.get(field.getKey()));
			valid = valid && instantValid;
			if (!instantValid)
				PrintUtils.debug("{}: {}", field.getKey(), passport.containsKey(field.getKey()) ? "wrong" : "missing");
		}
		return valid;
	}

	public static void main(String[] args) {
		Map<String, Function<String, Boolean>> fields = new HashMap<>();
		fields.put("byr", new NumberStringValidator(4, 1920, 2002));
		fields.put("iyr", new NumberStringValidator(4, 2010, 2020));
		fields.put("eyr", new NumberStringValidator(4, 2020, 2030));
		fields.put("hgt", HEIGHT_VALIDATOR);
		fields.put("hcl", new HexValidator());
		fields.put("ecl",
				e -> Arrays.asList(new String[] { "amb", "blu", "brn", "gry", "grn", "hzl", "oth" }).contains(e));
		fields.put("pid", e -> e.matches("[0-9]{9}"));
		//		Collection<String> fields = new ArrayList<>(
		//				Arrays.asList(new String[] { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" }));
		Collection<Map<String, String>> passports = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			List<String> entries = new ArrayList<>();
			while (in.hasNext()) {
				String line = in.nextLine();
				if (line.isEmpty()) {
					StringBuffer buffer = new StringBuffer();
					for (String passPart : entries)
						buffer.append(" ").append(passPart);
					entries.clear();
					passports.add(stringToPassport(buffer.toString().substring(1)));
				} else {
					entries.add(line);
				}
			}
		}
		final Puzzle4 passportChecker = new Puzzle4(fields);
		int count = 0;
		for (Map<String, String> passport : passports) {
			if (passportChecker.isValid(passport)) {
				count++;
			} else {
				PrintUtils.debug("{}", passport);
			}
		}
		//		DebugUtils.print("{}", passports.stream().filter(passportChecker::isValid).count());
		System.out.println(count);
	}

	private static Map<String, String> stringToPassport(String string) {
		StringTokenizer tokenizer = new StringTokenizer(string);
		Map<String, String> passport = new HashMap<>();
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			String[] parts = token.split(":");
			passport.put(parts[0], parts.length > 0 ? parts[1] : "");
		}
		return passport;
	}


}
