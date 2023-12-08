package com.github.sylordis.games.codingame.games.medium.specific;

import com.github.sylordis.commons.graph.HexagonalRotatingShape;

public class DepotFloor extends HexagonalRotatingShape<Depot> {

	private Depot center;

	public DepotFloor() {
		super();
	}

	public DepotFloor(Depot[] sides) {
		super(sides);
	}

	@Override
	public void rotate(boolean clockwise) {
		super.rotate(clockwise);
		for (Depot depot : getSides())
			if (depot != null)
				depot.rotate(clockwise);
		center.rotate(clockwise);
	}

	/**
	 * @return the center
	 */
	public Depot getCenter() {
		return center;
	}

	/**
	 * @param center
	 *            the center to set
	 */
	public void setCenter(Depot center) {
		this.center = center;
	}

}
