package com.github.sylordis.games.aoc.aoc2021;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

record Point(int x, int y) {

}

record Line(Point a, Point b) {

}

public class Day5 {

	public static void main(String[] args) {
		new Day5().run();
	}

	private void run() {
		Collection<Line> ventLines = swallow();
		Map<Point, Integer> map = new HashMap<>();
		for (Line line : ventLines) {
			int iStart = -1, iEnd = -1;
			if (line.a().x() == line.b().x()) {
				// Vertical line
				iStart = Math.min(line.a().y(), line.b().y());
				iEnd = Math.max(line.a().y(), line.b().y());
				// print("{}; on X={}: {}->{}", line, line.a().x(), iStart, iEnd);
				for (int y = iStart; y <= iEnd; y++)
					map.compute(new Point(line.a().x(), y), (k, v) -> v == null ? 1 : v + 1);
			} else if (line.a().y() == line.b().y()) {
				// Horizontal line
				iStart = Math.min(line.a().x(), line.b().x());
				iEnd = Math.max(line.a().x(), line.b().x());
				// print("{}; on Y={}: {}->{}", line, line.a().y(), iStart, iEnd);
				for (int x = iStart; x <= iEnd; x++)
					map.compute(new Point(x, line.a().y()), (k, v) -> v == null ? 1 : v + 1);
			} else {
				// Diagonal
				int xMod, yMod;
				xMod = (int) Math.signum(line.b().x() - line.a().x());
				yMod = (int) Math.signum(line.b().y() - line.a().y());
				//				print("Line={}: xMod={}, yMod={}", line, xMod, yMod);
				for (int x = line.a().x(), y = line.a().y(); x * xMod <= line.b().x() * xMod; x += xMod, y += yMod) {
					map.compute(new Point(x, y), (k, v) -> v == null ? 1 : v + 1);
				}

			}
		}
		long intersect = map.entrySet().stream().filter(v -> v.getValue() >= 2).count();
		print("{}", intersect);
	}

	private Collection<Line> swallow() {
		Collection<Line> ventLines = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			int x1, x2, y1, y2;
			Point p1, p2;
			while (in.hasNext()) {
				// Format x1,y1 -> x2,y2
				String line = in.nextLine();
				String[] couplesStr = line.split(" -> ");
				String[] p1Str = couplesStr[0].split(",");
				String[] p2Str = couplesStr[1].split(",");
				x1 = Integer.parseInt(p1Str[0]);
				x2 = Integer.parseInt(p2Str[0]);
				y1 = Integer.parseInt(p1Str[1]);
				y2 = Integer.parseInt(p2Str[1]);
				p1 = new Point(x1, y1);
				p2 = new Point(x2, y2);
				Line couple = new Line(p1, p2);
				ventLines.add(couple);
			}
		}
		return ventLines;
	}

}
