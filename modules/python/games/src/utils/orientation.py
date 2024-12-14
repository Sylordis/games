from __future__ import annotations
from dataclasses import dataclass


@dataclass(frozen=True)
class Direction:

    repr: str
    "Character representation of the direction."
    delta: tuple[int,int]
    "Delta for coordinates of this direction when having to move."

    @staticmethod
    def hexagonal(chars: str = None) -> list[Direction]:
        """
        Gets all directions of an hexagonal 2D space.

        :param chars: characters for representing the directions, starting from top and clockwise.
        Leave `None` for defaults.
        """
        symbols = chars if chars is not None else ">↘↙<↖↗"
        dirs = [(1,0), (0,1), (-1,1), (-1,0), (0, -1), (1,-1)]
        return [Direction(s,d) for s,d in zip(list(symbols), dirs)]

    @staticmethod
    def square_orthogonal(chars: str = None) -> list[Direction]:
        """
        Gets all orthogonal directions of a square 2D space.

        :param chars: characters for representing the directions, starting from top and clockwise.
        Leave `None` for defaults.
        """
        symbols = chars if chars is not None else "^>v<"
        dirs = [(0,-1), (1,0), (0,1), (-1,0)]
        return [Direction(s,d) for s,d in zip(list(symbols), dirs)]

    @staticmethod
    def square_all(chars: str = None) -> list[Direction]:
        """
        Gets all directions of a square 2D space, orthogonals and diagonals.

        :param chars: characters for representing the directions, starting from top and clockwise.
        Leave `None` for defaults.
        """
        symbols = chars if chars is not None else "^↗>↘v↙<↖"
        dirs = [(0,-1), (1,-1), (1,0), (1,1), (0,1), (-1,1), (-1,0), (-1,-1)]
        return [Direction(s,d) for s,d in zip(list(symbols), dirs)]


class DirectionManager:
    """
    Manages orientations.
    """

    def __init__(self, orientations: list[Direction]):
        self.orientations: list[Direction] = orientations
    
    def rotate(self, orientation: Direction, n: int = 1) -> Direction:
        """
        Rotates an orientation according to the defined orientations.

        :param orientation: orientation to rotate.
        :param n: the number of incremental rotations to performed clockwise, negative for anti-clockwise.
            Default is one rotation clock-wise.
        :return: the new orientation.
        """
        return self.orientations[(self.orientations.index(orientation) + n) % len(self.orientations)]

    def get_direction_from_char(self, char: str) -> Direction | None:
        """
        Gets a direction instance according to it's character representation.

        :param char: character representation for the Orientation.
        :return: the direction corresponding to the character, or None if none is found
        """
        for o in self.orientations:
            if char == o.repr:
                return o
        return None

    @staticmethod
    def hexagonal(chars: str = None) -> DirectionManager:
        """
        Creates a manager for 6 directions in an hexagonal 2D space.

        :param chars: characters for representing the directions, starting from right and clockwise.
        Leave `None` for defaults.
        """
        return DirectionManager(Direction.hexagonal(chars))

    @staticmethod
    def square_orthogonal(chars: str = None) -> DirectionManager:
        """
        Creates a manager for 4 directions in a 2D space (usually orthogonals).

        :param chars: characters for representing the directions, starting from top and clockwise.
        Leave `None` for defaults.
        """
        return DirectionManager(Direction.square_orthogonal(chars))

    @staticmethod
    def square_all(chars: str = None) -> DirectionManager:
        """
        Creates a manager for 8 directions in a 2D space (usually orthogonals and diagonals).

        :param chars: characters for representing the directions, starting from top and clockwise.
        Leave `None` for defaults.
        """
        return DirectionManager(Direction.square_all(chars))


