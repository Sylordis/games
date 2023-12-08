package com.github.sylordis.games.aoc.aoc2020;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.github.sylordis.commons.BinarySpacePartitioner;
import com.github.sylordis.commons.Pair;
import com.github.sylordis.commons.BinarySpacePartitioner.SpaceSection;

public class Puzzle5 {

	private final int ROWS = 128;
	private final int COLS = 8;

	public int getSeatID(int row, int col) {
		return row * 8 + col;
	}

	public Pair<Integer, Integer> getSeat(String pass) {
		// Get row
		BinarySpacePartitioner rowPartitioner = new BinarySpacePartitioner(ROWS);
		for (int rs = 0; rs < 7; rs++) {
			//			debug("rs={} char={}", rs, pass.charAt(rs));
			rowPartitioner.partition(pass.charAt(rs) == 'F' ? SpaceSection.FRONT : SpaceSection.BACK);
			//			debug(rowPartitioner.getRange());
		}
		// Get column
		BinarySpacePartitioner colPartitioner = new BinarySpacePartitioner(COLS);
		for (int cs = 7; cs < 10; cs++) {
			//			debug("cs={} char={}", cs, pass.charAt(cs));
			colPartitioner.partition(pass.charAt(cs) == 'L' ? SpaceSection.FRONT : SpaceSection.BACK);
			//			debug(colPartitioner.getRange());
		}
		final Pair<Integer, Integer> result = new Pair<Integer, Integer>(rowPartitioner.getRange().getFirst(), colPartitioner.getRange().getFirst());
		//		debug("Seat={}", result);
		return result;
	}

	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			List<String> boardingPasses = new ArrayList<>();
			while (in.hasNext())
				boardingPasses.add(in.nextLine());
			Puzzle5 tool = new Puzzle5();
			int[] list = boardingPasses.stream().map(p -> tool.getSeat(p))
					.mapToInt(p -> tool.getSeatID(p.getFirst(), p.getSecond())).sorted().toArray();
			// Part 1
			//			OptionalInt highest = Arrays.stream(list).max();
			//			DebugUtils.print("{}", highest.getAsInt());
			print("{}", tool.findGap(list));
		}
	}

	private int findGap(int[] list) {
		List<Integer> indexes = new ArrayList<>();
		int sum = 0;
		int nindexes = 0;
		for (int i = 1; i < list.length - 1; i++) {
			if (list[i - 1] != list[i] - 1 || list[i + 1] != list[i] + 1) {
				sum += list[i];
				nindexes++;
			}
		}
		return sum / nindexes;
	}

}
