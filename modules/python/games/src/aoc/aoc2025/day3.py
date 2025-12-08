from ..puzzle_aoc import PuzzleAOC


class Day20251203(PuzzleAOC):

    def parse_input(self, input_file):
        data = []
        with open(input_file, "r", encoding="utf-8") as file:
            # data = file.read().splitlines()
            for line in file:
                line_data = []
                for e in enumerate(line):
                    try:
                        line_data.append(int(e[1]))
                    except ValueError:
                        break
                data.append(line_data)
        return data

    def get_earliest_result_index(self, battery_size:int, battery_index:int, size:int = 2) -> int:
        return max(0, size - battery_size + battery_index)

    def find_highest_number(self, batteries:list[int], size:int = 2) -> int:
        output:list[int] = [0] * size
        self._log.debug("Check: %s", batteries)
        for battery_index, joltage in enumerate(batteries):
            min_index = self.get_earliest_result_index(len(batteries), battery_index, size)
            for i in range(min_index, len(output)):
                # If current value is < than joltage, replace
                # and reset all other joltages by 0s (to avoid pollution)
                if joltage > output[i]:
                    output[i] = joltage
                    for j in range (i+1, len(output)):
                        output[j] = 0
                    break
            self._log.debug("Step: %s", output)
        return int("".join([str(r) for r in output]))

    def part1(self, data):
        return sum([self.find_highest_number(d) for d in data])

    def part2(self, data):
        return sum([self.find_highest_number(d, 12) for d in data])
