from functools import reduce
import operator
from pathlib import Path

from ..puzzle_aoc import PuzzleAOC


class Day2025n06(PuzzleAOC):

    def calculate(self, symbol:str, data:list[str]):
        result = 0
        op = None
        if symbol == "+":
            op = operator.add
        elif symbol == "*":
            op = operator.mul
        if op:
            result = reduce(op, list(map(int, data)))
        return result

    def part1(self, data) -> int:
        pdata = [line.split() for line in data]
        return sum([self.calculate(d[-1], d[:-1]) for d in list(zip(*pdata))])

    def part2(self, data) -> int:
        total = 0
        self._log.debug(data)
        pdata = []
        for line in data:
            pdata.append(list(line))
        self._log.debug(pdata)
        pdata = list(reversed(list(zip(*pdata))))
        self._log.debug(pdata)
        last_i = 0
        for i,e in enumerate(pdata):
            if all(v is " " for v in e):
                last_i += 1
            elif e[-1] in ("*", "+"):
                symbol = e[-1]
                splice = list(map(lambda v: "".join(v).strip(), pdata[last_i:i])) + ["".join(pdata[i][:-1]).strip()]
                last_i = i+1
                self._log.debug("symbol=%s splice=%s", symbol, splice)
                total += self.calculate(symbol, splice)
        return total
