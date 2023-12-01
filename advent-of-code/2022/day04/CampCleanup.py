from file_utilities import read_lines

class CampCleanup:
    def part_1_execute(self, data):
        count = 0
        for index in range(0, len(data), 2):
            range_start_1, range_end_1 = data[index][0], data[index][1]
            range_start_2, range_end_2 = data[index + 1][0], data[index + 1][1]

            if (range_start_1 <= range_start_2 and range_end_2 <= range_end_1) or (range_start_2 <= range_start_1 and range_end_1 <= range_end_2):
                count += 1

        return count

    def part_2_execute(self, data):
        count = 0
        for index in range(0, len(data), 2):
            range_start_1, range_end_1 = data[index][0], data[index][1]
            range_start_2, range_end_2 = data[index + 1][0], data[index + 1][1]
            tempA = [value1 for value1 in range(range_start_1, range_end_1+1)]
            tempB = [value2 for value2 in range(range_start_2, range_end_2 + 1)]

            if (set(tempA) & set(tempB)) or (set(tempB) & set(tempA)): count += 1
        return count

if __name__ == '__main__':
    data = read_lines("./test/input.txt")
    result = []
    for item in data:
        split_ranges = item.split(",")
        for split_range in split_ranges:
            (rangeA, rangeB) = split_range.split("-")
            result.append((int(rangeA), int(rangeB)))

    print(f"part 1", CampCleanup().part_1_execute(result)) # 567
    print(f"part 2", CampCleanup().part_2_execute(result)) # 907