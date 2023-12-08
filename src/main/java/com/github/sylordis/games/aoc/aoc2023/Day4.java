package com.github.sylordis.games.aoc.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.sylordis.games.aoc.AoCPuzzle;

record ScratchCard(int id, List<Integer> numbers, List<Integer> winningNumbers) {
	/**
	 * Gets the score of a card, which is equal to 2^(n-1) where n is the matching winning numbers.
	 * 
	 * @return the score of the card or 0 if the card has no matching numbers.
	 */
	public long getScore() {
		int matching = getMatching();
		return matching == 0 ? 0 : (long) Math.pow(2, matching - 1);
	}

	/**
	 * Gets the amount of matching numbers matching winning numbers.
	 * 
	 * @return
	 */
	public int getMatching() {
		int matching = 0;
		for (int n : numbers)
			if (winningNumbers.contains(n))
				matching++;
		return matching;
	}
}

public class Day4 extends AoCPuzzle {

	@Override
	public void puzzle1() {
		List<ScratchCard> cards = new ArrayList<>();
		nomz(l -> cards.add(cardFrom(l)));
//		print(cards);
		long score = cards.stream().mapToLong(ScratchCard::getScore).sum();
		print(score);
	}

	@Override
	public void puzzle2() {
		List<ScratchCard> cards = new ArrayList<>();
		nomz(l -> cards.add(cardFrom(l)));
		// Dynamic array
		int[] cardsArr = new int[cards.size()];
		Arrays.fill(cardsArr, 1);
//		print(Arrays.toString(cardsArr));
		for (int i = 0; i < cards.size(); i++) {
			int matches = cards.get(i).getMatching();
			for (int x = i+1; x <= i + matches; x++) {
				cardsArr[x] += cardsArr[i];
			}
//			print("{} m{} => {}", i, matches, Arrays.toString(cardsArr));
		}
//		print(Arrays.toString(cardsArr));
		long sum = Arrays.stream(cardsArr).sum();
		print(sum);
	}

	private ScratchCard cardFrom(String l) {
		String[] parts1 = l.split(":");
		String idsub = parts1[0].replaceFirst("Card +", "");
		int id = Integer.parseInt(idsub);
		String[] numbers = parts1[1].split(" \\| ");
		return new ScratchCard(id, strToIntList(numbers[0]), strToIntList(numbers[1]));
	}

	private List<Integer> strToIntList(String s) {
		String[] numbers = s.trim().split(" +");
//		print("{} => {}", s, Arrays.toString(numbers));
		return new ArrayList<Integer>(Arrays.stream(numbers).map(Integer::parseInt).collect(Collectors.toList()));
	}

	public static void main(String[] args) {
		new Day4().run(args);
	}

}
