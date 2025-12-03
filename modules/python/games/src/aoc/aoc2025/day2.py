from dataclasses import dataclass
from pathlib import Path

from ..puzzle_aoc import PuzzleAOC
from ...utils.inputs import split_strs_to_list


@dataclass
class Range:
    start: int
    end: int

    def __repr__(self):
        return f"{self.start}-{self.end}"

class Day2y2025(PuzzleAOC):

    def parse_input(self, input_file: Path):
        with open(input_file, "r") as file:
            raw = file.readlines()[0].split(",")
            data = []
            for d in raw:
                splits = d.split("-")
                data.append(Range(int(splits[0]), int(splits[1])))
            return data

    def part1(self, data):
        invalids:list[int] = []
        for r in data:
            invalids_t:list[int] = []
            self._log.debug("Range: %s", r)
            for id in range(r.start, r.end+1):
                sid = str(id)
                # self._log.debug("id: %s |%i|, %s", sid, len(sid), len(sid)//2)
                if len(sid) % 2 == 1:
                    continue
                elif sid[:len(sid)//2] == sid[len(sid)//2:]:
                    invalids_t.append(id)
                    # self._log.debug("Invalid: %s", id)
            self._log.debug("Invalids: %s", invalids_t)
            invalids += invalids_t
        return sum(invalids)

    def part2(self, data):
        return 0
