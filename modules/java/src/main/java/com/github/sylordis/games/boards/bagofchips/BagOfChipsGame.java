package com.github.sylordis.games.boards.bagofchips;

import static com.github.sylordis.games.boards.bagofchips.Chip.GREEN;
import static com.github.sylordis.games.boards.bagofchips.Chip.ORANGE;
import static com.github.sylordis.games.boards.bagofchips.Chip.PURPLE;
import static com.github.sylordis.games.boards.bagofchips.Chip.RED;
import static com.github.sylordis.games.boards.bagofchips.Chip.YELLOW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.sylordis.commons.math.probability.StatisticalItem;
import com.github.sylordis.commons.math.probability.predicates.And;
import com.github.sylordis.commons.math.probability.predicates.AtLeast;
import com.github.sylordis.commons.math.probability.predicates.Equal;
import com.github.sylordis.commons.math.probability.predicates.Each;
import com.github.sylordis.commons.math.probability.predicates.Last;
import com.github.sylordis.commons.math.probability.predicates.More;
import com.github.sylordis.commons.math.probability.predicates.None;

public class BagOfChipsGame implements StatisticalItem<Chip> {

	/**
	 * Chips drawn each round.
	 */
	public final static int[] CHIPS_PER_ROUND = { 5, 4, 3, 2 };

	/**
	 * Distribution of chips, type => number.
	 */
	public final static Map<Chip, Integer> CHIPS = Map.of(PURPLE, 3, GREEN, 4, RED, 5, ORANGE, 6, YELLOW, 7);

	/**
	 * All cards in the game.
	 */
	public final static Set<ChipCard> CARDS = new HashSet<>(Arrays.asList(
			// Normal points cards
			new ChipCard(1, Each.of(1)),
			new ChipCard(16, And.of(AtLeast.of(GREEN, 2), AtLeast.of(RED, 2))),
			new ChipCard(30, Equal.of(RED, ORANGE)),
			new ChipCard(45, More.of(ORANGE, YELLOW)),
			new ChipCard(45, More.of(GREEN, YELLOW)),
			new ChipCard(50, More.of(RED, GREEN)),
			new ChipCard(61, Last.of(YELLOW)),
			new ChipCard(71, Last.of(ORANGE)),
			new ChipCard(80, More.of(RED, YELLOW)),
			new ChipCard(81, Last.of(RED)),
			new ChipCard(90, More.of(PURPLE, RED)),
			new ChipCard(140, AtLeast.of(GREEN, 4)),
			new ChipCard(201, None.of(PURPLE)),
			new ChipCard(202, None.of(GREEN)),
			// Multiplier points cards
			new ChipCard(RED, 11),
			// Weird cards
			new ChipCard(More.of(PURPLE, YELLOW))
	//
	));

	/**
	 * All rounds.
	 */
	public List<Chip> rounds;
	/**
	 * Content of the bag.
	 */
	public List<Chip> bag;

	public BagOfChipsGame() {
		this.rounds = new ArrayList<>();
		this.bag = new ArrayList<>();
		CHIPS.forEach((k, v) -> {
			for (int i = 0; i < v; i++)
				bag.add(k);
		});
		Collections.shuffle(bag);
	}

	/**
	 * Draw one chip.
	 * 
	 * @return
	 */
	public Chip drawOne() {
		return bag.removeFirst();
	}

	/**
	 * Draws all chips for the given round. If picking again for a round which was
	 * previously done, put all chips of the round back in the bag first and
	 * shuffles.
	 * 
	 * @param round in [1,4]
	 */
	public void drawForRound(int round) {
		for (int n = 0; n < CHIPS_PER_ROUND[round - 1]; n++)
			rounds.add(drawOne());
	}

	/**
	 * 
	 * @param round
	 * @return
	 */
	public List<Chip> getDrawnChipsFor(int round) {
		int start = totalChipsForRound(round - 1);
		int end = start + CHIPS_PER_ROUND[round - 1];
		return rounds.subList(start, end);
	}

	/**
	 * Calculates the total amount of chips once a round is drawn.
	 * 
	 * @param round
	 * @return
	 */
	private int totalChipsForRound(int round) {
		int amount = 0;
		for (int i = 0; i < round; i++)
			amount += CHIPS_PER_ROUND[i];
		return amount;
	}

	/**
	 * Get all the current chips of all rounds.
	 * 
	 * @return
	 */
	public Map<Chip, Integer> getCurrentChipsSummary() {
		Map<Chip, Integer> chips = new HashMap<>();
		for (Chip chip : rounds)
			chips.merge(chip, 1, (a, b) -> a + b);
		return chips;
	}

	/**
	 * Returns the probability to draw a chip of a certain colour.
	 * 
	 * @param chip
	 * @return
	 */
	public float probability(Chip chip) {
		int count = 0;
		for (Chip c : bag)
			if (c == chip)
				count++;
		return count / bag.size();
	}

	public List<Chip> getRounds() {
		return rounds;
	}

	public void setRounds(List<Chip> rounds) {
		this.rounds = rounds;
	}

	public List<Chip> getBag() {
		return bag;
	}

	public void setBag(List<Chip> bag) {
		this.bag = bag;
	}

	@Override
	public int getAmount(Chip a) {
		int count = 0;
		for (Chip c : rounds)
			if (c == a)
				count++;
		return count;
	}

	@Override
	public int getRemaining(Chip a) {
		int count = 0;
		for (Chip c : bag)
			if (c == a)
				count++;
		return count;
	}

	@Override
	public Chip getLast() {
		return rounds.getLast();
	}

	@Override
	public Collection<Chip> getTypes() {
		return Arrays.asList(Chip.values());
	}
}
