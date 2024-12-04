package com.github.sylordis.games.codingame.games.medium.specific;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.sylordis.commons.Direction;
import com.github.sylordis.commons.graph.d2.Point2Dint;

public class Bender {

	private List<Direction> priorities;
	private static final Direction[] PRIORITIES_ARRAY = new Direction[] { Direction.SOUTH, Direction.EAST, Direction.NORTH,
			Direction.WEST };
	private Direction direction;
	private Point2Dint position;
	private boolean breakerMode;

	// --- CONSTRUCTORS

	/**
	 * Constructs a new Bender starting at a given position.
	 *
	 * @param position
	 *            Start position.
	 */
	public Bender(Point2Dint position) {
		reset(position);
	}

	// --- MODIFIERS

	/**
	 * Notifies Bender that he drunk beer, inverting the breaker mode.
	 */
	public void drinkBeer() {
		breakerMode = !breakerMode;
	}

	/**
	 * Moves Bender in one direction.
	 *
	 * @param direction
	 *            Direction of the move to perform
	 */
	public void move(Direction direction) {
		position.translate(direction.dx(), direction.dy());
	}

	/**
	 * Resets Bender's priorities, position and properties.
	 *
	 * @param position
	 *            Position where to place Bender to.
	 */
	public void reset(Point2Dint position) {
		priorities = new ArrayList<>(Arrays.asList(PRIORITIES_ARRAY));
		direction = PRIORITIES_ARRAY[0];
		breakerMode = false;
		this.position = position;
	}

	public void reversePriorities() {
		Collections.reverse(priorities);
	}

	// --- OVERRIDEN METHODS

	// --- GETTERS AND SETTERS

	public Point2Dint getPosition() {
		return position;
	}

	public void setPosition(Point2Dint position) {
		this.position = position;
	}

	public List<Direction> getPriorities() {
		return priorities;
	}

	public void setPriorities(List<Direction> priorities) {
		this.priorities = priorities;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isBreakerMode() {
		return breakerMode;
	}

	public void setBreakerMode(boolean breakerMode) {
		this.breakerMode = breakerMode;
	}

}
