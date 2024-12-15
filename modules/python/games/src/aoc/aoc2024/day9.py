from dataclasses import dataclass
from pathlib import Path

from ..puzzle_aoc import PuzzleAOC


@dataclass
class DiskItem:
    is_file: bool
    id: int
    size: int
    remaining: int = 0
    processed: bool = False

    def __repr__(self):
        return "".join([str(self.id)]*self.size) if self.is_file else "".join(["."] * self.size)

class Day9(PuzzleAOC):

    def parse_input(self, input_file: Path):
        data = []
        with open(input_file, "r") as file:
            data = list(map(int, list(file.readline().strip())))
        return data

    def create_disk_entries(self, data) -> list[DiskItem]:
        disk: list[DiskItem] = []
        is_file = True
        file_index = 0
        for i in range(len(data)):
            disk.append(DiskItem(is_file, file_index, data[i], data[i]))
            if is_file:
                file_index += 1
            is_file = not is_file
        return disk

    def part1(self, data):
        self._log.debug(data)
        disk: list[DiskItem] = self.create_disk_entries(data)
        files = [f for f in disk if f.is_file]
        gaps = [f for f in disk if not f.is_file]
        self._log.debug(f"disk={disk}")
        self._log.debug(f"files={files}")
        self._log.debug(f"gaps={gaps}")
        checksum = 0
        curr_file, back_file = None, None
        curr_gap = None
        write_file = True
        index = 0
        while len(files) > 0:
            if write_file:
                # Write file normally as they are on the disk
                self._log.debug("write file")
                curr_file, files = files[0], files[1:]
                self._log.debug(f"file={curr_file}")
                for _ in range(curr_file.remaining):
                    checksum += index * curr_file.id
                    index += 1
                write_file = False
            else:
                if len(gaps) > 0:
                    self._log.debug("write gap")
                    # Fill next gap with last file
                    curr_gap, gaps = gaps[0], gaps[1:]
                    self._log.debug(f"gap={curr_gap}")
                    for _ in range(curr_gap.size):
                        if not back_file:
                            back_file, files = files[-1], files[:-1]
                            self._log.debug(f"filler={back_file}")
                        checksum += index * back_file.id
                        back_file.remaining += -1
                        if back_file.remaining == 0:
                            back_file = None
                        index += 1
                    self._log.debug(f"filler={back_file}")
                    write_file = True

        # Write the last of back file
        if back_file and back_file.remaining > 0:
            for _ in range(back_file.remaining):
                checksum += index * back_file.id
                back_file.remaining += -1
                index += 1
        return checksum

    def part2(self, data):
        # Absolutely not optimized, do not run this or good luck
        disk: list[DiskItem] = self.create_disk_entries(data)
        checksum : int = 0
        self._log.debug(data)
        while True:
            self._log.debug("".join([str(f) for f in disk]))
            unprocessed_files = [i for i, f in enumerate(disk) if not f.processed and f.is_file]
            if len(unprocessed_files) % 100 == 0:
                print(f"unprocessed={len(unprocessed_files)}")
            self._log.debug(f"unprocessed={unprocessed_files}")
            if len(unprocessed_files) == 0:
                break
            file_index = unprocessed_files[-1]
            disk[file_index].processed = True
            fitting_gaps = [i for i, gap in enumerate(disk[:file_index]) if not gap.is_file and gap.size >= disk[file_index].size]
            self._log.debug(f"gaps={fitting_gaps}")
            if len(fitting_gaps) > 0:
                gap_index = fitting_gaps[0]
                if disk[gap_index].size == disk[file_index]:
                    disk.pop(gap_index)
                else:
                    disk[gap_index].size -= disk[file_index].size
                file = disk.pop(file_index)
                disk.insert(gap_index, file)
                disk.insert(file_index, DiskItem(False, -1, file.size))
        index = 0
        self._log.debug("".join([str(f) for f in disk]))
        for f in disk:
            if f.is_file:
                for i in range(f.size):
                    checksum += f.id * (index + i)
            index += f.size
        return checksum
