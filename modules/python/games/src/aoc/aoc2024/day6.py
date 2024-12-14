from copy import deepcopy
from dataclasses import dataclass
from typing import TypeAlias
from pathlib import Path

from ..puzzle_aoc import PuzzleAOC
from ...utils.orientation import Direction, DirectionManager
from ...utils.tuples import Tuples


Position: TypeAlias = tuple[int,int]
SeenArray : TypeAlias = set[tuple[Position, Direction]]


class LoopError(Exception):
    pass

class Day6(PuzzleAOC):

    def __init__(self):
        super().__init__()
        self.compass = DirectionManager.square_orthogonal()

    def is_blocking(self, char) -> bool:
        return char == "#"

    def find_guard(self, data) -> tuple[int,int]:
        pos = (-1,-1)
        for y in range(len(data)):
            for x in range(len(data[y])):
                if data[y][x] == "^":
                    pos = (x,y)
                    break
        return pos

    def parse_input(self, input_file: Path):
        with open(input_file, "r") as file:
            data = file.read().splitlines()
        return data

    def is_on_map(self, pos: tuple[int,int], data) -> bool:
        x,y = pos
        return 0 <= y < len(data) and 0 <= x < len(data[0])

    def walk(self, data, start, dir) -> SeenArray:
        pos = start
        seen = set()
        seen |= {(pos, dir)}
        # self._log.debug(pos)
        # self._log.debug(f"\n{MapUtils.map_as_str(data)}")
        # self._log.debug("")
        while self.is_on_map(pos, data):
            try:
                next_pos = Tuples.add(pos, dir.delta)
                # self._log.debug(f"next = {next_pos}")
                if self.is_blocking(data[next_pos[1]][next_pos[0]]):
                    dir = self.compass.rotate(dir)
                    # self._log.debug(f"Turn: {pos}")
                elif (next_pos, dir) in seen:
                    raise LoopError
                else:
                    pos = next_pos
                    if self.is_on_map(pos, data):
                        seen |= {(pos, dir)}
                        # self._log.debug(f"{pos}, {dir}, {len({p for p,_ in seen})}")
            except IndexError:
                break
        return seen

    def part1(self, data):
        start: tuple[int,int] = self.find_guard(data)
        dir: Direction = self.compass.get_direction_from_char("^")
        seen = self.walk(data, start, dir)
        return len({p for p,_ in seen})

    def part2(self, data):
        start: tuple[int,int] = self.find_guard(data)
        dir: Direction = self.compass.get_direction_from_char("^")
        added_obstacles: set[Position] = set()
        for y in range(len(data)):
            for x in range(len(data[0])):
                self._log.debug(f"{(x,y)}")
                new_data = deepcopy(data)
                if data[y][x] != '#':
                    new_data[y] = data[y][:x] + '#' + data[y][x+1:]
                    try:
                        self.walk(new_data, start, dir)
                    except LoopError:
                        added_obstacles |= {(x,y)}
                        self._log.debug(f"Loop found")
        self._log.debug(added_obstacles)
        return len(added_obstacles)
