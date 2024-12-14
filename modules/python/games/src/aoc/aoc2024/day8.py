from dataclasses import dataclass
import itertools
from pathlib import Path

from ..puzzle_aoc import PuzzleAOC
from ...utils.inputs import Files
from ...utils.maps import MapUtils
from ...utils.vectors import Vector


class Day8(PuzzleAOC):

    def parse_input(self, input_file: Path):
        return Files.as_str_array(input_file)

    def get_antennas(self, data) -> dict[str, Vector]:
        antennas: dict[str, Vector] = {}
        for y in range(len(data)):
            for x in range(len(data[0])):
                if data[y][x] != ".":
                    if data[y][x] not in antennas:
                        antennas[data[y][x]] = []
                    antennas[data[y][x]].append(Vector((x,y)))
        return antennas

    def calculate_antinodes(self, data, start, vector, harmonics=False):
        antinodes: set[Vector] = set()
        self._log.debug(f"  direction: start={start}, v={vector}")
        harmonic = 0 if harmonics else 1
        while MapUtils.is_in_map(data, (pos := start + vector * harmonic)):
            self._log.debug(f"    harmonic={harmonic}, pos={pos}")
            antinodes |= {Vector(pos)}
            self._log.debug(f"    antinodes={antinodes}")
            harmonic += 1
            if not harmonics:
                break
        return antinodes

    def find_antinodes(self, data, harmonics=False) -> set[Vector]:
        all_antennas = self.get_antennas(data)
        self._log.debug(all_antennas)
        all_antinodes = set()
        for frequency in all_antennas.keys():
            self._log.debug(frequency)
            for elements in itertools.product(all_antennas[frequency], all_antennas[frequency]):
                a,b = elements
                if a != b:
                    self._log.debug(f"elements={elements}")
                    for v in [(a, a-b), (b, b-a)]:
                        all_antinodes |= self.calculate_antinodes(data, v[0], v[1], harmonics)
                    self._log.debug(f"all antinodes={all_antinodes}")
        return all_antinodes

    def part1(self, data):
        return len(self.find_antinodes(data))

    def part2(self, data):
        return len(self.find_antinodes(data, True))
