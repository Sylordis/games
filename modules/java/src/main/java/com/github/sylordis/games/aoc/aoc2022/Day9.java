package com.github.sylordis.games.aoc.aoc2022;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.github.sylordis.commons.Direction;
import com.github.sylordis.commons.functional.comparators.Point2DintComparator;
import com.github.sylordis.commons.graph.d2.Point2Dint;
import com.github.sylordis.commons.graph.d2.Vector2Dint;

public class Day9 {

	private final double MAX_DISTANCE = Math.sqrt(2);
	private final int INDEX_HEAD = 0;
	
	public static void main(String[] args) {
		new Day9().day2();
	}

	private List<String> orders;

	private void run(int ntails) {
		load();
		// Head is 0
		List<Point2Dint> points = new ArrayList<>();
		points.add(new Point2Dint());
		for (int i = 0; i < ntails; i++)
			points.add(points.get(0).clone());
		Set<Point2Dint> visited = new TreeSet<>(new Point2DintComparator());
		visited.add(points.get(ntails).clone());
		for (String order : orders) {
			String[] parts = order.split(" ");
			Direction dir = translateToDirection(parts[0]);
			int n = Integer.parseInt(parts[1]);
//			print("Dir={} n={}", dir, n);
			// For each move
			for (int i = 0; i < n; i++) {
				Point2Dint head = points.get(INDEX_HEAD);
				head.add(dir.dx(), dir.dy());
				// For each point in the chain
				for (int j = 1; j <= ntails; j++) {
					Point2Dint ctail = points.get(j);
					Point2Dint previous = points.get(j - 1);
					double distance = ctail.getDistance(previous);
					// Tail is too far away from head, move the tail
					Vector2Dint move = null;
					if (distance > MAX_DISTANCE) {
						move = new Vector2Dint(normaliseMove(previous.x() - ctail.x()), normaliseMove(previous.y() - ctail.y()));
						ctail.add(move);
						if (j == ntails)
							visited.add(ctail.clone());
					}
//					print("H={} T({})={} dist={} move={}", head, j, ctail, distance, move);
				}
//				print("visited={}[{}]", visited, visited.size());
			}
		}
		print("{}", visited.size());
	}
	
	private void day1() {
		run(1);
	}

	private int normaliseMove(int n) {
		int newN = 0;
		if (n < 0) {
			newN = Math.max(-1, n);
		} else if (n > 0) {
			newN = Math.min(1, n);
		}
		return newN;
	}

	private void load() {
		orders = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				orders.add(in.nextLine());
			}
		}
	}

	private void day2() {
		run(9);
	}

	private Direction translateToDirection(String str) {
		Direction dir = null;
		if (str.equals("U"))
			dir = Direction.NORTH;
		else if (str.equals("L"))
			dir = Direction.WEST;
		else if (str.equals("R"))
			dir = Direction.EAST;
		else if (str.equals("D"))
			dir = Direction.SOUTH;
		return dir;
	}
}
