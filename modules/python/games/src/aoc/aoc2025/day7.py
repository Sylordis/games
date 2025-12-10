from enum import StrEnum

from ..puzzle_aoc import PuzzleAOC


class PosType(StrEnum):
    START = "S"
    SPLITTER = "^"
    BEAM = "|"
    EMPTY = "."


class Day2025n07(PuzzleAOC):

    def get_start_position(self, line:str) -> int:
        return line.index(PosType.START)
    
    def light_show(self, data, parallel = False) -> tuple[list[int],int]:
        splits = 0
        status = [0] * len(data[0])
        status[self.get_start_position(data[0])] = 1
        self._log.debug("Status=%s", status)
        for line in data[1:]:
            new_status = [0] * len(status)
            for i,v in enumerate(line):
                if status[i] > 0 and v == PosType.SPLITTER:
                    new_status[i-1] = new_status[i-1] + status[i] if parallel else 1
                    new_status[i] = 0
                    new_status[i+1] = new_status[i+1] + status[i] if parallel else 1
                    splits += 1
                else:
                    new_status[i] = new_status[i] + status[i]
            status = new_status
            self._log.debug("Status=%s, splits=%d", status, splits)
        return (status, splits)

    def part1(self, data) -> int:
        _, splits = self.light_show(data)
        return splits

    def part2(self, data) -> int:
        status, _ = self.light_show(data, True)
        return sum(status)
