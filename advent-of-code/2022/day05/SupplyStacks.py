from collections import defaultdict
from file_utilities import split_line_by_delimiter


class SupplyStacks:
	def transform_crates(self, crates):
		crates_split = crates.split("\n")
		base_crates = crates_split[len(crates_split) - 1]
		no_of_crates = base_crates.split("  ")
		number_crates = int(no_of_crates[len(no_of_crates) - 1])
		
		print(crates.split('\n'))
		
	def part1_execute(self, crates, instruction):
		self.transform_crates(crates)


if __name__ == '__main__':
    lines = split_line_by_delimiter("./data/test.txt", "\n\n")
    crates, instruction = lines[0], lines[1]
    print(f"part 1 {SupplyStacks().part1_execute(crates, instruction)}")