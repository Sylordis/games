class Tuples:

    @staticmethod
    def add(a, b) -> tuple:
        return tuple(sum(x) for x in zip(a, b))
