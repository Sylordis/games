package com.github.sylordis.games.aoc.aoc2021;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3 {

	public static void main(String[] args) {
		new Day3().run1();
	}

	public void run1() {
		// Gamma rate: most common bits
		int gammaRate = 0;
		// epsilon rate: least common bits (max - gamma rate)
		int epsilonRate = 0;
		Collection<Integer> report = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext())
				report.add(in.nextInt(2));
		}
		int max = report.stream().mapToInt(n -> n).max().getAsInt();
		int bitSize = Integer.toBinaryString(max).length();
		print("report size={} max={} ({})2[{}]", report.size(), max, Integer.toBinaryString(max), bitSize);
		// Process list to find Gamma rate
		for (int bit = 0; bit < bitSize; bit++)
			gammaRate += getMostCommonNBit(report, bit) << bit;
		BigInteger twoToN = BigInteger.ZERO.setBit(bitSize);
		BigInteger maxNbits = twoToN.subtract(BigInteger.ONE);
		print("Max on {} bits: {} ({})2", bitSize, maxNbits.intValue(), Integer.toBinaryString(maxNbits.intValue()));
		epsilonRate = maxNbits.intValue() - gammaRate;
		print("gamma={} epsilon={}", gammaRate, epsilonRate);
		System.out.println(gammaRate * epsilonRate);
	}

	public void run2() {
		// oxygen generator rating: incremental filtering of most common bit
		int oxGenRat = -1;
		// co2 scrubber rating: incremental filtering of least common bit
		int co2ScrubRate = -1;
		Collection<Integer> report = new ArrayList<>();
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext())
				report.add(in.nextInt(2));
		}
		int max = report.stream().mapToInt(n -> n).max().getAsInt();
		int bitSize = Integer.toBinaryString(max).length();
		// Get biggest number possible on given number of bits
		BigInteger twoToN = BigInteger.ZERO.setBit(bitSize);
		int maxNbits = twoToN.subtract(BigInteger.ONE).intValue();
		print("report size={} max={} ({})2[{}] max({} bits)={}({})2", report.size(), max, Integer.toBinaryString(max),
		        bitSize, bitSize, maxNbits, Integer.toBinaryString(maxNbits));
		int mask = 0;
		List<Integer> oxCandidates = new ArrayList<>(report);
		List<Integer> co2Candidates = new ArrayList<>(report);
		int mRate = 0;
		int lRate = 0;
		for (int nbit = bitSize - 1; nbit >= 0; nbit--) {
			Map<Integer, Long> occurrencesMost = countByOccurrence(oxCandidates, nbit);
			Map<Integer, Long> occurrencesLeast = countByOccurrence(co2Candidates, nbit);
			boolean equalOccurrencesMost = hasEqualOccurrences(occurrencesMost);
			boolean equalOccurrencesLeast = hasEqualOccurrences(occurrencesLeast);
			final int mostCommonNBit = equalOccurrencesMost ? 1 : getMostCommon(occurrencesMost, nbit);
			final int leastCommonNBit = equalOccurrencesLeast ? 0 : getLeastCommon(occurrencesLeast, nbit);
			mRate += mostCommonNBit << nbit;
			lRate += leastCommonNBit << nbit;
			mask += 1 << nbit;
			final int fmask = mask;
			final int fmRate = mRate;
			final int flRate = lRate;
			print("BIT {}: most={} least={} mask={} most={}({}) least={}({})", nbit, mostCommonNBit, leastCommonNBit,
			        Integer.toBinaryString(mask), Integer.toBinaryString(mRate), mRate, Integer.toBinaryString(lRate),
			        lRate);
			// Try to set oxygen rating if not set
			if (oxGenRat == -1) {
				oxCandidates = report.stream().filter(n -> ((n & fmask) ^ fmRate) == 0).collect(Collectors.toList());
				if (oxCandidates.size() == 1)
					oxGenRat = oxCandidates.get(0);
				print("oxc={}({})", oxCandidates.stream().map(n -> pad0(Integer.toBinaryString(n), bitSize))
				        .collect(Collectors.toList()), oxCandidates.size());
			}
			// Try to set co2 scrubber rating if not set
			if (co2ScrubRate == -1) {
				co2Candidates = report.stream().filter(n -> ((n & fmask) ^ flRate) == 0).collect(Collectors.toList());
				if (co2Candidates.size() == 1)
					co2ScrubRate = co2Candidates.get(0);
				print("co2c={}({})", co2Candidates.stream().map(n -> pad0(Integer.toBinaryString(n), bitSize))
				        .collect(Collectors.toList()), co2Candidates.size());
			}
		}
		// Calculate epsilon rate by subtracting gamma rate from maximum number (inverse)
		print("oxGen={} co2Scrub={}", oxGenRat, co2ScrubRate);
		System.out.println(oxGenRat * co2ScrubRate);
	}

	private Integer getMostCommonNBit(Collection<Integer> list, int nbit) {
		return list.stream().map(n -> getBit(n, nbit))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.max(Map.Entry.comparingByValue()).get().getKey();
	}

	private boolean hasEqualOccurrences(Map<Integer, Long> data) {
		return data.containsKey(0) && data.containsKey(1) && data.get(0) == data.get(1);
	}

	private Integer getMostCommon(Map<Integer, Long> occurrences, int nbit) {
		return occurrences.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
	}

	private Integer getLeastCommon(Map<Integer, Long> occurrences, int nbit) {
		return occurrences.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
	}

	private Map<Integer, Long> countByOccurrence(Collection<Integer> list, int nbit) {
		return list.stream().map(n -> getBit(n, nbit))
		        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	private int getBit(int number, int bit) {
		return (number >>> bit) & 1;
	}

	private String pad0(String s, int size) {
		return String.format("%" + size + "s", s).replace(' ', '0');
	}

}
