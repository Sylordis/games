from enum import StrEnum
import copy

from ..puzzle_aoc import PuzzleAOC
from ...utils.maps import MapUtils
from ...utils.orientation import Direction


class PosType(StrEnum):
    PAPER_ROLL = "@"
    EMPTY = "."


class Day2025n04(PuzzleAOC):

    def __init__(self):
        super().__init__()
        self.dirs: list[Direction] = Direction.square_all()

    def is_accessible(self, data, x, y):
        adjacent_rolls = 0
        for d in self.dirs:
            nx, ny = x + d.vector[0], y + d.vector[1]
            if MapUtils.is_in_map(data, (nx, ny)) and data[ny][nx] == PosType.PAPER_ROLL:
                adjacent_rolls = adjacent_rolls + 1
        return adjacent_rolls < 4

    def check_accessibles(self, data) -> tuple[int,list[str]]:
        accessible:int = 0
        data_check = copy.deepcopy(data)
        for y, yd in enumerate(data):
            for x, xd in enumerate(yd):
                if xd == PosType.PAPER_ROLL:
                    if self.is_accessible(data, x, y):
                        accessible = accessible + 1
                        data_check[y] = data_check[y][0:x] + "x" + data_check[y][x+1:]
        return accessible, data_check

    def part1(self, data):
        return self.check_accessibles(data)[0]

    def part2(self, data):
        total_accessible: int = 0
        have_accessibles = True
        ndata = data
        while have_accessibles:
            accessibles, ndata = self.check_accessibles(ndata)
            have_accessibles = accessibles > 0
            total_accessible = total_accessible + accessibles
        return total_accessible
