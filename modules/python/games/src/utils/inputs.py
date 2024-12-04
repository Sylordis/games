from pathlib import Path
from typing import Callable, Any


def split_strs_to_list(delim: str, data: list[str], converter: Callable[[],Any] = None) -> list:
    """
    Converts a list of string into multiple lists.

    :param delim: delimiter, use r"pattern" for regex split
    :param data: the list of string to split
    :return: lists of strings according to split
    """
    converter = converter if converter else lambda x: x
    first_split=[list(map(converter, s.split(delim))) for s in data]
    return list(map(list, zip(*first_split)))

class Files:

    @staticmethod
    def lines_as_lists(file: Path, converter: Callable[[],Any] = lambda x: x) -> list[list[Any]]:
        data = []
        with open(file, "r") as ifile:
            data = [list(map(converter, line.split(' '))) for line in ifile.read().splitlines()]
        return data
