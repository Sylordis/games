from copy import deepcopy
from dataclasses import dataclass
import numpy as np
from pathlib import Path

from ..puzzle_aoc import PuzzleAOC
from ...utils.inputs import Files
from ...utils.maps import MapUtils
from ...utils.orientation import Direction, DirectionManager
from ...utils.vectors import Vector


@dataclass(frozen=True)
class TrailStep:
    pos: Vector
    elevation: int


class Day10(PuzzleAOC):

    def parse_input(self, input_file: Path):
        return [[int(e) for e in line] for line in Files.as_str_array(input_file)]

    def get_trailheads(self, data) -> list[TrailStep]:
        trailheads = []
        for y in range(len(data)):
            for x in range(len(data[y])):
                if data[y][x] == 0:
                    trailheads += [TrailStep(Vector((x,y)), 0)]
        return trailheads

    def find_trail_tail(self, data, last_step: TrailStep):
        if last_step is None:
            return []
        x,y,*rest = last_step.pos.xyz
        print(f"{last_step} = {data[y][x]}")
        if data[y][x] == 9:
            print("End")
            return last_step
        else:
            trails = []
            for d in Direction.square_orthogonal():
                npos = last_step.pos + Vector(d.delta)
                xn,yn,*restn = npos.xyz
                if MapUtils.is_in_map(data, (xn,yn)) and data[yn][xn] == data[y][x] + 1:
                    trails.append(self.find_trail_tail(data, TrailStep(npos,data[yn][xn])))
            return trails

    def part1(self, data):
        print(data)
        trailheads = self.get_trailheads(data)
        print(trailheads)
        total_score = 0
        for trailhead in trailheads[0:1]:
            trails = self.find_trail_tail(data, trailhead)
            print(trails)
            total_score += len(trails)
            print(f"score={len(trails)} / total={total_score}")
        return total_score

    def part2(self, data):
        return
