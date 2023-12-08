package com.github.sylordis.games.aoc.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

import com.github.sylordis.commons.math.LongSimpleRange;
import com.github.sylordis.commons.utils.StringUtils;
import com.github.sylordis.games.aoc.AoCPuzzle;

record TranspositionCategory(String name, List<TranspositionRange> ranges) {
	public long transpose(long n) {
		Optional<TranspositionRange> range = ranges.stream().filter(c -> c.contains(n)).findFirst();
		return range.isPresent() ? range.get().transpose(n) : n;
	}

	public List<LongSimpleRange> transpose(LongSimpleRange r) {
		List<TranspositionRange> range = ranges.stream().filter(c -> c.overlaps(r)).toList();
		List<LongSimpleRange> results = new ArrayList<>();
//		results.addAll(range.isPresent() ? range.get().transpose(r) : Arrays.asList(new LongSimpleRange[]{ r }));
		return results;
	}
}

record TranspositionRange(long from, long to, long range) {
	public boolean contains(long n) {
		return n >= from && n <= from + range - 1;
	}

	public long transpose(long n) {
		long r = n;
		if (contains(n))
			r = n - from + to;
		return r;
	}

	public boolean overlaps(LongSimpleRange r) {
		return LongSimpleRange.fromSpan(from, range).overlaps(r);
	}
	
	public List<LongSimpleRange> transpose(LongSimpleRange r) {
		// TODO
		return null;
	}
}

public class Day5 extends AoCPuzzle {

	private List<Long> seeds = new ArrayList<>();
	private List<LongSimpleRange> seedsRanges = new ArrayList<>();
	private List<TranspositionCategory> categories = new ArrayList<>();

	@Override
	public void puzzle1() {
		load(this::setSeeds);
		print("seeds={}", seeds);
//		categories.forEach(c -> print(c));
		List<Long> before = new ArrayList<>();
		before.addAll(seeds);
		List<Long> after = new ArrayList<>();
		for (TranspositionCategory category : categories) {
//			print(category.name());
//			category.ranges().forEach(r -> print(r));
			after.addAll(before.stream().map(n -> category.transpose(n)).toList());
			before.clear();
			before.addAll(after);
			after.clear();
//			print("cat={} {}", category.name(), before);
//			break;
		}
//		print(before);
		before.sort(Long::compareTo);
		print(before.get(0));
	}

	@Override
	public void puzzle2() {
		load(this::setSeedsRanges);
		print(seedsRanges);
	}

	public void setSeedsRanges(String line) {
		String[] seedsStr = line.substring(line.indexOf(':') + 1).trim().split(" +");
		for (int i = 0; i < seedsStr.length; i += 2)
			this.seedsRanges.add(
			        LongSimpleRange.fromSpan(Long.parseLong(seedsStr[i]), Long.parseLong(seedsStr[i + 1])));
	}

	/**
	 * Loads input from {@link System#in}.
	 * 
	 * @param seedSetter
	 */
	public void load(Consumer<String> seedSetter) {
		try (Scanner in = new Scanner(System.in)) {
			int nline = 0;
			String id = null;
			List<TranspositionRange> ranges = new ArrayList<>();
			while (in.hasNext()) {
				String line = in.nextLine();
//				print(line);
				if (nline == 0) {
					seedSetter.accept(line);
				} else if (line.isEmpty() && id != null) {
					// End of category
					this.categories.add(new TranspositionCategory(id, new ArrayList<>(ranges)));
//					print("End category? id={} ranges={}", id, ranges);
//					this.categories.forEach(c -> print(c));
					ranges.clear();
					id = null;
				} else if (!line.isEmpty()) {
					if (id == null) {
						// Category beginning => get id
						id = line.substring(0, line.indexOf(" map:"));
//						print("New category: {}", id);
					} else {
						// Category numbers
						List<Long> numbers = StringUtils.strToList(line, " +", Long::parseLong);
						ranges.add(new TranspositionRange(numbers.get(1), numbers.get(0), numbers.get(2)));
//						print("New range: {}", ranges);
					}
				}
				nline++;
			}
			// End category again
			this.categories.add(new TranspositionCategory(id, ranges));
		}
	}

	public void setSeeds(String line) {
		this.seeds.addAll(StringUtils.strToList(line.substring(line.indexOf(':') + 1), " +", Long::parseLong));
	}

	public static void main(String[] args) {
		new Day5().run(args);
	}

}
