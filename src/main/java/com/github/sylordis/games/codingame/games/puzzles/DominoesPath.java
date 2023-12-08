package com.github.sylordis.games.codingame.games.puzzles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.github.sylordis.commons.Pair;
import com.github.sylordis.games.codingame.games.puzzles.dominoespath.Domino;
import com.github.sylordis.games.codingame.games.puzzles.dominoespath.DominoAddMatchConsumer;

public class DominoesPath {

	// --- FIELDS

	/**
	 * All dominoes added to this exercise.
	 */
	private Collection<Domino> dominoes;

	// --- CONSTRUCTORS

	/**
	 * Contructs a new problem solver for the DominoesPath puzzle.
	 */
	public DominoesPath() {
		this.dominoes = new ArrayList<>();
	}

	// --- MODIFIERS

	/**
	 * Adds a new domino to the pool and erases all previous computed solutions.
	 *
	 * @param a
	 *            First value of the domino
	 * @param b
	 *            Second value of the domino
	 */
	public void addDomino(Domino d) {
		this.dominoes.add(d);
	}

	/**
	 * Checks whether or not a path is possible. Will call {@link #computeSolutions()} if it has never been called.
	 *
	 * @return
	 */
	public boolean isPathPossible() {
		boolean possible = false;
		Collection<Pair<Domino, Collection<Domino>>> tsolutions = new ArrayList<>();
		if (!dominoes.isEmpty()) {
			Pair<Domino, Collection<Domino>> pair;
			// Generate all possibilities for start: take each domino as start point
			for (Domino domino : dominoes) {
				pair = new Pair<>(domino, new ArrayList<>(dominoes));
				pair.getSecond().remove(domino);
				tsolutions.add(pair);
			}
			tsolutions.forEach(s -> System.err.println("  " + s));
			// As long as the temporary solutions are not empty and
			while (!tsolutions.isEmpty() && tsolutions.parallelStream().anyMatch(p -> !p.second().isEmpty())) {
				System.err.println("Solutions could be found!");
				Collection<Pair<Domino, Collection<Domino>>> current = new ArrayList<>();
				// For each current solutions
				for (Pair<Domino, Collection<Domino>> element : tsolutions) {
					Collection<Pair<Domino, Collection<Domino>>> temp = new ArrayList<>();
					DominoAddMatchConsumer consumer = new DominoAddMatchConsumer(temp, element);
					// Check if last domino can be matched with one remaining domino
					final Domino last = element.first();
					element.second().parallelStream()
							.filter(d -> d.first().equals(last.second()) || d.second().equals(last.second()))
							.forEach(consumer);
					System.err.println(element.first() + " (" + temp.size() + "): ");
					temp.forEach(p -> System.err.println("  " + p));
					current.addAll(temp.parallelStream().filter(p -> p != null).collect(Collectors.toList()));
				}
				tsolutions = current;
			}
			possible = !tsolutions.isEmpty();
		}
		return possible;
	}

	// --- GETTERS & SETTERS

	/**
	 * @return the dominoes
	 */
	public Collection<Domino> getDominoes() {
		return Collections.unmodifiableCollection(dominoes);
	}

	// --- MAIN

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		DominoesPath dp = new DominoesPath();
		int n = in.nextInt();
		for (int i = 0; i < n; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			dp.addDomino(new Domino(a, b));
		}
		System.err.println("Dominoes collected (" + dp.getDominoes().size() + "): " + dp.getDominoes());
		System.out.println(dp.isPathPossible());
		in.close();
	}

}
