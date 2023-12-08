package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BrickInTheWall {

	private static final int G = 10;
	private int length;

	public BrickInTheWall(int length) {
		this.length = length;
	}

	private double build(Collection<Integer> bricks) {
		double joules = 0;
		int n = 0;
		List<Integer> lBricks = new ArrayList<>(bricks);
		Collections.sort(lBricks, (a, b) -> Integer.compare(b, a));
		for (Integer brick : lBricks)
			joules += toJoules(brick, n++ / length + 1);
		return joules;
	}

	public double toJoules(int mass, int level) {
		return (level - 1) * mass * 6.5 * G / 100;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int length = in.nextInt();
		Collection<Integer> bricks = new ArrayList<>();
		BrickInTheWall wall = new BrickInTheWall(length);
		int nBricks = in.nextInt();
		for (int i = 0; i < nBricks; i++)
			bricks.add(in.nextInt());
		in.close();
		System.out.printf("%.3f", wall.build(bricks));
	}

}
