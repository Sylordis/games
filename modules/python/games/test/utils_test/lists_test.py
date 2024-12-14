import pytest

from utils.lists import Lists


@pytest.mark.parametrize("sequence,expected_trend",
                         [
                             ([1,2,3,4,5], 1),
                             ([1,2,8,4,5], 1),
                             ([5,4,2,2,1], -1),
                             ([1,5,4,3,2], -1),
                             ([1,1,2,1,1], 0),
                         ])
def test_get_global_trend(sequence: list[int], expected_trend: int):
    assert expected_trend == Lists.get_global_trend(sequence)
