import numpy as np

class Lists:

    @staticmethod
    def get_global_trend(items: list[int]) -> int:
        trend = 0
        it = iter(items)
        previous = next(it)
        while (current := next(it, None)) is not None:
            if previous < current:
                trend += 1
            elif current < previous:
                trend -= 1
            previous = current
        return np.sign(trend)
