package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BankRobbers {

	static class Vault {
		private final int codeLength;
		private final int digits;

		public Vault(int combinationLength, int digits) {
			this.codeLength = combinationLength;
			this.digits = digits;
		}

		/**
		 * Gets the number of possible combinations for this vault.
		 *
		 * @return
		 */
		public long getNumberOfCombinations() {
			return (long) (Math.pow(10, digits) * Math.pow(Bank.NON_DIGITS.length, codeLength - digits));
		}

		@Override
		public String toString() {
			return "<" + codeLength + "," + digits + "=" + getNumberOfCombinations() + ">";
		}

	}

	static class Bank {
		private final List<Vault> vaults;
		public static final String[] NON_DIGITS = { "A", "E", "I", "O", "U" };

		public Bank() {
			this.vaults = new ArrayList<>();
		}

		public void addVault(Vault vault) {
			vaults.add(vault);
		}

		/**
		 * @return the vaults
		 */
		public List<Vault> getVaults() {
			return vaults;
		}

	}

	private int numberOfRobbers;
	private static final int TIME_PER_COMBINATION = 1;

	public BankRobbers(int n) {
		this.numberOfRobbers = n;
	}

	public long rob(Bank bank) {
		LinkedList<Vault> vaults = new LinkedList<>(bank.getVaults());
		Map<Integer, Long> robbers = new HashMap<>();
		for (int r = 0; r < numberOfRobbers; r++)
			robbers.put(r, 0L);
		long totalTime = 0L;
		while (!vaults.isEmpty()) {
			Collection<Entry<Integer, Long>> idleRobbers = robbers.entrySet().stream().filter(e -> e.getValue() == 0)
					.collect(Collectors.toList());
			for (Entry<Integer, Long> robber : idleRobbers) {
				Vault nextVault = vaults.removeFirst();
				robber.setValue(nextVault.getNumberOfCombinations() * TIME_PER_COMBINATION);
			}
			int min = robbers.values().stream().mapToInt(r -> r.intValue()).min().getAsInt();
			totalTime += min;
			robbers.entrySet().forEach(r -> r.setValue(r.getValue() - min));
		}
		totalTime += robbers.values().stream().mapToInt(r -> r.intValue()).max().getAsInt();
		return totalTime;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int nrobbers = in.nextInt();
		int nvaults = in.nextInt();
		Bank bank = new Bank();
		for (int n = 0; n < nvaults; n++)
			bank.addVault(new Vault(in.nextInt(), in.nextInt()));
		in.close();
		BankRobbers robbers = new BankRobbers(nrobbers);
		System.out.println(robbers.rob(bank));
	}
}
