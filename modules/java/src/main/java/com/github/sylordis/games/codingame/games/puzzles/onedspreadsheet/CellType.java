package com.github.sylordis.games.codingame.games.puzzles.onedspreadsheet;

import java.util.function.IntBinaryOperator;

public enum CellType {
	VALUE((a, b) -> a), ADD((a, b) -> a + b), SUB((a, b) -> a - b), MULT((a, b) -> a * b);

	private final IntBinaryOperator operation;

	private CellType(IntBinaryOperator op) {
		this.operation = op;
	}

	public IntBinaryOperator getOperator() {
		return operation;
	}
}