from abc import ABC, abstractmethod
import logging
from pathlib import Path
from typing import Any, Callable

class Puzzle(ABC):

    def __init__(self):
        self._log = logging.getLogger(self.__class__.__name__)
        self._parts: list[Callable[[],Any]] = []

    @abstractmethod
    def parse_input(self, input_file: Path):
        """
        Parse input for the puzzle.

        :param input_file: input file to parse
        """

    def start_part(self, part, index, data):
        """
        Runs a given part of a puzzle.

        :param part: list of paths for the puzzle input.
        :param data: any other argument usable by the puzzle.
        :param kwargs: any other argument usable by the part.
        """
        part(data)

    def solve(self, paths: list[str] = [], **kwargs):
        """
        Solves the puzzle.

        :param paths: list of paths for the puzzle input.
        :param kwargs: any other argument usable by the puzzle.
        """
        for path in paths:
            data = self.parse_input(Path(path))
            for i in range(len(self._parts)):
                if "part" not in kwargs or ("part" in kwargs and i+1 == kwargs.get("part", 0)):
                    self.start_part(self._parts[i], i+1, data)
