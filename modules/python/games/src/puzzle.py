from abc import ABC, abstractmethod
import logging
from pathlib import Path

class Puzzle(ABC):

    def __init__(self):
        self._log = logging.getLogger(__name__)

    @abstractmethod
    def parse_input(self, input_file: Path):
        """
        Parse input for the puzzle.

        :param input_file: input file to parse
        """

    @abstractmethod
    def solve(self, paths: list[str] = []):
        """
        Solves the puzzle.

        :param paths: list of paths for the puzzle input.
        """
