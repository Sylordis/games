from pathlib import Path

from ..puzzle_aoc import PuzzleAOC


class Day5(PuzzleAOC):

    def __init__(self):
        super().__init__()
        self.dependencies: list[tuple[int,int]] = []
        self.updates: list[list[int]] = []

    def parse_input(self, input_file: Path):
        with open(input_file, "r") as file:
            dependencies_part = True
            for line in file:
                if line.strip() == "":
                    dependencies_part = False
                elif dependencies_part:
                    self.dependencies.append(tuple(map(int, line.strip().split('|'))))
                else:
                    self.updates.append(list(map(int,line.strip().split(","))))
        self._log.debug(self.dependencies)
        return None

    def is_update_correct(self, update: list[int]) -> bool:
        for i in range(len(update)):
            applicable_dependencies = [d for d in self.dependencies if d[1] == update[i] and d[0] in update]
            if not all(d in update[:i] for d,e in applicable_dependencies):
                return False
        return True

    def reorder_update(self, update: list[int]) -> list[int]:
        while not self.is_update_correct(update):
            for i in range(len(update)):
                applicable_dependencies = [d for d in self.dependencies if d[1] == update[i] and d[0] in update]
                broken_dependencies = [(d,p) for d,p in applicable_dependencies if d not in update[:i]]
                if len(broken_dependencies) > 0:
                    self._log.debug(f"reordering: {broken_dependencies}")
                    for broken_dependency in broken_dependencies:
                        update.insert(update.index(broken_dependency[1]), update.pop(update.index(broken_dependency[0])))
        return update

    def part1(self, data):
        total = 0
        for update in self.updates:
            if self.is_update_correct(update):
                total += update[int(len(update)/2)]
        return total

    def part2(self, data):
        total = 0
        for update in self.updates:
            if not self.is_update_correct(update):
                self._log.debug(f"wrong:     {update}")
                update = self.reorder_update(update)
                self._log.debug(f"reordered: {update}")
                total += update[int(len(update)/2)]
        return total
