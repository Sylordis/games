package com.github.sylordis.games.codingame.games.puzzles.appletree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AppleTreeMain {

	// --- FIELDS

	private Collection<Apple> apples = new ArrayList<>();

	// --- CONSTRUCTORS

	// --- MODIFIERS

	public void addApple(Apple apple) {
		apples.add(apple);
	}

	public void fall(Apple apple) {
		apples.remove(apple);
		Collection<Apple> fallingApples = new HashSet<>();
		fallingApples.add(apple);
		while (!fallingApples.isEmpty()) {
			Collection<Apple> willStartFalling = new HashSet<>();
			fallingApples.forEach(f -> willStartFalling
					.addAll(apples.parallelStream().filter(a -> a.collideWith(f)).collect(Collectors.toList())));
			apples.removeAll(willStartFalling);
			fallingApples = willStartFalling;
		}
	}

	// --- STATICS

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		AppleTreeMain tree = new AppleTreeMain();
		int n = in.nextInt();
		int f = in.nextInt();
		Apple falling = null;
		in.nextLine();
		for (int i = 0; i < n; i++) {
			Apple apple = new Apple(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
			if (f == i)
				falling = apple;
			tree.addApple(apple);
		}
		tree.fall(falling);
		System.out.println(tree.getApples());
		in.close();
	}

	// --- GETTERS & SETTERS

	public int getApples() {
		return apples.size();
	}
}
