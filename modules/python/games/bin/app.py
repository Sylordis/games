import argparse
# import importlib.util
# from pathlib import Path
import sys
import logging

from ..src.aoc.aoc2024.day5 import Day5


logger = logging.getLogger(__name__)


class ArgParser:
    """
    Class to organise and setup the different options for the software.
    """

    def __init__(self):
        self.parser = argparse.ArgumentParser(
            prog=__name__, description="Runs a python coding puzzle."
        )
        # self.parser.add_argument("module", help="Module of the game.")
        self.parser.add_argument("input_file", help="Input file.", nargs='+')
        self.parser.add_argument(
            "--debug",
            help="Sets debug mode (equivalent to '--log debug').",
            action="store_const",
            dest="loglevel",
            const="debug",
            default="info",
        )

    def parse(self):
        return self.parser.parse_args()


def main():
    args = ArgParser().parse()
    logging.basicConfig(level=getattr(logging, args.loglevel.upper(), None))
    # class_name = args.module.split(".")[-1].capitalize()
    # print("..src." + args.module, class_name)
    # module = importlib.import_module("..src." + args.module, "games")
    # class_ = getattr(module, class_name)
    # instance = class_()
    # instance.solve(args.input_file)
    # print(args.module, args.input_file)
    Day5().solve(args.input_file)
