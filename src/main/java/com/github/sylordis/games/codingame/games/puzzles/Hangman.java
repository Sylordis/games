package com.github.sylordis.games.codingame.games.puzzles;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.function.Consumer;

import com.github.sylordis.commons.GameException;
import com.github.sylordis.commons.StatefulObject;
import com.github.sylordis.games.codingame.games.puzzles.hangman.LetterChanging;

public class Hangman implements StatefulObject<Integer, String> {

	// --- Constants

	/**
	 * Number of states.
	 */
	private static final int NUMBER_OF_STATES = 6;
	/**
	 * Pictogram base format for each line of the hangman.
	 */
	private static final String PICTO_FORMAT = "%-6s";
	/**
	 * Base of the hangman.
	 */
	private static final char[][] BASE = new char[][] { String.format(PICTO_FORMAT, "+--+").toCharArray(),
			String.format(PICTO_FORMAT, "|").toCharArray(), String.format(PICTO_FORMAT, "|").toCharArray(),
			String.format(PICTO_FORMAT, "|\\").toCharArray() };
	/**
	 * Consumers to modify the base drawing.
	 */
	private static final List<Consumer<Character[][]>> BODY_PARTS = Arrays.asList(new LetterChanging('o', 3, 1),
			new LetterChanging('|', 3, 2), new LetterChanging('/', 2, 2), new LetterChanging('\\', 4, 2),
			new LetterChanging('/', 2, 3), new LetterChanging('\\', 4, 3));

	// --- FIELDS

	/**
	 * Current state of the hangman.
	 */
	private int state;
	/**
	 * Word to be guessed.
	 */
	private String word;
	/**
	 * All characters remaining to be guessed.
	 */
	private Collection<Character> charsToGuess;

	// --- CONSTRUCTORS

	/**
	 * Builds a new Hangman.
	 *
	 * @param word
	 */
	public Hangman(String word) {
		this.state = 0;
		this.word = word;
		charsToGuess = new HashSet<>();
		for (char c : word.toLowerCase().toCharArray()) {
			if (c != ' ')
				charsToGuess.add(c);
		}
	}

	// --- MODIFIERS

	@Override
	public boolean hasNext() {
		return state < NUMBER_OF_STATES;
	}

	@Override
	public Integer next() {
		if (!hasNext())
			throw new NoSuchElementException();
		return ++state;
	}

	@Override
	public Integer previous() {
		if (state == 0)
			throw new NoSuchElementException();
		return --state;
	}

	/**
	 * Makes tries on several letters.
	 *
	 * @param tries
	 *            List of characters to try with
	 * @param delim
	 *            Delimiter of the characters in tries string
	 * @throws GameException
	 *             If the game ends, whether win or loss
	 */
	public void tries(String tries, String delim) throws GameException {
		StringTokenizer tokenizer = new StringTokenizer(tries, delim);
		while (tokenizer.hasMoreTokens()) {
			char c = tokenizer.nextToken().charAt(0);
			tryLetter(c);
		}
		if (state == NUMBER_OF_STATES)
			throw new GameException("Loss");
	}

	/**
	 * Tries one letter.
	 *
	 * @param letter
	 *            Letter to try the game with
	 * @throws GameException
	 *             If the game ends, whether win or loss
	 */
	public void tryLetter(char letter) throws GameException {
		System.err
				.println("try[" + state + "]: " + letter + " " + (charsToGuess.contains(letter) ? "correct!" : "meh"));
		if (charsToGuess.contains(letter)) {
			charsToGuess.remove(letter);
			// Win
			if (charsToGuess.isEmpty())
				throw new GameException("Win");
		} else {
			try {
				next();
			} catch (NoSuchElementException e) {
				// Game over
				throw new GameException("Game over");
			}
		}
	}

	// --- OBJECT

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getCurrentStateRepresentation());
		for (char c : word.toCharArray()) {
			if (c != ' ' && charsToGuess.contains(Character.toLowerCase(c)))
				builder.append('_');
			else
				builder.append(c);
		}
		return builder.toString();
	}

	// --- GETTERS & SETTERS

	@Override
	public String getStateRepresentation(Integer state) {
		Character[][] hangman = new Character[BASE.length][BASE[0].length];
		for (int i = 0; i < BASE.length; i++) {
			for (int j = 0; j < BASE[i].length; j++) {
				hangman[i][j] = BASE[i][j];
			}
		}
		for (int s = 0; s < getState(); s++)
			BODY_PARTS.get(s).accept(hangman);
		StringBuilder builder = new StringBuilder();
		for (Character[] cs : hangman) {
			StringBuilder level = new StringBuilder();
			for (Character c : cs)
				level.append(c);
			builder.append(level.toString().trim());
			builder.append("\n");
		}
		return builder.toString();
	}

	@Override
	public Integer getState() {
		return state;
	}

	@Override
	public void setState(Integer state) {
		if (state < 0 || state > NUMBER_OF_STATES)
			throw new NoSuchElementException();
		this.state = state;
	}

	// --- MAIN

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Hangman hangman = new Hangman(in.nextLine());
		try {
			hangman.tries(in.nextLine(), " ");
		} catch (GameException e) {
			System.err.println(e.getMessage());
			System.out.println(hangman);
		}
		in.close();
	}

}
