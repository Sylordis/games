package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class HungerGames {

	private static final String KILLED_STR = "killed";

	static class Tribute {
		private final String name;
		private Tribute killer;
		private Collection<Tribute> victims;

		public Tribute(String name) {
			this.name = name;
			this.killer = null;
			this.victims = new ArrayList<>();
		}

		public void setKiller(Tribute killer) {
			this.killer = killer;
		}

		public void addVictim(Tribute victim) {
			this.victims.add(victim);
		}

		@Override
		public String toString() {
			return name;
		}

		public String getName() {
			return name;
		}

		public Tribute getKiller() {
			return killer;
		}

		public Collection<Tribute> getVictims() {
			return victims;
		}
	}

	private Map<String, Tribute> tributes;

	public HungerGames() {
		this.tributes = new TreeMap<>();
	}

	public void addTribute(Tribute tribute) {
		tributes.put(tribute.getName(), tribute);
	}

	public void event(String event) {
		final int killedIdx = event.indexOf(KILLED_STR);
		String killerName = event.substring(0, killedIdx - 1);
		String[] victims = event.substring(killedIdx + KILLED_STR.length() + 1).split(", ");
		for (String victim : victims) {
			final Tribute killed = tributes.get(victim);
			final Tribute killer = tributes.get(killerName);
			killed.setKiller(killer);
			killer.addVictim(killed);
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Tribute tribute : tributes.values()) {
			buffer.append("Name: ").append(tribute.getName()).append("\n");
			buffer.append("Killed: ").append(tribute.getVictims().isEmpty() ? "None"
					: tribute.getVictims().stream().map(Tribute::toString).sorted().collect(Collectors.joining(", ")))
					.append("\n");
			buffer.append("Killer: ").append(tribute.getKiller() == null ? "Winner" : tribute.getKiller())
					.append("\n\n");
		}
		return buffer.toString().substring(0, buffer.length() - 2);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int ntributes = in.nextInt();
		HungerGames games = new HungerGames();
		if (in.hasNextLine())
			in.nextLine();
		for (int i = 0; i < ntributes; i++)
			games.addTribute(new Tribute(in.nextLine()));
		int rounds = in.nextInt();
		if (in.hasNextLine())
			in.nextLine();
		for (int i = 0; i < rounds; i++)
			games.event(in.nextLine());
		in.close();
		System.out.println(games);
	}

}
