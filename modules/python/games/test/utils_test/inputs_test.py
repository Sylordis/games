import pytest

from utils.inputs import split_strs_to_list

@pytest.mark.parametrize("delim,strings,converter,expected",
    [
        ("   ", ["3   4", "4   3", "2   5", "1   3", "3   9", "3   3"], None, [["3","4","2","1","3","3"],["4","3","5","3","9","3"]]),
        ("   ", ["3   4", "4   3", "2   5", "1   3", "3   9", "3   3"], lambda x: int(x), [[3,4,2,1,3,3],[4,3,5,3,9,3]]),
    ])
def test_split_strs_to_list(delim, strings: list[str], converter, expected):
    assert expected == split_strs_to_list(delim, strings, converter)
