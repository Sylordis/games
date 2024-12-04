from collections import Counter
from pathlib import Path

from ..puzzle_aoc import PuzzleAOC
from ...utils.inputs import split_strs_to_list
from ...utils.geometry import distance


class Day1(PuzzleAOC):

    def parse_input(self, input_file: Path):
        with open(input_file, "r") as file:
            data = split_strs_to_list("   ", file.read().splitlines(), int)
            return data

    def part1(self, data):
        la, lb = data
        la.sort()
        lb.sort()
        s = sum([distance(int(a), int(b)) for a, b in zip(la, lb)])
        return s

    def part2(self, data):
        la, lb = data
        count = Counter(lb)
        return sum([i * count.get(i, 0) for i in la])
