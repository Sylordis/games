package com.github.sylordis.games.aoc.aoc2022;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import com.github.sylordis.commons.Direction;
import com.github.sylordis.commons.boards.NumberBoard;

public class Day8 {

	public static void main(String[] args) {
		new Day8().day2();
	}

	private NumberBoard forest;

	private void loadForest() {
		List<String> forestRows = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				forestRows.add(in.nextLine());
			}
		}
		forest = new NumberBoard(forestRows.get(0).length(), forestRows.size());
		for (int i = 0; i < forestRows.size(); i++)
			forest.setRowFrom(i, forestRows.get(i));
//		print("{}", forest);
	}

	private void day1() {
		loadForest();
		Boolean[][] visibilityMap = new Boolean[forest.getWidth()][forest.getHeight()];
		for (int y = 0; y < forest.getHeight(); y++)
			Arrays.fill(visibilityMap[y], false);
//		print("{}", MatrixUtils.printMatrixes(visibilityMap));
		List<Direction> dirs = List.of(Direction.CARDINAL_4);
		for (int y = 0; y < forest.getHeight(); y++) {
			for (int x = 0; x < forest.getWidth(); x++) {
//				print("({},{})={}", x, y, forest.get(x, y));
				if (x == 0 || y == 0 || x == forest.getWidth() - 1 || y == forest.getHeight() - 1) {
					visibilityMap[y][x] = true;
				} else {
					boolean visible = false;
					for (Direction dir : dirs) {
						boolean isVisible = visibleFrom(x, y, dir);
						visible = visible || isVisible;
					}
					visibilityMap[y][x] = visible;
//					print("{}", MatrixUtils.printMatrixes(visibilityMap));
				}
			}
		}
//		print("{}", MatrixUtils.printMatrixes(visibilityMap));
		int nvisible = Arrays.stream(visibilityMap).flatMap(Arrays::stream).mapToInt(b -> b ? 1 : 0).sum();
		print("{}", nvisible);
	}

	private boolean visibleFrom(int x, int y, Direction direction) {
		boolean flag = false;
		List<Integer> slice;
		switch (direction) {
			case NORTH:
				slice = forest.getColSlice(x, 0, y);
				break;
			case SOUTH:
				slice = forest.getColSlice(x, y + 1, forest.getHeight());
				break;
			case WEST:
				slice = forest.getRowSlice(y, 0, x);
				break;
			case EAST:
				slice = forest.getRowSlice(y, x + 1, forest.getWidth());
				break;
			default:
				slice = new ArrayList<>();
				break;
		}
		flag = slice.stream().allMatch(v -> v < forest.get(x, y));
//		print("dir={} slice={}, flag={}", direction, slice, flag);
		return flag;
	}

	private void day2() {
		loadForest();
		// Map where each tree is weighted according to the number of trees it can see
		NumberBoard visibility = new NumberBoard(forest.getWidth(), forest.getHeight());
		Direction[] card4 = Direction.CARDINAL_4;
		for (int y = 0; y < forest.getHeight(); y++) {
			for (int x = 0; x < forest.getWidth(); x++) {
//				print("({}.{})={}", x, y, forest.get(x, y));
				int scenicScore = 1;
				for (Direction dir : card4) {
					scenicScore *= getNumberOfSeenTrees(x, y, dir);
				}
				visibility.set(x, y, scenicScore);
//				print("scenic ({}.{})={}", x, y, scenicScore);
			}
		}
//		print("{}", forest);
//		print("{}", visibility);
		int maxTrees = Arrays.stream(visibility.getTiles()).flatMap(Arrays::stream).mapToInt(n -> (Integer) n).max()
		        .getAsInt();
		print("{}", maxTrees);
	}

	private Integer getNumberOfSeenTrees(int ox, int oy, Direction dir) {
		int count = 0;
		int startValue = 0, maxValue = 0, delta = Integer.MAX_VALUE;
		Function<Integer,Integer> supplier = i -> 0;
		switch (dir) {
			case NORTH:
				supplier = i -> forest.get(ox, i);
				delta = dir.dy();
				startValue = oy;
				maxValue = 0;
				break;
			case SOUTH:
				supplier = i -> forest.get(ox, i);
				delta = dir.dy();
				startValue = oy;
				maxValue = forest.getHeight();
				break;
			case WEST:
				supplier = i -> forest.get(i, oy);
				delta = dir.dx();
				startValue = ox;
				maxValue = 0;
				break;
			case EAST:
				supplier = i -> forest.get(i, oy);
				delta = dir.dx();
				startValue = ox;
				maxValue = forest.getWidth();
				break;
		}
		for (int i = startValue + delta; delta > 0 ? i < maxValue : i >= maxValue; i += delta) {
			count++;
//			print("dir={} i={}, curr={} count={}", dir, i, supplier.apply(i), count);
			if (supplier.apply(i) >= forest.get(ox,oy))
				break;
		}
		return count;
	}

}
