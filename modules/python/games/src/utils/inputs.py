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
    """
    Utilities class for reading file inputs and converting their data.
    """

    @staticmethod
    def as_converted_array(file: Path, separator: str = ' ', converter: Callable[[],Any] = lambda x: x) -> list[list[Any]]:
        """
        Takes a file containing values separated by spaces and return an array of converted values, splitting each line according to a separator.
        
        :param file: Input file
        :param separator: separator between each value, default is whitespace
        :param converter: value converter for each value, default is identity
        :return: a 2D array of values
        """
        data = []
        with open(file, "r") as ifile:
            data = [list(map(converter, line.split(separator))) for line in ifile.read().splitlines()]
        return data

    @staticmethod
    def as_str_array(file: Path) -> list[list[str]]:
        """
        Takes a file and converts all lines to a 2D array of strings, where each character is an element.

        Example:
        ```
        abc
        def
        ```
        will be converted to:
        ```
        [["a","b","c"],["d","e","f"]]
        ```

        :param file: Input file
        :return: a 2D string array
        """
        data = []
        with open(file, "r") as ifile:
            data = [list(line) for line in ifile.read().splitlines()]
        return data
