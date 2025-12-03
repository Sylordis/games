from dataclasses import dataclass
import math

from ..puzzle_aoc import PuzzleAOC


STARTING_INDEX:int = 50


class Day1y2025(PuzzleAOC):

    def parse_rotation(self, r) -> int:
        amount = int(r[1:])
        direction = -1 if r[0] == "L" else 1
        return amount * direction

    def part1(self, data):
        i:int = STARTING_INDEX
        times = 0
        for rotation in data:
            i = i + self.parse_rotation(rotation)
            if i % 100 == 0:
                times = times + 1
            self._log.debug("%s => %s", rotation, i % 100)
        return times

    def part2(self, data):
        prev:int = STARTING_INDEX
        current:int = STARTING_INDEX
        clicks = 0
        for rotation_input in data:
            prev = current
            rotation = self.parse_rotation(rotation_input)
            current = prev + rotation
            if rotation > 0:
                clicks = clicks + current // 100
            if rotation < 0:
                if prev != 0:
                    clicks = clicks + 1
                clicks = clicks + (abs(rotation) - prev) // 100
            current = current % 100
            self._log.debug("%s: %s => %s [%d]", rotation_input, prev, current, clicks)
        return clicks
