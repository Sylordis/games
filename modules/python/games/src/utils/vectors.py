from __future__ import annotations
from typing import Any

class Vector:

    def __init__(self, coordinates: tuple):
        self._coordinates: tuple = coordinates

    @property
    def coordinates(self):
        return self._coordinates

    @property
    def coords(self):
        return self._coordinates

    @property
    def xyz(self):
        return self._coordinates

    def __add__(self, value: Any) -> Vector:
        if isinstance(value, Vector):
            return self.add_vector(value)
        else:
            return self.add_scalar(value)

    def add_scalar(self, scalar) -> Vector:
        return Vector(tuple(e + scalar for e in self.coordinates))

    def add_vector(self, vector: Vector) -> Vector:
        return Vector(tuple(sum(x) for x in zip(self.coordinates, vector.coordinates)))

    def __eq__(self, other) -> bool:
        if isinstance(other, tuple):
            return all(a == b for a,b in zip(self.coordinates, other))
        elif isinstance(other, Vector):
            return all(a == b for a,b in zip(self.coordinates, other.coordinates))
        else:
            return False

    def __floordiv__(self, other) -> Vector:
        return Vector(tuple(e // other for e in self.coordinates))

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
        return Vector(tuple([e * value for e in self.coordinates]))

    def __repr__(self):
        return f"Vector{self.coordinates}"

    def __str__(self):
        return f"{self.coordinates}"

    def __truediv__(self, other) -> Vector:
        return Vector(tuple(e / other for e in self.coordinates))
