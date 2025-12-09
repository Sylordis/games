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
        self._log.debug("Ranges=%s", data[0])
        ranges:list[Range] = sorted(data[0], key=lambda f: f.end)
        self._log.debug("Sorted=%s", ranges)
        current:Range = ranges[-1]
        processed = []
        for i in range(len(ranges)-2, -1, -1):
            self._log.debug("%ds current=%s r[i]=%s", i, current, ranges[i])
            if current.intersects(ranges[i]):
                self._log.debug("%dp intersects")
                current = current.merge(ranges[i])
            else:
                processed.append(current)
                current = ranges[i]
                ranges = ranges[:i]
            self._log.debug("%de current=%s", i, current)
        processed.append(current)
        self._log.debug("End=%s", processed)
        return sum([f.span for f in processed])
