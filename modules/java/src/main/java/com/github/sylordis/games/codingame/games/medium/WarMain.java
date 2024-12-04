package com.github.sylordis.games.codingame.games.medium;

import static com.github.sylordis.commons.utils.PrintUtils.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.github.sylordis.games.codingame.games.medium.war.WarCard;
import com.github.sylordis.games.codingame.games.medium.war.WarPlayer;

public class WarMain {

	private static final int PAT = 0;

	private int rounds;
	private final List<WarPlayer> players;
	private boolean over;
	private boolean pat;

	/**
	 * Constructs a war game.
	 */
	public WarMain() {
		rounds = 0;
		over = false;
		pat = false;
		players = new ArrayList<>();
	}

	/**
	 * Adds a new player.
	 *
	 * @param player
	 */
	private void addPlayer(WarPlayer player) {
		players.add(player);
	}

	/**
	 * Plays a round of War.
	 */
	public void play() {
		rounds++;
		debug("=== Round {} begins", rounds);
		try {
			drawCards(1);
			players.forEach(p -> debug(p));
			int outcome;
			while ((outcome = battle()) == 0) {
				// 3 cards for the war, and the next one played
				debug("war");
				drawCards(3);
				drawCards(1);
				players.forEach(p -> debug(p));
			}
			endRound(outcome < 0 ? players.get(1) : players.get(0));
			if (players.stream().anyMatch(p -> !p.hasCards()))
				over = true;
		} catch (IndexOutOfBoundsException e) {
			debug("Not enough cards!");
			over = true;
			pat = true;
		}
	}

	/**
	 * Ends the round, the winner takes all cards. First player's cards first, then second player's.
	 *
	 * @param winner
	 *            Player which will win all cards.
	 */
	private void endRound(WarPlayer winner) {
		for (int i = 0; i < players.size(); i++) {
			List<WarCard> pCards = players.get(i).removePlayedCards();
			players.get(i).emptyHands();
			winner.addCardsToDeck(pCards);
		}
		debug("P{} wins", winner.getId());
	}

	/**
	 * Checks the first player's last played card against the second player's last played card.
	 *
	 * @return < 0, 0 or > 0 if players 1 card is (respectively) lower, equal or greater than player's 2.
	 */
	private int battle() {
		return players.get(0).get().compareTo(players.get(1).get());
	}

	/**
	 * Makes each player draw N cards.
	 *
	 * @param n
	 */
	public void drawCards(int n) {
		players.forEach(p -> p.draw(n));
	}

	/**
	 * Returns the result of the game.
	 *
	 * @return a string, either PAT, or the winner and the number of rounds played.
	 */
	public int getResult() {
		int result;
		if (pat) {
			result = PAT;
		} else {
			WarPlayer winner = players.stream().filter(WarPlayer::hasCards).findFirst().get();
			result = winner.getId();
		}
		return result;
	}

	/**
	 * Gets the number of rounds played.
	 *
	 * @return
	 */
	public int getRounds() {
		return rounds;
	}

	/**
	 * Checks if the game is over or not.
	 *
	 * @return
	 */
	public boolean isOver() {
		return over;
	}

	/**
	 * Retrieves the next set of cards.
	 *
	 * @param in
	 * @return a list of cards
	 */
	private static List<WarCard> getNextCards(Scanner in) {
		List<WarCard> list = new ArrayList<>();
		int n = in.nextInt();
		for (int i = 0; i < n; i++) {
			final String cstr = in.next();
			list.add(WarCard.buildCard(cstr));
		}
		return list;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		WarMain war = new WarMain();
		for (int i = 1; i <= 2; i++) {
			WarPlayer p = new WarPlayer(i);
			p.addCardsToDeck(getNextCards(in));
			war.addPlayer(p);
			debug(p);
		}
		while (!war.isOver())
			war.play();
		int result = war.getResult();
		System.out.println(result != PAT ? result + " " + war.getRounds() : "PAT");
		in.close();
	}

}