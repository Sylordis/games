package com.github.sylordis.games.aoc.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

import com.github.sylordis.commons.math.LongSimpleRange;
import com.github.sylordis.commons.math.SimpleRange;
import com.github.sylordis.commons.utils.StringUtils;
import com.github.sylordis.games.aoc.AoCPuzzle;

record TranslationCategory(String name, List<TranslationRange> ranges) {
	public long transpose(long n) {
		Optional<TranslationRange> range = ranges.stream().filter(c -> c.contains(n)).findFirst();
		return range.isPresent() ? range.get().translate(n) : n;
	}

	public List<SimpleRange<Long>> translate(List<SimpleRange<Long>> input) {
		List<SimpleRange<Long>> results = new ArrayList<>();
		for (SimpleRange<Long> range : input) {
			List<TranslationRange> matching = this.ranges.stream().filter(r -> r.containsOrOverlap(range)).toList();
			if (matching.isEmpty())
				results.add(range);
			else
				results.addAll(matching.stream().map(r -> r.translate(range)).flatMap(List::stream).toList());
		}
		return results;
	}
}

record TranslationRange(LongSimpleRange from, LongSimpleRange to) {
	public boolean contains(long n) {
		return from.contains(n);
	}

	public boolean containsOrOverlap(SimpleRange<Long> range) {
		return from.contains(range) || from.overlaps(range);
	}

	public long translate(long n) {
		long r = n;
		if (contains(n))
			r = n - from.start() + to.start();
		return r;
	}

	public boolean overlaps(LongSimpleRange r) {
		return this.from.overlaps(r);
	}

	public List<SimpleRange<Long>> translate(SimpleRange<Long> range) {
		List<SimpleRange<Long>> results = new ArrayList<>();
		if (this.from.contains(range)) {
			// Case 1: range is contained in "from", created only one new transposed range
			results.add(new LongSimpleRange(translate(range.start()), translate(range.end())));
		} else {
			// Case 2: range overlaps "from" or contains "from"
			if (range.start() < from.start()) {
				// Under
				results.add(new LongSimpleRange(range.start(), from.start() - 1));
			}
			if (range.start() < from.start() && range.end() > from.end()) {
				results.add(to.clone());
			} else if (range.end() < from.end()) {
				results.add(new LongSimpleRange(to.start(), translate(range.end())));
			} else if (range.start() > from.start()) {
				results.add(new LongSimpleRange(translate(range.start()), to.end()));
			}
			if (range.end() > from.end()) {
				// Over
				results.add(new LongSimpleRange(from.end() + 1, range.end()));
			}
		}
		return results;
	}
}

public class Day5 extends AoCPuzzle {

	private List<Long> seeds = new ArrayList<>();
	private List<LongSimpleRange> seedsRanges = new ArrayList<>();
	private List<TranslationCategory> categories = new ArrayList<>();

	@Override
	public void puzzle1() {
		load(this::setSeeds);
		print("seeds={}", seeds);
//		categories.forEach(c -> print(c));
		List<Long> before = new ArrayList<>();
		before.addAll(seeds);
		List<Long> after = new ArrayList<>();
		for (TranslationCategory category : categories) {
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
		print("seeds={}", seedsRanges);
		List<SimpleRange<Long>> before = new ArrayList<>();
		before.addAll(seedsRanges);
		List<SimpleRange<Long>> after = new ArrayList<>();
		for (TranslationCategory category : categories) {
			print(category.name());
			category.ranges().forEach(this::print);
			after.clear();
			after.addAll(category.translate(before));
			before.clear();
			before = SimpleRange.mergeAllOnce((List<SimpleRange<Long>>) after);
			print(before);
		}
		long min = Long.MAX_VALUE;
		for (SimpleRange<Long> r : before) {
			if (r.start() < min)
				min = r.start();
		}
		print(min);
	}

	public void setSeedsRanges(String line) {
		String[] seedsStr = line.substring(line.indexOf(':') + 1).trim().split(" +");
		for (int i = 0; i < seedsStr.length; i += 2)
			this.seedsRanges
			        .add(LongSimpleRange.fromSpan(Long.parseLong(seedsStr[i]), Long.parseLong(seedsStr[i + 1])));
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
			List<TranslationRange> ranges = new ArrayList<>();
			while (in.hasNext()) {
				String line = in.nextLine();
//				print(line);
				if (nline == 0) {
					seedSetter.accept(line);
				} else if (line.isEmpty() && id != null) {
					// End of category
					this.categories.add(new TranslationCategory(id, new ArrayList<>(ranges)));
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
						ranges.add(new TranslationRange(LongSimpleRange.fromSpan(numbers.get(1), numbers.get(2)),
						        LongSimpleRange.fromSpan(numbers.get(0), numbers.get(2))));
//						print("New range: {}", ranges);
					}
				}
				nline++;
			}
			// End category again
			this.categories.add(new TranslationCategory(id, ranges));
		}
	}

	public void setSeeds(String line) {
		this.seeds.addAll(StringUtils.strToList(line.substring(line.indexOf(':') + 1), " +", Long::parseLong));
	}

	public static void main(String[] args) {
		new Day5().run(args);
	}

}
