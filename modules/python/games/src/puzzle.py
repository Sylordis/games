from abc import ABC, abstractmethod
from pathlib import Path

class Puzzle(ABC):

    @abstractmethod
    def parse_input(self, input_file: Path):
        """
        Parse input for the puzzle.

        :param input_file: input file to parse
        """

    @abstractmethod
    def solve(self, paths: list[str] = []):
        """
        Solves the puzzle for both parts for each input.

        :param paths: list of paths
        """
