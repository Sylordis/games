package com.github.sylordis.games.codingame.games.easy;

import static com.github.sylordis.commons.utils.PrintUtils.debug;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class DeadManShot {

	static class Game {
		private List<Point> points;

		public Game() {
			this.points = new ArrayList<>();
		}

		public void addPoint(Point p) {
			points.add(p);
		}

		public boolean hitsInTarget(Point p) {
			int nhits = 0;
			final int sy = p.y();
			final int sx = points.stream().mapToInt(Point::x).min().getAsInt() - 1;
			debug("shot at ({}), start=({},{})", p, sx, sy);
			Collection<Predicate<Point>> excludes = new ArrayList<>();
			for (int i = 0; i < points.size(); i++) {
				Collection<Predicate<Point>> excludesToAdd = new ArrayList<>();
				Point p1 = points.get(i);
				Point p2 = points.get((i + 1) % points.size());
				double xForY = Double.MAX_VALUE;
				final int minY = Math.min(p1.y(), p2.y());
				final int maxY = Math.max(p1.y(), p2.y());
				final int minX = Math.min(p1.x(), p2.x());
				final int maxX = Math.max(p1.x(), p2.x());
				if (p1.x() == p2.x()) { // Vertical
					debug("  Vertical");
					xForY = p1.x();
					excludesToAdd.add(s -> s.x() != p1.x());
				} else if (p1.y() == p2.y()) { // Horizontal
					debug("  Horizontal");
					// Only consider if it's on the line
					if (p.y() == p1.y()) {
						xForY = Math.min(p1.x(), p2.x());
						excludesToAdd.add(s -> s.y() != p1.y() && s.x() <= maxX && s.x() >= minX);
					} else
						continue;
				} else {
					Line edge = new Line(p1, p2);
					xForY = edge.getXForY(sy);
				}
				if (xForY >= sx && xForY <= p.x() && p.y() >= minY && p.y() <= maxY
						&& (excludes.isEmpty() || excludes.stream().allMatch(pr -> pr.test(p)))) {
					nhits++;
				}
				debug("  ({}),({}), x={}, y=[{},{}] / crossed={} / |e|={}", p1, p2, xForY, minY, maxY, nhits,
						excludes.size());
				if (!excludesToAdd.isEmpty())
					excludes.addAll(excludesToAdd);
			}
			return nhits % 2 == 1;
		}

	}

	static class Line {

		private double slope;
		private double y0;

		public Line(Point p1, Point p2) {
			this.slope = (p2.y() - p1.y()) / (p2.x() - p1.x());
			this.y0 = ((p1.y() + p2.y()) - this.slope * (p1.x() + p2.x())) / 2;
		}

		public Line(double slope, Point point) {
			this.slope = slope;
			this.y0 = point.y() - (slope * point.x());
		}

		public double slope() {
			return slope;
		}

		public double y0() {
			return y0;
		}

		public double getXForY(double y) {
			if (slope == 0)
				return 0;
			return (y - y0) / slope;
		}

		public double getYForX(double x) {
			return slope * x + y0;
		}
	}

	static class Point {
		private int x, y;

		public Point() {
			this(0, 0);
		}

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int x() {
			return x;
		}

		public void x(int x) {
			this.x = x;
		}

		public int y() {
			return y;
		}

		public void y(int y) {
			this.y = y;
		}

		public int distanceSquare(Point p) {
			return (int) (Math.pow(x - p.x(), 2) + Math.pow(y - p.y(), 2));
		}

		@Override
		public String toString() {
			return x + "," + y;
		}

	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Game game = new Game();
		int ncorners = in.nextInt();
		for (int i = 0; i < ncorners; i++)
			game.addPoint(new Point(in.nextInt(), in.nextInt()));
		int nshots = in.nextInt();
		for (int i = 0; i < nshots; i++)
			System.out.println(game.hitsInTarget(new Point(in.nextInt(), in.nextInt())) ? "hit" : "miss");
		in.close();
	}

}
