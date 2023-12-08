package com.github.sylordis.games.codingame.games.easy;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Darts {

	static class Player implements Comparable<Player> {
		private final String name;
		private int score;

		public Player(String name) {
			this.name = name;
			this.score = 0;
		}

		public void score(int amount) {
			score += amount;
		}

		public String getName() {
			return name;
		}

		public int score() {
			return score;
		}

		@Override
		public String toString() {
			return name + " " + score;
		}

		@Override
		public int compareTo(Player p) {
			return p.score() - score;
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

	static interface Shape {
		public boolean isInShape(Point p);
	}

	static class Square implements Shape {
		private final Point center;
		private final int width;

		public Square(Point center, int width) {
			this.center = center;
			this.width = width;
		}

		@Override
		public boolean isInShape(Point p) {
			return p.x() <= center.x() + width / 2 && p.x() >= center.x() - width / 2 && p.y() <= center.y() + width / 2
					&& p.y() >= center.y() - width / 2;
		}

	}

	static class Circle implements Shape {
		private final Point center;
		private final int radius;

		public Circle(Point center, int radius) {
			this.center = center;
			this.radius = radius;
		}

		@Override
		public boolean isInShape(Point p) {
			int distanceSq = p.distanceSquare(center);
			int rsq = radius * radius;
			return distanceSq <= rsq;
		}
	}

	static class SquaredRhombus implements Shape {
		private final Point center;
		private final int diagonalLength;

		public SquaredRhombus(Point center, int diagonalLength) {
			this.center = center;
			this.diagonalLength = diagonalLength;
		}

		@Override
		public boolean isInShape(Point p) {
			int dx = Math.abs(p.x() - center.x());
			int dy = Math.abs(p.y() - center.y());
			return (double) (dx + dy) * 2 / diagonalLength <= 1;
		}

	}

	static class DartGame {
		private int size;
		private Map<Shape, Integer> shapes;

		public DartGame(int size) {
			this.size = size;
			shapes = new LinkedHashMap<>();
			final Point center = new Point();
			shapes.put(new Square(center, size), 5);
			shapes.put(new Circle(center, size / 2), 10);
			shapes.put(new SquaredRhombus(center, size), 15);
		}

		public int getScore(Point p) {
			Shape last = null;
			for (Shape shape : shapes.keySet()) {
				if (shape.isInShape(p)) {
					last = shape;
					System.err.println("In " + shape.getClass().getSimpleName());
				} else
					break;
			}
			return last == null ? 0 : shapes.get(last);
		}

		public int getSize() {
			return size;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Map<String, Player> players = new LinkedHashMap<>();
		// Game
		int SIZE = in.nextInt();
		DartGame game = new DartGame(SIZE);
		// Players
		int nplayers = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		for (int i = 0; i < nplayers; i++) {
			Player player = new Player(in.nextLine());
			players.put(player.getName(), player);
		}
		// Throws
		int nthrows = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		for (int i = 0; i < nthrows; i++) {
			String name = in.next();
			int x = in.nextInt();
			int y = in.nextInt();
			System.err.println("Shot: " + name + " " + x + "," + y);
			players.get(name).score(game.getScore(new Point(x, y)));
		}
		in.close();
		players.values().stream().sorted().forEach(System.out::println);
	}
}
