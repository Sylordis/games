package com.github.sylordis.games.codingame.games.medium.war;

import com.github.sylordis.commons.cards.Card;
import com.github.sylordis.commons.cards.ClassicCardColor;

/**
 * Classic card.
 */
public class WarCard extends Card {

	/**
	 * Basic constructor for classic card.
	 *
	 * @param color
	 * @param value
	 */
	public WarCard(ClassicCardColor color, int value) {
		super(color, value);
	}

	@Override
	public String toString() {
		return valueToStr(this.getValue()) + this.getColor().getSymbol();
	}

	/**
	 * Builds a war card.
	 *
	 * @param cstr
	 * @return
	 */
	public static WarCard buildCard(String cstr) {
		final ClassicCardColor color = (ClassicCardColor) ClassicCardColor
				.getFromSymbol(cstr.substring(cstr.length() - 1));
		final int value = strToValue(cstr.substring(0, cstr.length() - 1));
		return new WarCard(color, value);
	}

	/**
	 * Translates the value of a card into an integer.
	 *
	 * @param strVal
	 * @return
	 */
	public static int strToValue(String strVal) {
		int ret = 0;
		switch (strVal) {
			case "J":
				ret = 11;
				break;
			case "Q":
				ret = 12;
				break;
			case "K":
				ret = 13;
				break;
			case "A":
				ret = 14;
				break;
			default:
				ret = Integer.parseInt(strVal);
				break;
		}
		return ret;
	}

	/**
	 * Translates the value of a card into an integer.
	 *
	 * @param strVal
	 * @return
	 */
	public static String valueToStr(int strVal) {
		String ret = "X";
		switch (strVal) {
			case 11:
				ret = "J";
				break;
			case 12:
				ret = "Q";
				break;
			case 13:
				ret = "K";
				break;
			case 14:
				ret = "A";
				break;
			default:
				ret = "" + strVal;
				break;
		}
		return ret;
	}
}
