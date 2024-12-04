package com.github.sylordis.games.codingame.games.puzzles.foldingpaper;

public enum FoldingSide {

	R, L, U, D;

	public static FoldingSide getOpposite(FoldingSide s) {
		FoldingSide r = null;
		switch (s) {
			case L:
				r = FoldingSide.R;
				break;
			case R:
				r = FoldingSide.L;
				break;
			case D:
				r = FoldingSide.U;
				break;
			case U:
				r = FoldingSide.D;
				break;
		}
		return r;
	}

}
