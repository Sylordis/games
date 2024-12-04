package com.github.sylordis.games.codingame.games.medium.specific;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.sylordis.commons.Direction;
import com.github.sylordis.commons.RotationDirection;

public class LastCrusadeRoom implements Cloneable {

	private Map<Direction, Direction> inOuts;

	/**
	 * Constructs a new room without any entry or exit points.
	 */
	public LastCrusadeRoom() {
		inOuts = new HashMap<>();
	}

	/**
	 * Rotates the room, changing entry and exit points.
	 *
	 * @param direction
	 */
	public void rotate(RotationDirection direction) {
		// Map<Direction, Direction> replacements = new HashMap<>();
		// TODO
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		LastCrusadeRoom room = new LastCrusadeRoom();
		for (Entry<Direction, Direction> entry : inOuts.entrySet())
			room.addInOut(entry.getKey(), entry.getValue());
		return room;
	}

	/**
	 * Add one possibility of entry/exit.
	 *
	 * @param in
	 *            Entry point.
	 * @param out
	 *            Where the entry point leads for exiting.
	 */
	public void addInOut(Direction in, Direction out) {
		inOuts.put(in, out);
	}

	/**
	 * Gets the exit for a given entry point.
	 *
	 * @param from
	 *            Entry direction.
	 * @return An exit direction or null if it does not exist.
	 */
	public Direction getExit(Direction from) {
		return inOuts.get(from);
	}

	/**
	 * Replaces all entries and exits of this room.
	 *
	 * @param inouts
	 *            A new set of entries and exits.
	 */
	protected void setInOuts(Map<Direction, Direction> inouts) {
		this.inOuts = inouts;
	}

}
