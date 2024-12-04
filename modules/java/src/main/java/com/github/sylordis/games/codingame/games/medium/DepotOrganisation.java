package com.github.sylordis.games.codingame.games.medium;

import static com.github.sylordis.commons.utils.PrintUtils.debug;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.github.sylordis.games.codingame.games.medium.specific.Depot;

public class DepotOrganisation {

	private static final int TEXTURES_PER_DEPOT = 6;
	public static final int DEPOTS_NUMBER = 7;

	public void solve(Collection<Depot> depots) {
		Map<Collection<Depot>, List<Depot>> possibilities = new HashMap<>();
		Collection<Depot> biggestMatching = new ArrayList<>();
		int max = 0;
		for (Depot depot : depots) {
			final int matches = (int) depots.stream().filter(d -> !d.equals(depot) && depot.haveMatchingTexture(d))
					.count();
			if (matches > max) {
				max = matches;
				biggestMatching.clear();
			}
			if (matches == max) {
				max = matches;
				biggestMatching.add(depot);
			}
		}
		debug("max={}, list={}", max, biggestMatching);
		// TODO highest match in the middle
	}

	public static void main(String[] args) {
		DepotOrganisation org = new DepotOrganisation();
		Collection<Depot> depots = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			for (int di = 0; di < DEPOTS_NUMBER; di++) {
				String[] textures = new String[TEXTURES_PER_DEPOT];
				for (int ti = 0; ti < TEXTURES_PER_DEPOT; ti++) {
					textures[ti] = in.next();
				}
				final Depot depot = new Depot(di, textures);
				debug(depot.draw());
				depots.add(depot);
			}
		}
		org.solve(depots);
	}

}
