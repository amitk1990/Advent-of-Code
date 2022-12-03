from file_utilities import read_lines

class RuckSackOrganization:
    priority_index = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    def calculate_score_index(self, input):
        result = []
        for item in input:
            result.append(self.priority_index.index(item.pop()))

        return sum(result)

    def part_1_execute(self, data):
        defects = []
        for item in data:
            split = int(len(item)/2)
            inputA, inputB = item[0:split], item[split:]
            defects.append(set(inputA) & set(inputB))

        return self.calculate_score_index(defects)

    def part_2_execute(self, rucksacks):
        result, common = [], []
        for i in range(0, len(rucksacks), 3):
            result.append([rucksacks[i], rucksacks[i + 1], rucksacks[i + 2]])

        for x, y, z  in result:
            common.append(set(x) & set(y) & set(z))

        return self.calculate_score_index(common)


if __name__ == '__main__':
    rucksacks = read_lines("./data/input.txt")
    print(f"part 1 {RuckSackOrganization().part_1_execute(rucksacks)}") # 7878
    print(f"part 2 {RuckSackOrganization().part_2_execute(rucksacks)}") # 2760

