from file_utils import read_lines_numbers
from functools import reduce
from collections import defaultdict


def sum(a, b):
    return a + b

def calculate_frequency(input):
    return reduce(sum, input)

def duplicate_frequency(input):
    memo = {0}
    current_freq = 0

    while True:
        for freq in input:
            current_freq += freq
            if current_freq in memo:
                return current_freq
            memo.add(current_freq)


if __name__ == '__main__':
    # part 1
    main_input = read_lines_numbers("./inputs/input.txt")
    print(f"part1 - {calculate_frequency(main_input)}"); # 486

    # part 2
    print(f"part 2 - {duplicate_frequency(main_input)}") # 69285