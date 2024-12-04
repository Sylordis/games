package com.github.sylordis.games.codingame.games.puzzles.onedspreadsheet;

import com.github.sylordis.commons.Pair;

public class Cell extends Pair<String, String> {

	private CellType type;

	public Cell(CellType type, String a, String b) {
		super(a, b);
		this.type = type;
	}

	public CellType getType() {
		return type;
	}

}
