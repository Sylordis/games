package com.github.sylordis.games.boards.bagofchips;

import java.util.function.Function;

import com.github.sylordis.commons.math.probability.predicates.AtLeast;
import com.github.sylordis.commons.math.probability.predicates.UniversalCriteria;

public class ChipCard {

	private final Function<BagOfChipsGame, Integer> points;
	private final UniversalCriteria<BagOfChipsGame> condition;
	private final boolean isWinning;

	/**
	 * Creates a card that provides a fixed amount of points when satisfied.
	 * 
	 * @param points
	 * @param condition
	 */
	public ChipCard(int points, UniversalCriteria<BagOfChipsGame> condition) {
		this(g -> points, condition, false);
	}

	/**
	 * Creates a card that automatically wins or loses the round if satisfied.
	 * 
	 * @param condition
	 */
	public ChipCard(UniversalCriteria<BagOfChipsGame> condition) {
		this(g -> 0, condition, true);
	}

	/**
	 * Creates a card of variable point amount when satisfied.
	 * 
	 * @param chip
	 * @param multiplier
	 */
	public ChipCard(Chip chip, int multiplier) {
		this(g -> g.getAmount(chip)*multiplier, AtLeast.of(chip, 1), false);
	}

	/**
	 * Universal card creation constructor.
	 * 
	 * @param points    How many points this card is supposed to make gain
	 * @param condition Condition to fulfil to be satisfied.
	 * @param isWinning Whether this card automatically wins or loses the game if
	 *                  satisfied.
	 */
	private ChipCard(Function<BagOfChipsGame, Integer> points, UniversalCriteria<BagOfChipsGame> condition,
			boolean isWinning) {
		this.points = points;
		this.condition = condition;
		this.isWinning = isWinning;
	}

	public int getPoints(BagOfChipsGame game) {
		return points.apply(game);
	}

	public UniversalCriteria<BagOfChipsGame> getCondition() {
		return condition;
	}

	public boolean isWinning() {
		return isWinning;
	}

}
