package com.github.sylordis.games.codingame.games.puzzles.xorandor;

import com.github.sylordis.commons.logical.ANDgate;
import com.github.sylordis.commons.logical.AbstractLogicalGate;
import com.github.sylordis.commons.logical.LogicalComponent;
import com.github.sylordis.commons.logical.ORgate;
import com.github.sylordis.commons.logical.Output;
import com.github.sylordis.commons.logical.SwitchGenerator;

public enum XorandorElement {

	// Outputs
	LED('@', Output.class),
	// Gates
	NOT('~', AbstractLogicalGate.class), AND('&', ANDgate.class), OR('|', ORgate.class), XOR('+',
			AbstractLogicalGate.class), NAND('^',
					AbstractLogicalGate.class), NOR('-', AbstractLogicalGate.class), XNOR('=', AbstractLogicalGate.class),
	// Switches
	SWITCH_LEFT('<', AbstractLogicalGate.class), SWITCH_RIGHT('>', AbstractLogicalGate.class), SWITCH('0', SwitchGenerator.class),
	// Cables
	CABLE('\u2300', null, true), CABLE_CHUNK('C', null, true);

	private char symbol;
	private boolean replaceable;
	private Class<? extends LogicalComponent> targetClass;

	private XorandorElement(char symbol, Class<? extends LogicalComponent> targetClass) {
		this(symbol, targetClass, false);
	}

	private XorandorElement(char symbol, Class<? extends LogicalComponent> targetClass, boolean replaceable) {
		this.symbol = symbol;
		this.replaceable = replaceable;
		this.targetClass = targetClass;
	}

	/**
	 * Gets an element according to its symbol.
	 *
	 * @param symbol
	 *            The symbol to match against
	 * @return the given element, or null if it does not match anything
	 */
	public static XorandorElement matchBySymbol(char symbol) {
		for (XorandorElement elt : XorandorElement.values()) {
			if (symbol == elt.getSymbol())
				return elt;
		}
		return null;
	}

	public char getSymbol() {
		return symbol;
	}

	public boolean isReplaceable() {
		return replaceable;
	}

	public Class<? extends LogicalComponent> getTargetClass() {
		return targetClass;
	}

}
