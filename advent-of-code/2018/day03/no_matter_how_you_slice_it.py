from file_utils import read_lines
from collections import defaultdict

coordinates = defaultdict(list)
claim_map = defaultdict(set)
claims = set()

# parse input


def parser(item):
    raw_claim, character, config, raw_dimension = item.split(" ")
    fabric_config = list(map(int, config[0:len(config) - 1].split(",")))
    dimension = list(map(int, raw_dimension.split("x")))
    claim = raw_claim[1:]

    return claim, fabric_config, dimension

# non overlapping claims


def non_overlapping_claims():
    for values in claim_map.values():
        if len(values) > 1:
            claims.difference_update(values)

    return claims

# overlapping part 1


def overlapping_claims(result):
    for key, values in coordinates.items():
        if values > 1:
            result += 1
    return result


def draw_claims(input_values):
    claim, fabric_config, dimension = input_values

    # edge and dimesion of fabric
    x_axis, y_axis = int(fabric_config[0]), int(fabric_config[1])
    x_length, y_length = int(dimension[0]), int(dimension[1])

    claims.add(claim)

    for i in range(x_length):
        for j in range(y_length):
            x, y = x_axis + i, y_axis + j

            # Hashmap ' ' to avoid collisions
            key = str(x) + ' ' + str(y)
            if key in coordinates.keys():
                coordinates[key] = coordinates.get(key) + 1
                temp = claim_map.get(key, [])
                temp.add(claim)
                claim_map[key] = temp
            else:
                coordinates[key] = 1
                hashset = set()
                hashset.add(claim)
                claim_map[key] = hashset


def calculate_overlapping_claims(input):
    for item in input:
        parsed_input = parser(item)
        draw_claims(parsed_input)

    # part 1 & 2
    result = 0
    return overlapping_claims(result), non_overlapping_claims()


if __name__ == '__main__':
    input_values = read_lines("./input/input.txt")
    print(f"part 1 & 2: {calculate_overlapping_claims(input_values)}")
    '''
    part 1: 124850
    part 2: {'#1097'}
    '''
