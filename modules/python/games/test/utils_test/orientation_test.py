import pytest

from utils.orientation import Direction, DirectionManager


def test_direction__statics():
    assert 4 == len(Direction.square_orthogonal())
    assert 6 == len(Direction.hexagonal())
    assert 8 == len(Direction.square_all())


def test_direction_manager__statics():
    assert 4 == len(DirectionManager.square_orthogonal().orientations)
    assert 6 == len(DirectionManager.hexagonal().orientations)
    assert 8 == len(DirectionManager.square_all().orientations)


@pytest.mark.parametrize("expected,base,nturns", [
    (1, 0, 1),
    (2, 0, 2),
    (3, 0, 3),
    (0, 0, 4),
    (3, 2, 5),
])
def test_direction_manager__rotate(expected: int, base: int, nturns: int):
    mgr = DirectionManager.square_orthogonal()
    assert mgr.orientations[expected] == mgr.rotate(mgr.orientations[base], nturns)


@pytest.mark.parametrize("expected,symbols,char", [
    (0, None, "^"),
    (1, None, ">"),
    (2, None, "v"),
    (3, None, "<"),
    (2, "abcd", "c"),
])
def test_direction_manager__get_direction_from_char(expected, symbols:str, char: str):
    mgr = DirectionManager.square_orthogonal(symbols)
    assert mgr.orientations[expected] == mgr.get_direction_from_char(char)