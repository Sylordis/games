from typing import Any

class MapUtils:

    @staticmethod
    def map_as_str(arr, elements: dict[tuple[int,int],str] = {}) -> str:
        """
        Transforms a 2D string array into a string, each line being joined with a new line.

        :param map: the 2D array.
        :return: the string representation.
        """
        new_arr = arr
        if type(arr[0]) is str:
            new_arr = [list(line) for line in arr]
        for k,v in elements.items():
            new_arr[k[1]][k[0]] = v
        for i in range(len(arr[0])):
            new_arr[i] = "".join(arr[i])
        return "\n".join(new_arr)

    @staticmethod
    def is_in_map(map: list[list[Any]], coordinates) -> bool:
        """
        Checks if given coordinates are in the given map, e.g. coordinates are within the bounds of lines and rows.

        :param map: map to check
        :param coordinates: (x,y[,_]) coordinates as iterable
        :return: True if the coordinates are within the map, False otherwise
        """
        x,y,*rest = coordinates
        return 0 <= y < len(map) and 0 <= x < len(map[0])
