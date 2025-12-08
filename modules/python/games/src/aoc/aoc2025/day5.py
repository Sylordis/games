from pathlib import Path

from ..puzzle_aoc import PuzzleAOC
from ...utils.ranges import Range


class Day2025n05(PuzzleAOC):

    def parse_input(self, input_file: Path) -> tuple[list[int], list[Range]]:
        data_ranges = []
        data_fresh = []
        with open(input_file, "r", encoding="utf-8") as file:
            ranges = True
            for line in file.readlines():
                if line.strip() == "":
                    ranges = False
                elif ranges:
                    r = [int(n) for n in line.strip().split("-")]
                    data_ranges.append(Range(r[0], r[1]))
                else:
                    data_fresh.append(int(line))
        return (data_ranges, data_fresh)

    def part1(self, data) -> int:
        fresh:int = 0
        self._log.debug(data[0])
        self._log.debug(data[1])
        r:Range
        for p in data[1]:
            for r in data[0]:
                if r.contains(p):
                    fresh = fresh + 1
                    break
        return fresh

    def part2(self, data) -> int:
        ranges:list[Range] = data[0]
        processed:list[Range] = []
        self._log.debug("start=%s", ranges)
        while len(ranges) > 0:
            r = ranges[0]
            indexes = []
            # Try to merge every range that intersects
            for i, rsub in enumerate(ranges[1:]):
                if r.intersects(rsub):
                    r = r.merge(rsub)
                    indexes.append(i+1)
            # Delete every range that was merged
            for index in sorted(indexes + [0], reverse=True):
                del ranges[index]
            # If no range was merged, then it doesn't intersect, no need to process it further
            if len(indexes) == 0:
                processed.append(r)
            else:
                ranges = [r] + ranges
            self._log.debug("ranges=%s", ranges)
            self._log.debug("proc  =%s", processed)
            self._log.debug("indexes=%s", indexes)
        self._log.debug(processed)
        return sum([r.span for r in processed])
