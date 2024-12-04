package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GhostLegs {

	class Bridge {
		private final int level, branchA, branchB;

		public Bridge(int level, int branchA, int branchB) {
			this.level = level;
			this.branchA = branchA;
			this.branchB = branchB;
		}

		public int level() {
			return level;
		}

		public int a() {
			return branchA;
		}

		public int b() {
			return branchB;
		}

		public int getOther(int i) {
			return i == branchA ? branchB : branchA;
		}

		@Override
		public String toString() {
			return "(" + level + "," + branchA + "," + branchB + ")";
		}

	}

	private static final String HOLE = "  ";
	private static final String BRIDGE = "--";
	private static final String BRANCH = "|";

	private Collection<Bridge> bridges;
	private List<String> entryPoints;
	private List<String> exitPoints;

	/**
	 * Constructor, initialises everything.
	 */
	public GhostLegs() {
		bridges = new ArrayList<>();
		entryPoints = new ArrayList<>();
		exitPoints = new ArrayList<>();
	}

	/**
	 * Loads the lines and converts it
	 *
	 * @param lines
	 */
	public void load(String[] lines) {
		// First line is entries
		entryPoints = Arrays.asList(lines[0].split(HOLE));
		// Process each line
		for (int i = 1; i < lines.length - 1; i++) {
			String[] holes = lines[i].split(Pattern.quote(BRANCH));
			// Parse line
			for (int j = 0; j < holes.length; j++) {
				if (holes[j].equals(BRIDGE))
					bridges.add(new Bridge(i - 1, j - 1, j));
			}
		}
		System.err.println(entryPoints);
		System.err.println(bridges);
		System.err.println(exitPoints);
		// Last line is exits
		exitPoints = Arrays.asList(lines[lines.length - 1].split(HOLE));
	}

	public List<String> getEntryPoints() {
		return entryPoints;
	}

	public String enter(String label) {
		int col = entryPoints.indexOf(label);
		int lvl = 0;
		boolean finished = false;
		while (!finished) {
			final int flvl = lvl;
			final int fcol = col;
			Optional<Bridge> bridge = bridges.stream()
					.filter(b -> b.level() >= flvl && (b.a() == fcol || b.b() == fcol)).findFirst();
			if (bridge.isPresent()) {
				System.err.println(bridge.get());
				lvl = bridge.get().level() + 1;
				col = bridge.get().getOther(col);
			} else
				finished = true;
		}
		return exitPoints.get(col);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		in.nextInt();
		int h = in.nextInt();
		if (in.hasNextLine())
			in.nextLine();
		String[] lines = new String[h];
		for (int i = 0; i < h; i++)
			lines[i] = in.nextLine();
		GhostLegs model = new GhostLegs();
		model.load(lines);
		for (String entry : model.getEntryPoints())
			System.out.println(entry + model.enter(entry));
		in.close();
	}
}
