from pathlib import Path
import itertools

from ..puzzle_aoc import PuzzleAOC


class Day4(PuzzleAOC):

    def __init__(self):
        super().__init__()
        self.data = []

    def parse_input(self, input_file: Path):
        with open(input_file, "r") as file:
            self.data = file.read().splitlines()
        return self.data

    def find_word(self, x_start: int, y_start: int, direction: tuple[int, int], word: str) -> bool:
        for i in range(len(word)):
            try:
                y = y_start + direction[1] * i
                x = x_start + direction[0] * i
                if x < 0 or y < 0 or self.data[y][x] != word[i]:
                    raise IndexError
            except IndexError:
                return 0
        return 1

    def part1(self, data):
        total = 0
        directions = list(itertools.product([-1, 0, 1], [-1, 0, 1]))
        for y in range(len(data)):
            for x in range(len(data[0])):
                if data[y][x] == "X":
                    for direction in directions:
                        total += 1 if self.find_word(x, y, direction, "XMAS") else 0
        return total

    def part2(self, data):
        total = 0
        directions1 = [(-1,-1),(1,1)]
        directions2 = [(1,-1),(-1,1)]
        fnc = lambda x,y,dx,dy: self.find_word(x+dx,y+dy,(-dx,-dy), "MAS")
        for y in range(len(data)):
            for x in range(len(data[0])):
                if data[y][x] == "A":
                    total += (
                        1
                        if any([fnc(x,y,dx,dy) for dx,dy in directions1]) and any([fnc(x,y,dx,dy) for dx,dy in directions2])
                        else 0
                    )
        return total
