package com.github.sylordis.games.codingame.games.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.github.sylordis.commons.Direction;
import com.github.sylordis.commons.Triplet;
import com.github.sylordis.commons.boards.BoardPoint;
import com.github.sylordis.commons.graph.d2.Point2Dint;
import com.github.sylordis.games.codingame.games.medium.specific.Bender;

/**
 *
 * Symbols:
 * \@ entry point
 * $ exit (suicide) point
 * # unbreakable wall
 * x breakable wall (breaker mode)
 * S,E,N,W forces current direction to SOUTH,EAST,NORTH,WEST
 * I priorities inverter
 * B beer, switch breaker mode
 * T teleporter, switch to other teleporter
 *
 *
 */
public class BenderMain {

	private BoardPoint arena;
	private Bender bender;
	private final static int LOOP_THRESHOLD = 5;

	private boolean applyNewTileEffects() {
		boolean end = false;
		char tile = arena.getAsChar((int) bender.getPosition().x(), (int) bender.getPosition().y());
		switch (tile) {
			case 'x':
			case 'X':
				arena.set((int) bender.getPosition().x(), (int) bender.getPosition().y(), Integer.valueOf(' '));
				break;
			case 'S':
			case 'E':
			case 'N':
			case 'W':
				bender.setDirection(Direction.getFromString("" + tile));
				break;
			case 'I':
				bender.reversePriorities();
				break;
			case 'B':
				bender.drinkBeer();
				break;
			case 'T':
				List<Point2Dint> teleports = arena.getTiles('T');
				teleports.remove(bender.getPosition());
				bender.setPosition(teleports.get(0));
				break;
			case '$':
				end = true;
				break;
		}
		return end;
	}

	public boolean canMove(Point2Dint pos) {
		boolean result = true;
		// Check if coordinates in limits
		if (!arena.isInLimits(pos.x(), pos.y())) {
			result = false;
		} else {
			// Check tile
			switch (arena.getAsChar(pos.x(), pos.y())) {
				case '#':
					result = false;
					break;
				case 'x': // Fallthrough
				case 'X':
					result = bender.isBreakerMode();
					break;
			}
		}
		return result;
	}

	public Direction determineNextMove() {
		// Recopy priorities of bender
		List<Direction> directions = new ArrayList<>(bender.getPriorities());
		// Put its current direction in the head of priorities
		directions.remove(bender.getDirection());
		directions.add(0, bender.getDirection());
		// Go along priorities
		Direction current, result = null;
		Point2Dint next;
		Iterator<Direction> it = directions.iterator();
		while (null == result && it.hasNext()) {
			current = it.next();
			next = bender.getPosition().clone();
			next.translate(current.dx(), current.dy());
			if (canMove(next))
				result = current;
		}
		return result;
	}

	private String run(Point2Dint start) {
		boolean ended = false;
		boolean turnsOutToBeLoop = false;
		BoardPoint latestSnapshot = (BoardPoint) arena.clone();
		bender = new Bender(start);
		Map<Triplet<Point2Dint, Direction, Boolean>, Integer> passed = new HashMap<>();
		StringBuilder rame = new StringBuilder();
		while (!ended && !turnsOutToBeLoop) {
			Direction next = determineNextMove();
			// System.err.println(bender.getPosition() + " " + next.toString() + " " + bender.isBreakerMode());
			rame.append(next.toString() + "\n");
			bender.move(next);
			bender.setDirection(next);
			// Check if already passed in that square
			Triplet<Point2Dint, Direction, Boolean> key = new Triplet<>(bender.getPosition(), bender.getDirection(),
					bender.isBreakerMode());
			if (passed.containsKey(key)) {
				// System.err.println(ArenaUtils.printArenas(arena, latestSnapshot));
				turnsOutToBeLoop = latestSnapshot.equals(arena) && passed.get(key) < LOOP_THRESHOLD;
				if (!turnsOutToBeLoop)
					passed.put(key, Integer.valueOf(passed.get(key) + 1));
			} else {
				key.setFirst(bender.getPosition().clone());
				passed.put(key, 0);
			}
			latestSnapshot = (BoardPoint) arena.clone();
			ended = applyNewTileEffects();
		}
		if (turnsOutToBeLoop)
			rame = new StringBuilder("LOOP");
		return rame.toString();
	}

	public BenderMain() {
		Scanner in = new Scanner(System.in);
		int H = in.nextInt();
		int W = in.nextInt();
		arena = new BoardPoint(W, H);
		Point2Dint startPos = new Point2Dint();
		in.nextLine();
		// Get arena
		for (int i = 0; i < H; i++) {
			String line = in.nextLine();
			arena.setRowFrom(i, line);
			int startChar = line.indexOf("@");
			if (startChar != -1) {
				startPos = new Point2Dint(startChar, i);
				System.err.println("Start: " + startPos);
			}
		}
		in.close();
		System.err.println(arena.toStringDefault());
		arena.clone();

		System.out.println(run(startPos));
	}

	public static void main(String[] args) {
		new BenderMain();
	}

}
