from abc import ABC, abstractmethod
from pathlib import Path

from ..puzzle import Puzzle

class PuzzleAOC(Puzzle, ABC):

    @abstractmethod
    def part1(self, data):
        """Solve part 1"""

    @abstractmethod
    def part2(self, data):
        """Solve part 2"""

    def solve(self, paths: list[str] = []):
        """
        Solves the puzzle for both parts for each input.

        :param paths: list of paths
        """
        for path in paths:
            data = self.parse_input(Path(path))
            solution_p1 = self.part1(data)
            solution_p2 = self.part2(data)
            print(f"Part 1: {solution_p1}")
            print(f"Part 2: {solution_p2}")
