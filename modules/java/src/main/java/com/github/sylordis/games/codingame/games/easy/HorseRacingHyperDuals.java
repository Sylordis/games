package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.OptionalLong;
import java.util.Scanner;

public class HorseRacingHyperDuals {

	static class Horse {
		private final long velocity;
		private final long elegance;

		public Horse(long velocity, long elegance) {
			this.velocity = velocity;
			this.elegance = elegance;
		}

		public long distance(Horse h) {
			return Math.abs(velocity - h.velocity()) + Math.abs(elegance - h.elegance());
		}

		public long velocity() {
			return velocity;
		}

		public long elegance() {
			return elegance;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		Collection<Horse> horses = new ArrayList<>();
		long minDistance = Long.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			final Horse horse = new Horse(in.nextLong(), in.nextLong());
			OptionalLong distance = horses.stream().mapToLong(h -> horse.distance(h)).min();
			minDistance = distance.isPresent() && distance.getAsLong() < minDistance ? distance.getAsLong()
					: minDistance;
			horses.add(horse);
		}
		in.close();
		System.out.println(minDistance);
	}

}
