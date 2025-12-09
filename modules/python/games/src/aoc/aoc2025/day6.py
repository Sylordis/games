from functools import reduce
import operator
from pathlib import Path

from ..puzzle_aoc import PuzzleAOC


class Day2025n06(PuzzleAOC):

    def calculate(self, symbol:str, data:list[str]):
        result = 0
        op = None
        if symbol == "+":
            op = operator.add
        elif symbol == "*":
            op = operator.mul
        if op:
            result = reduce(op, list(map(int, data)))
        return result

    def parse_input(self, input_file:Path):
        data = []
        with open(input_file, "r", encoding="utf-8") as file:
            for line in file.readlines():
                data.append(line.split())
        return data

    def part1(self, data) -> int:
        return sum([self.calculate(d[-1], d[:-1]) for d in list(zip(*data))])

    def part2(self, data) -> int:
        pass
