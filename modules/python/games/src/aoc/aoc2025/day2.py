from dataclasses import dataclass
from pathlib import Path
import re

from ..puzzle_aoc import PuzzleAOC
from ...utils.inputs import split_strs_to_list


@dataclass
class Range:
    start: int
    end: int

    def __repr__(self):
        return f"{self.start}-{self.end}"


class Day2y2025(PuzzleAOC):

    def check_repeating_pattern_on_whole_pid(self, sid:str, end:int) -> bool:
        if end * 2 > len(sid):
            return False
        else:
            next_corresponds_to_previous = True
            harmonic = 1
            # self._log.debug("check: start=%s l=%i", sid[0], end)
            while end * harmonic < len(sid) and next_corresponds_to_previous:
                h_start = harmonic*end
                h_end = end*(harmonic+1)
                if sid[0:end] != sid [h_start:h_end]:
                    next_corresponds_to_previous = False
                harmonic = harmonic + 1
                # self._log.debug("check: h=%i l=%i s=%i e=%i str=%s", harmonic, end, h_start, h_end, sid[0:h_end])
            return next_corresponds_to_previous


    def parse_input(self, input_file: Path):
        with open(input_file, "r", encoding="utf-8") as file:
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
            for pid in range(r.start, r.end+1):
                sid = str(pid)
                self._log.debug("id: %s |%i|, %s", sid, len(sid), len(sid)//2)
                if len(sid) % 2 == 1:
                    continue
                elif sid[:len(sid)//2] == sid[len(sid)//2:]:
                    invalids_t.append(pid)
                    self._log.debug("Invalid: %s", id)
            self._log.debug("Invalids: %s", invalids_t)
            invalids += invalids_t
        return sum(invalids)

    def part2(self, data):
        invalids:list[int] = []
        for r in data:
            invalids_t:list[int] = []
            self._log.debug("Range: %s", r)
            for pid in range(r.start, r.end+1):
                sid = str(pid)
                for i in range(1, len(sid)//2+1):
                    pattern_end = i
                    if self.check_repeating_pattern_on_whole_pid(sid, pattern_end):
                        invalids_t.append(pid)
                        self._log.debug("Invalid: %s", pid)
                        break
                    else:
                        pattern_end = pattern_end + 1
            self._log.debug("Invalids: %s", invalids_t)
            invalids += invalids_t
        return sum(invalids)
