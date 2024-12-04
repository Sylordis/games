package com.github.sylordis.games.codingame.games.puzzles.hangman;

import java.util.function.Consumer;

public class LetterChanging implements Consumer<Character[][]> {

	// --- FIELDS

	private char changer;
	private int x;
	private int y;

	// --- CONSTRUCTORS

	public LetterChanging(char changer, int x, int y) {
		this.changer = changer;
		this.x = x;
		this.y = y;
	}

	// --- MODIFIERS

	@Override
	public void accept(Character[][] t) {
		t[y][x] = changer;
	}

}
