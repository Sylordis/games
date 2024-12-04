package com.github.sylordis.games.codingame.games.puzzles.dominoespath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import com.github.sylordis.commons.Pair;

public class DominoAddMatchConsumer implements Consumer<Domino> {

	// --- FIELDS

	private final Collection<Pair<Domino, Collection<Domino>>> list;
	private final Pair<Domino, Collection<Domino>> current;

	// --- CONSTRUCTORS

	public DominoAddMatchConsumer(Collection<Pair<Domino, Collection<Domino>>> list,
			Pair<Domino, Collection<Domino>> current) {
		this.list = list;
		this.current = current;
	}

	// --- MODIFIERS

	@Override
	public void accept(Domino d) {
		Domino nd = d;
		if (!d.first().equals(current.first().second()) && d.second().equals(current.first().second()))
			nd = d.exchange();
		Pair<Domino, Collection<Domino>> pair = new Pair<>(nd, new ArrayList<>(current.second()));
		pair.second().remove(d);
		list.add(pair);
	}

	// --- GETTERS & SETTERS
}
