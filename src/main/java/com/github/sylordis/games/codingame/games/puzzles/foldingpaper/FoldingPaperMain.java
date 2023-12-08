package com.github.sylordis.games.codingame.games.puzzles.foldingpaper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FoldingPaperMain {

	private Map<FoldingSide, Integer> sides = new HashMap<>();

	public FoldingPaperMain() {
		Scanner in = new Scanner(System.in);
		String order = in.nextLine(); // Several letters without spaces
		// Side where you will observe at the end
		FoldingSide observer = FoldingSide.valueOf(in.nextLine());
		Arrays.asList(FoldingSide.values()).forEach(s -> sides.put(s, 1));
		order.chars().forEach(l -> fold(FoldingSide.valueOf(Character.toString((char) l))));
		System.out.println(sides.get(observer));
		in.close();
	}

	private void fold(FoldingSide fold) {
		FoldingSide opposite = FoldingSide.getOpposite(fold);
		int layersCurrentFoldingSide = sides.get(fold);
		for (FoldingSide side : sides.keySet()) {
			if (side == fold)
				sides.put(side, 1);
			else if (side == opposite)
				sides.put(side, sides.get(side) + layersCurrentFoldingSide);
			else
				sides.put(side, sides.get(side) * 2);
		}
	}

	public static void main(String args[]) {
		new FoldingPaperMain();
	}

}
