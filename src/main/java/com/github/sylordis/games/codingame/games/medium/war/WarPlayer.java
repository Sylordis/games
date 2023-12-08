package com.github.sylordis.games.codingame.games.medium.war;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WarPlayer {

	private final LinkedList<WarCard> deck;
	private int played;
	private final int id;

	/**
	 * Constructs a new player with a unique id.
	 *
	 * @param id
	 */
	public WarPlayer(int id) {
		this.id = id;
		played = -1;
		deck = new LinkedList<>();
	}

	/**
	 * Empty player's played cards.
	 */
	public void emptyHands() {
		played = -1;
	}

	/**
	 * Adds some cards to player's deck.
	 *
	 * @param cards
	 */
	public void addCardsToDeck(Collection<WarCard> cards) {
		deck.addAll(cards);
	}

	/**
	 * Plays N cards from the deck to the played stack.
	 *
	 * @param n
	 *            Number of cards to draw
	 * @throws IndexOutOfBoundsException
	 *             If there's not enough cards in the deck
	 *
	 */
	public void draw(int n) throws IndexOutOfBoundsException {
		played += n;
		if (played > deck.size())
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Checks if the player has cards.
	 *
	 * @return
	 */
	public boolean hasCards() {
		return !deck.isEmpty();
	}

	/**
	 * Checks if the player has played any cards in front of them.
	 *
	 * @return
	 */
	public boolean hasPlayedCards() {
		return played >= 0;
	}

	/**
	 * @return the played cards
	 */
	public List<WarCard> getPlayedCards() {
		return new ArrayList<>(deck.subList(0, played + 1));
	}

	/**
	 * @return the played cards
	 */
	public List<WarCard> removePlayedCards() {
		List<WarCard> cards = getPlayedCards();
		deck.removeAll(cards);
		return cards;
	}

	/**
	 *
	 * @return the deck
	 */
	public LinkedList<WarCard> getDeck() {
		return deck;
	}

	/**
	 * Gets the first card played from the player.
	 *
	 * @return
	 */
	public WarCard get() {
		return deck.get(played);
	}

	/**
	 * @return the player id
	 */
	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "P" + id + ": " + played + "::" + deck;
	}
}
