package com.github.sylordis.games.aoc;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.github.sylordis.commons.utils.PrintUtils;

/**
 * Abstract class for puzzles.
 */
public abstract class AoCPuzzle {

//  This should be added to each puzzle file.
//	public static void main(String[] args) {
//		new DayX().run(args);
//	}

	/**
	 * Default method to run the puzzle.
	 * 
	 * @param args which puzzle to run.
	 */
	public void run(String[] args) {
		Class<?> current = this.getClass();
		try {
			AoCPuzzle puzzle = (AoCPuzzle) current.getConstructor().newInstance();
			if (null != args && args.length > 0) {
				switch (args[0]) {
					case "1":
					case "part1":
					case "one":
						puzzle.puzzle1();
						break;
					case "2":
					case "part2":
					case "two":
						puzzle.puzzle2();
						break;
				}
			} else
				puzzle.puzzle1();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
		        | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method for day 1 logic.
	 */
	public abstract void puzzle1();

	/**
	 * Method for day 2 logic.
	 */
	public abstract void puzzle2();

	/**
	 * Default method to get raw input as a list of string.
	 * 
	 * @return
	 */
	public List<String> loadInput() {
		List<String> input = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				input.add(in.nextLine());
			}
		}
		return input;
	}

	/**
	 * Consumes the input provided from {@link System#in}.
	 * 
	 * @param consumer
	 */
	public void nomz(Consumer<String> consumer) {
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				consumer.accept(in.nextLine());
			}
		}
	}

	public <T> T nomzInto(T o, BiConsumer<String, T> filler) {
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				filler.accept(in.nextLine(), o);
			}
		}
		return o;
	}

	/**
	 * Shortcut for {@link PrintUtils#format(String, Object...)}.
	 * 
	 * @param format base message
	 * @param args   arguments to provide for injection
	 */
	public void print(String format, Object... args) {
		PrintUtils.print(format, args);
	}

	/**
	 * Shortcut for {@link PrintUtils#format(Object)}.
	 * 
	 * @param arg argument to print
	 */
	public void print(Object arg) {
		PrintUtils.print(arg);
	}
}
