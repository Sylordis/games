from __future__ import annotations
from dataclasses import dataclass


@dataclass
class Range():
    """
    Represents a range of ints.
    """

    start: int
    "Start of the range."
    end: int
    "End of the range."

    @property
    def span(self):
        "Gets the total span for this range."
        return self.end - self.start + 1

    def contains(self, value:int) -> bool:
        """
        Checks if a value is contained in a given range.
        """
        return self.start <= value <= self.end

    def intersects(self, r:Range) -> bool:
        """
        Checks if a Range intersects with another range.
        """
        return r.start <= self.start <= r.end or r.start <= self.end <= r.end

    def merge(self, r:Range) -> Range:
        """
        Merges this range with another one.

        :param r: range to merge with
        :return: both ranges merged as a new one, or this range if they didn't intersect.
        """
        if self.intersects(r):
            return Range(min(r.start, self.start), max(r.end, self.end))
        else:
            return self
    # TODO
    # Range exclude Range
    # Range merge Range
