package com.github.sylordis.games.aoc.aoc2023;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.sylordis.commons.utils.ArrayUtils;
import com.github.sylordis.games.aoc.AoCPuzzle;

record Game(int id, Map<String, Integer> cubes) {
	public long getPower() {
		long power = 1L;
		if (cubes.isEmpty())
			power = 0L;
		for (Integer ncube : cubes.values())
			power *= ncube;
		return power;
	}
}

public class Day2 extends AoCPuzzle {

	public Game gameFrom(String s) {
		Map<String, Integer> cubes = new HashMap<>();
		String[] parts1 = s.split(":");
		String idsub = parts1[0].substring(5);
		int id = Integer.parseInt(idsub);
		String[] plays = parts1[1].split(";");
		for (String play : plays) {
			String[] cubesInPlay = play.split(",");
			ArrayUtils.trimEach(cubesInPlay);
			for (String cubePlay : cubesInPlay) {
				String[] cubeParts = cubePlay.split(" ");
				int ncubes = Integer.parseInt(cubeParts[0]);
				cubes.compute(cubeParts[1], (k, v) -> v == null ? ncubes : Math.max(v, ncubes));
			}
		}
		return new Game(id, cubes);
	}

	@Override
	public void puzzle1() {
		final Map<String, Integer> maxima = Map.of("red", 12, "green", 13, "blue", 14);
		Collection<Game> legalGames = new ArrayList<>();
		Collection<Game> games = new ArrayList<>();
		nomz(s -> games.add(gameFrom(s)));
//		print("{}", games);
		long sum = 0L;
		for (Game game : games) {
			boolean valid = true;
			for (Entry<String, Integer> play : game.cubes().entrySet()) {
				if (play.getValue() > maxima.get(play.getKey()))
					valid = false;
			}
			if (valid) {
				legalGames.add(game);
				sum += game.id();
			}
		}
//		print("{}: {}", sum, legalGames);
		print(sum);
	}

	@Override
	public void puzzle2() {
		Collection<Game> games = new ArrayList<>();
		nomz(s -> games.add(gameFrom(s)));
		long sum = games.stream().mapToLong(g -> g.getPower()).sum();
		print(sum);
	}

	public static void main(String[] args) {
		new Day2().run(args);
	}

}
