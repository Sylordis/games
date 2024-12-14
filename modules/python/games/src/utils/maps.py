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
