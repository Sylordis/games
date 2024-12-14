import pytest

from utils.tuples import Tuples


@pytest.mark.parametrize("expected, ta, tb",
                         [
                             ((0,0), (0,0), (0,0)),
                             ((1,-1), (0,0), (1,-1)),
                             ((-2,6), (3,4), (-5,2)),
                             ((4,4,4), (1,2,3), (3,2,1)),
                         ])
def test_add(expected: tuple, ta: tuple, tb: tuple):
    assert expected == Tuples.add(ta, tb)
