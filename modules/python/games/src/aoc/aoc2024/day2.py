from pathlib import Path

from ..puzzle_aoc import PuzzleAOC
from ...utils.inputs import Files
from ...utils.lists import Lists


class Day2(PuzzleAOC):

    def parse_input(self, input_file: Path):
        return Files.as_converted_array(input_file, int)

    def is_good_level(self, a:int, b:int, trend:int = 1) -> bool:
        return 1 <= b*trend - a*trend <= 3

    def is_safe(self, report: list[int]) -> bool:
        trend = Lists.get_global_trend(report)
        for i in range(1, len(report)):
            if not self.is_good_level(report[i-1], report[i], trend):
                return False
        return True

    def part1(self, data):
        n_safe = len([report for report in data if self.is_safe(report)])
        return n_safe

    def part2(self, data):
        n_safe = sum([any([self.is_safe(row[:i] + row[i + 1:]) for i in range(len(row))]) for row in data])
        return n_safe
