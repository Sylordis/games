from abc import ABC, abstractmethod
from pathlib import Path

from ..puzzle import Puzzle


class PuzzleAOC(Puzzle, ABC):

    def __init__(self):
        super().__init__()
        self._parts = [self.part1, self.part2]

    @abstractmethod
    def part1(self, data):
        """Solve part 1"""

    @abstractmethod
    def part2(self, data):
        """Solve part 2"""

    def start_part(self, part, index, data):
        solution_part = part(data)
        print(f"Part {index}: {solution_part}")
