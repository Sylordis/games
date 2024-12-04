package com.github.sylordis.games.codingame.games.medium.specific;

import com.github.sylordis.commons.graph.HexagonalPosition;
import com.github.sylordis.commons.graph.HexagonalRotatingShape;

public class Depot extends HexagonalRotatingShape<String> implements Cloneable {

	private final int id;

	public Depot(int id, String[] textures) {
		super(textures);
		this.id = id;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Depot(id, getSides().toArray(String[]::new));
	}

	/**
	 * Checks if a depot have a matching texture.
	 *
	 * @param depot
	 * @return
	 */
	public boolean haveMatchingTexture(Depot depot) {
		return depot.getSides().stream().anyMatch(getSides()::contains);
	}

	public boolean is(Depot depot) {
		return this.id == depot.getId();
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return Integer.toString(id) + getSide(HexagonalPosition.EAST);
	}

	public String draw() {
		StringBuffer buff = new StringBuffer();
		buff.append("  / \\  \n");
		buff.append(" /").append(getSide(HexagonalPosition.NORTH_WEST)).append(" ")
				.append(getSide(HexagonalPosition.NORTH_EAST)).append("\\ \n");
		buff.append("|     |\n");
		buff.append("|").append(getSide(HexagonalPosition.WEST)).append(" ").append(id).append(" ")
				.append(getSide(HexagonalPosition.EAST)).append("|\n");
		buff.append("|     |\n");
		buff.append(" \\").append(getSide(HexagonalPosition.SOUTH_WEST)).append(" ")
				.append(getSide(HexagonalPosition.SOUTH_EAST)).append("/ \n");
		buff.append("  \\ /  ");
		return buff.toString();
	}
}
