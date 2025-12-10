import pytest

from utils.vectors import Vector


@pytest.mark.parametrize("expected_coords,vector",[
    ([0], Vector(0)),
    ([0], Vector((0))),
    ([0,0], Vector(0,0)),
    ([0,0], Vector((0,0))),
    ([0,0], Vector([0,0])),
    ([1,2,3,4], Vector(1,2,3,4)),
    ([1,2,3,4], Vector([1,2,3,4])),
])
def test_builder(expected_coords, vector):
    assert vector.coordinates == expected_coords

@pytest.mark.parametrize("expected,a,b", [
    ((0,0), (0,0), Vector((0,0))),
    ((1,2), (0,0), Vector((1,2))),
    ((1,2), (1,2), Vector((0,0))),
    ((4,8), (2,8), Vector((2,0))),
    ((-8,-4), (4,6), Vector((-12,-10))),
    ((1,2,3), (1,2,1), Vector((0,0,2))),
    ((9,7,8), (9,7,8), 0),
    ((9,7,8), (6,4,5), 3),
])
def test_add(expected: tuple, a: tuple, b):
    va = Vector(a)
    v = va + b
    assert v == expected


@pytest.mark.parametrize("expected,a,b", [
    (0, Vector(0,0),Vector(0,0)),
    (0, Vector(0,0),Vector(0,0,0,0)),
    (1, Vector(1), Vector(0)),
    (45, Vector(48), Vector(-3)),
    (15, Vector(-5), Vector(10)),
])
def test_distance(expected, a:Vector, b:Vector):
    assert a.distance(b) == expected

@pytest.mark.parametrize("a,b", [
    ((0,0),Vector((0,0))),
    ((12,48),Vector((12,48))),
    ((5,6),Vector((5,6))),
    ((0,0),(0,0)),
    ((12,48),(12,48)),
    ((5,6),(5,6)),
])
def test_eq(a, b):
    assert Vector(a) == b


@pytest.mark.parametrize("expected,coords,multiplier", [
    ((0,0), (0,0), 4329),
    ((0,0), (3213,48), 0),
    ((3,3), (1,1), 3),
    ((60,50),(6,5),10)
])
def test_mul(expected: tuple, coords: tuple, multiplier):
    v = Vector(coords) * multiplier
    assert v == expected


@pytest.mark.parametrize("a,b", [
    ((0,0),Vector((1,546))),
    ((1,2),Vector((1,3))),
    ((5,6),Vector((6,5))),
    ((0,0),(1,1)),
    ((8,65),(12,48)),
    ((0,-1),(0,1)),
    ((0,-1),4),
])
def test_ne(a, b):
    assert Vector(a) != b


@pytest.mark.parametrize("expected,a", [
    ((0,0), (0,0)),
    ((-4,-9), (4,9)),
    ((2,-7,23), (-2,7,-23))
])
def test_neg(expected: tuple, a: tuple):
    v = -Vector(a)
    assert v == expected


@pytest.mark.parametrize("expected,a,b", [
    ((0,0), (0,0), Vector((0,0))),
    ((-1,-2), (0,0), Vector((1,2))),
    ((1,2), (1,2), Vector((0,0))),
    ((0,8), (2,8), Vector((2,0))),
    ((16,16), (4,6), Vector((-12,-10))),
    ((1,2,-1), (1,2,1), Vector((0,0,2))),
])
def test_sub(expected: tuple, a: tuple, b):
    v = Vector(a) - b
    assert v == expected
