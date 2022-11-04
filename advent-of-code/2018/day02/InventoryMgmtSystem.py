from file_utils import read_lines
from collections import Counter

def repeat(frequency, freq, count):
    if freq in frequency.values():
        count.append(1)
    return count


def calculate_checksum(input):
    twice, thrice = [], []

    for item in input:
        count_2 = repeat(Counter(item), 2, twice)
        count_3 = repeat(Counter(item), 3, thrice)

    return sum(count_2) * sum(count_3)


def compare_boxes(first, second):
    temp, diff = '', 0
    for a, b in zip(first, second):
        if a == b:
            temp += a
        if a != b:
            diff += 1

    return temp, diff


def common_box_ids(input):
    sorted_input = sorted(input)

    for index in range(1, len(sorted_input)):
        temp, diff = compare_boxes(
            sorted_input[index - 1], sorted_input[index])
        if diff == 1:
            return temp


if __name__ == '__main__':
    input = read_lines("./input/input.txt")
    print(f"part 1 {calculate_checksum(input)}")  # 6225
    print(f"part 2 {common_box_ids(input)}")  # revtaubfniyhsgxdoajwkqilp
