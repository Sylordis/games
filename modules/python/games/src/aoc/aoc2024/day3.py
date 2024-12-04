from pathlib import Path
import re

from ..puzzle_aoc import PuzzleAOC
from ...utils.inputs import Files


class Day3(PuzzleAOC):

    def __init__(self):
        super().__init__()

    def parse_input(self, input_file: Path):
        data = ""
        with open(input_file, "r") as file:
            data = "".join(file.readlines())
        return data

    def part1(self, data):
        numbers = [[int(i) for i in match] for match in re.findall(r"mul\(([0-9]{1,3}),([0-9]{1,3})\)", data)]
        return sum([l[0] * l[1] for l in numbers])

    def part2(self, data):
        do = True
        it = re.finditer(r"mul\(([0-9]{1,3}),([0-9]{1,3})\)|do\(\)|don't\(\)", data)
        expr = []
        while (match := next(it, None)) is not None:
            if re.fullmatch(r"do\(\)", match.group()):
                do = True
            elif re.fullmatch(r"don't\(\)", match.group()):
                do = False
            elif do:
                expr.append([int(i) for i in list(match.groups())])
        return sum([l[0] * l[1] for l in expr])
