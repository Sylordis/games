package com.github.sylordis.games.codingame.games.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.github.sylordis.commons.graph.IdentifiableVertex;

public class DwarvesOnGiantMain {

	Map<Integer, IdentifiableVertex> people;

	public DwarvesOnGiantMain() {
		collectAndBuild();
		int maxDist = people.values().stream().mapToInt(v -> v.calculateDistanceFromFarthest(false)).max().getAsInt();
		System.out.println(maxDist + 1);
	}

	private IdentifiableVertex createOrGet(int id) {
		if (!people.containsKey(id))
			people.put(id, new IdentifiableVertex(id));
		return people.get(id);
	}

	private void collectAndBuild() {
		people = new HashMap<>();
		Scanner in = new Scanner(System.in);
		int n = in.nextInt(); // the number of relationships of influence
		for (int i = 0; i < n; i++) {
			int x = in.nextInt(); // a relationship of influence between two people (x influences y)
			int y = in.nextInt();
			IdentifiableVertex a = createOrGet(x);
			IdentifiableVertex b = createOrGet(y);
			a.addNeighbour(b);
		}
		in.close();
	}

	public static void main(String[] args) {
		new DwarvesOnGiantMain();
	}

}
