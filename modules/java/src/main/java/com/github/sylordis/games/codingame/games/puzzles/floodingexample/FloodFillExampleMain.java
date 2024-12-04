package com.github.sylordis.games.codingame.games.puzzles.floodingexample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import com.github.sylordis.commons.Direction;
import com.github.sylordis.commons.Pair;
import com.github.sylordis.commons.boards.Board;

public class FloodFillExampleMain extends Board {

	// --- CONSTANTS

	public static final char REACHABLE = '.';
	public static final char UNREACHABLE = '#';
	public static final char MULTIPLE = '+';

	// --- FIELDS

	// --- CONSTRUCTORS

	public FloodFillExampleMain(int width, int height) {
		super(width, height);
	}

	// --- MODIFIERS

	/**
	 * Applies the flooding on the map.
	 */
	public void flood() {
		Collection<FloodingVertex> olds = getTowers();
		System.err.println(olds);
		while (hasNeighboursToFill(olds)) {
			final Collection<FloodingVertex> current = new ArrayList<>();
			// Parse all olds nodes
			for (FloodingVertex vertex : olds) {
				System.err.print(vertex + ": ");
				// Get new neighbours which can be filled
				Collection<FloodingVertex> neighbours = getNeighboursToFill(vertex);
				System.err.println(neighbours);
				for (FloodingVertex neighbour : neighbours) {
					Optional<FloodingVertex> same = current.stream()
							.filter(v -> v.x() == neighbour.x() && v.y() == neighbour.y()).findFirst();
					neighbour.setId(
							!same.isPresent() || same.get().is(vertex) ? vertex.getId() : new Pair<>(MULTIPLE, 0));
					current.add(neighbour);
				}
			}
			current.forEach(v -> set(v.x(), v.y(), (int) v.getId().first().charValue()));
			olds = current;
			System.err.println(this);
		}
	}

	@Override
	public String toString() {
		StringBuilder rame = new StringBuilder();
		for (int h = 0; h < getHeight(); h++) {
			for (int w = 0; w < getWidth(); w++)
				rame.append(getAsChar(w, h));
			rame.append("\n");
		}
		return rame.toString();
	}

	// --- STATICS

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int width = in.nextInt();
		int height = in.nextInt();
		in.nextLine();
		FloodFillExampleMain solution = new FloodFillExampleMain(width, height);
		for (int i = 0; i < height; i++) {
			solution.setRowFrom(i, in.nextLine());
		}
		System.err.println(solution);
		solution.flood();
		String board = solution.toString();
		System.out.print(board.substring(0, board.length() - 1));
		in.close();
	}

	// --- GETTERS & SETTERS

	/**
	 * Gets all neighbours to fill of one vertex.
	 *
	 * @param v
	 * @return
	 */
	public Collection<FloodingVertex> getNeighboursToFill(FloodingVertex v) {
		final Collection<FloodingVertex> neighbours = new ArrayList<>();
		for (Direction d : Direction.CARDINAL_4) {
			int nx = v.x() + d.dx();
			int ny = v.y() + d.dy();
			if (isInLimits(nx, ny) && getAsChar(nx, ny) == REACHABLE)
				neighbours.add(new FloodingVertex(nx, ny, getAsChar(nx, ny), 0));
		}
		return neighbours;
	}

	/**
	 * Checks that all given vertices has at least one neighbour to fill.
	 *
	 * @param current
	 * @return
	 */
	private boolean hasNeighboursToFill(Collection<FloodingVertex> current) {
		return current.parallelStream().flatMap(v -> getNeighboursToFill(v).stream())
				.anyMatch(v -> v.getId().first().charValue() == REACHABLE);
	}

	/**
	 * Collects all towers from the map.
	 *
	 * @return
	 */
	public Collection<FloodingVertex> getTowers() {
		Collection<FloodingVertex> towers = new ArrayList<>();
		Map<Character, Integer> indexes = new HashMap<>();
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				char curr = this.getAsChar(x, y);
				if (curr != REACHABLE && curr != UNREACHABLE) {
					indexes.putIfAbsent(curr, 0);
					towers.add(new FloodingVertex(x, y, curr, indexes.get(curr)));
					indexes.put(curr, indexes.get(curr).intValue() + 1);
				}
			}
		}
		return towers;
	}

}
