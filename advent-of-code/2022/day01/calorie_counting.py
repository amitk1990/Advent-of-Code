from file_utilities import group_by_limiter

class CalorieCounting:
    def most_calories_elf(self, elf_snacks):
        return max([sum(snack) for snack in elf_snacks])

    def top_3_most_calories_elf(self, elf_snacks):
        return sum(sorted([ sum(snack) for snack in elf_snacks], reverse=True)[:3])

if __name__ == '__main__':
    calorie_input = group_by_limiter("./data/input.txt")

    print(f"part 1 {CalorieCounting().most_calories_elf(calorie_input)}") # 68467
    print(f"part 2 {CalorieCounting().top_3_most_calories_elf(calorie_input)}") # 203420


