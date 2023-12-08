package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NatureOfQuadrilateral {

	static abstract class CoordsObject {
		private int x, y;

		public CoordsObject(int x, int y) {
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
	}

	static class Vector extends CoordsObject {

		public Vector(int x, int y) {
			super(x, y);
		}

		public Vector(Point p1, Point p2) {
			super(p1.x() - p2.x(), p1.y() - p2.y());
		}

		public int cross(Vector v) {
			return x() * v.x() + y() * v.y();
		}

		public double length() {
			return Math.sqrt(x() * x() + y() * y());
		}
	}

	static class Point extends CoordsObject {
		private String name;

		public Point(String name, int x, int y) {
			super(x, y);
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	static enum QuadriType {
		QUADRILATERAL(q -> true), // The rest
		PARALLELOGRAM(
				q -> {
					// TODO
					return false;
				}), // All opposite sides parallel
		RHOMBUS(q -> {
			return q.getSides().stream().mapToDouble(Vector::length).distinct().count() <= 1;
		}), // Four sides equal
		RECTANGLE(q -> {
			List<Vector> sides = q.getSides();
			List<Integer> crosses = new ArrayList<>();
			for (int i = 0; i < 4; i++)
				crosses.add(sides.get(i).cross(sides.get((i + 1) % 4)));
			return crosses.stream().allMatch(c -> c == 0);
		}), // Four angles are right
		SQUARE(q -> RHOMBUS.checkIf(q) && RECTANGLE.checkIf(q)); // Rhombus + rectangle
		private final Predicate<Quadrilateral> criterias;

		private QuadriType(Predicate<Quadrilateral> criterias) {
			this.criterias = criterias;
		}

		public boolean checkIf(Quadrilateral quad) {
			return criterias.test(quad);
		}

		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
	}

	static class Quadrilateral {

		private final List<Point> points;

		public Quadrilateral(Point p1, Point p2, Point p3, Point p4) {
			this.points = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
		}

		public QuadriType verifyType() {
			Deque<QuadriType> qualifications = new LinkedList<>();
			for (QuadriType type : QuadriType.values())
				if (type.checkIf(this))
					qualifications.add(type);
			return qualifications.peekLast();
		}

		public List<Point> getPoints() {
			return points;
		}

		public List<Vector> getSides() {
			List<Vector> sides = new ArrayList<>();
			for (int i = 0; i < points.size(); i++)
				sides.add(new Vector(points.get(i), points.get((i + 1) % 4)));
			return sides;
		}

		@Override
		public String toString() {
			return points.stream().map(Point::getName).collect(Collectors.joining());
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		Collection<Quadrilateral> figures = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			Point p1 = new Point(in.next(), in.nextInt(), in.nextInt());
			Point p2 = new Point(in.next(), in.nextInt(), in.nextInt());
			Point p3 = new Point(in.next(), in.nextInt(), in.nextInt());
			Point p4 = new Point(in.next(), in.nextInt(), in.nextInt());
			figures.add(new Quadrilateral(p1, p2, p3, p4));
		}
		in.close();
		figures.forEach(q -> System.out.println(q + " is a " + q.verifyType()));
	}
}
