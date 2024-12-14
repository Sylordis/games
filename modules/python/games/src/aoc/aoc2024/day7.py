from dataclasses import dataclass
from pathlib import Path
import re
from typing import Any, Callable

from ..puzzle_aoc import PuzzleAOC


@dataclass(frozen=True)
class Operation:
    symbol: str
    operation: Callable[[int,int],int]

    def do(self, a: int, b: int) -> int:
        return self.operation(a, b)


OPERATIONS = [
    Operation("+", lambda a,b: a + b),
    Operation("*", lambda a,b: a * b),
    Operation("||", lambda a,b: int(str(a) + str(b))),
]


class Day7(PuzzleAOC):

    def find_if_possible(self, result, numbers, operations: list[Operation]) -> bool:
        self._log.debug(f"possible? {result}: {numbers}")
        possible = self.find_if_possible_recursive(result, numbers[0], numbers[1:], operations)
        self._log.debug(f"{possible}")
        return possible

    def find_if_possible_recursive(self, result, value, numbers, operations: list[Operation]) -> bool:
        self._log.debug(f"calculate: {result}/{value}: {numbers}")
        if len(numbers) == 0:
            return value == result
        else:
            return any([self.find_if_possible_recursive(result, op.do(value, numbers[0]), numbers[1:], operations) for op in operations])

    def parse_input(self, input_file: Path):
        data = []
        with open(input_file, "r") as file:
            content = file.read().splitlines()
            for line in content:
                data.append(list(map(int,re.split(r'[:\s]+', line))))
        return data

    def part1(self, data):
        self._log.debug(data)
        total = sum([entry[0] for entry in data if self.find_if_possible(entry[0], entry[1:], OPERATIONS[0:2])])
        return total

    def part2(self, data):
        return sum([entry[0] for entry in data if self.find_if_possible(entry[0], entry[1:], OPERATIONS)])
