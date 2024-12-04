from typing import Any

def distance(a: Any, b: Any) -> int | float:
    """
    Calculates the distance between 2 objects, A and B.

    :param a: object A
    :param b: object B
    :return: the distance between the two.
    """
    return abs(b - a)
