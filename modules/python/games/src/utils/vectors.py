from __future__ import annotations
import math
from typing import Any


class Vector:
    """
    Class for managing any kind of mathematical vectors. This class expects numbers.

    :param coordinates: each coordinates in the space as a tuple (commonly: x, y, z, ...)
    """

    def __init__(self, *coords):
        """
        Creates a new Vector based on the amount of coordinates provided.
        If `coords` is a tuple or a list, then this argument will be taken as coordinates of the
        Vector, otherwise all the arguments will be taken as coordinates.
        
        :param coords: coordinates of the vector
        """
        if len(coords) > 0 and isinstance(coords[0], list | tuple):
            self._coordinates: list = list(coords[0])
        if len(coords) > 0 and not isinstance(coords[0], list | tuple):
            self._coordinates = list(coords)

    @property
    def coordinates(self):
        "Gets this vector's coordinates."
        return self._coordinates

    @property
    def coords(self):
        "Gets this vector's coordinates."
        return self._coordinates

    @property
    def xyz(self):
        "Gets this vector's coordinates."
        return self._coordinates

    def __add__(self, value: Any) -> Vector:
        if isinstance(value, Vector):
            return self.add_vector(value)
        else:
            return self.add_scalar(value)

    def add_scalar(self, scalar) -> Vector:
        """
        Returns a new vector with every component of this vector added by a scalar factor.
        Done automatically with `Vector + Scalar`.

        :param scalar: Scalar to add.
        """
        return Vector(tuple(e + scalar for e in self.coordinates))

    def add_vector(self, vector: Vector) -> Vector:
        """
        Returns a new vector with every component of being the sum of the components of both
        vectors on their respective axis.
        Done automatically with `Vector + Vector`.

        :param vector: Vector to add.
        """
        return Vector(tuple(sum(x) for x in zip(self.coordinates, vector.coordinates)))

    def distance(self, other:Vector) -> float:
        """
        Calculates the distance between this vector and another, i.e. the square root of the
        squared differences of their respective coordinates. The vectors do not have to have the
        same amount of dimensions.

        :param other: other vector to calculate the distance to/from
        :return: the distance between this and other
        """
        s_coords = self.coordinates + [0] * max(0, len(other.coordinates)-len(self.coordinates))
        o_coords = other.coordinates + [0] * max(0, len(self.coordinates)-len(other.coordinates))
        total = 0
        for i in range(len(s_coords)):
            total += math.pow(s_coords[i] + o_coords[i], 2)
        return math.sqrt(total)

    def __eq__(self, other) -> bool:
        if isinstance(other, tuple):
            return all(a == b for a,b in zip(self.coordinates, other))
        elif isinstance(other, Vector):
            return all(a == b for a,b in zip(self.coordinates, other.coordinates))
        else:
            return False

    def __floordiv__(self, other) -> Vector:
        return Vector(tuple(e // other for e in self.coordinates))

    def __getitem__(self, index):
        return self.coordinates[index]

    def __hash__(self):
        return hash(self.coordinates)

    def __iadd__(self, other: Any) -> Vector:
        if isinstance(other, Vector):
            self._coordinates = tuple(sum(x) for x in zip(self.coordinates, other.coordinates))
        else:
            self._coordinates = tuple(e + other for e in self.coordinates)

    def __isub__(self, other: Any) -> Vector:
        if isinstance(other, Vector):
            self._coordinates = tuple(sum(x) for x in zip(self.coordinates, (-other).coordinates))
        else:
            self._coordinates = tuple(e - other for e in self.coordinates)

    def __idiv__(self, other: Any) -> Vector:
        self._coordinates = tuple(e / other for e in self.coordinates)

    def __ifloordiv__(self, other: Any) -> Vector:
        self._coordinates = tuple(e // other for e in self.coordinates)

    def __imult__(self, other: Any) -> Vector:
        self._coordinates = tuple(e * other for e in self.coordinates)

    def __invert__(self) -> Vector:
        return self.__neg__()

    def __iter__(self):
        return iter(self.coordinates)

    def __ne__(self, other) -> bool:
        if isinstance(other, tuple):
            return any(a != b for a,b in zip(self.coordinates, other))
        elif isinstance(other, Vector):
            return any(a != b for a,b in zip(self.coordinates, other.coordinates))
        else:
            return True

    def __neg__(self) -> Vector:
        return Vector(tuple(-e for e in self.coordinates))

    def __sub__(self, other) -> Vector:
        if isinstance(other, Vector):
            return self.add_vector(-other)
        else:
            return self.add_scalar(-other)

    def __mul__(self, other) -> Vector:
        return self.mul_scalar(other)

    def mul_scalar(self, value) -> Vector:
        """
        Returns a new vector with every component of this vector multiplied by a scalar factor.
        Done automatically with `Vector * Scalar`.

        :param value: Scalar to multiply with
        """
        return Vector(tuple([e * value for e in self.coordinates]))

    def __repr__(self):
        return f"Vector{self.coordinates}"

    def __str__(self):
        return f"{self.coordinates}"

    def __truediv__(self, other) -> Vector:
        return Vector(tuple(e / other for e in self.coordinates))
