from file_utils import  read_single_line

class AlchemicalReduction:
    @staticmethod
    def reduce(polymer):
        reduction = True
        while reduction:
            reduction = False
            index = 1
            while index < len(polymer):
                if (ord(polymer[index]) - 32) == ord(polymer[index-1]) or (ord(polymer[index-1]) - 32) == ord(polymer[index]):
                    # polarity are opposite, destroy each other - reduction
                    polymer = polymer[:index-1] + polymer[index+1:]
                    reduction = True
                else:
                    # same or different polymer, do nothing - no reduction
                    pass
                index += 1
        return len(polymer)

    def shortest_polymer(self, polymer):
        temp = polymer.lower()
        unit_types = sorted("".join(set(temp)))
        shortest_polymer = float('inf')
        for unit in unit_types:
            transformed_polymer = polymer[:]
            transformed_polymer = transformed_polymer.replace(unit, '')
            transformed_polymer = transformed_polymer.replace(unit.upper(), '')
            length = AlchemicalReduction.reduce(transformed_polymer)
            if shortest_polymer > length:
                shortest_polymer = length

        return shortest_polymer


if __name__ == '__main__':
    polymer = read_single_line("./inputs/demo.txt")
    polymer_reduction = AlchemicalReduction()
    print(f"part 1 {AlchemicalReduction.reduce(polymer[0])}") # 9822
    print(f"part 2 {polymer_reduction.shortest_polymer(polymer[0])}")  # 5726
