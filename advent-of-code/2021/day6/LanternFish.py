import sys
sys.path.append("advent-of-code/2021/utils")
sys.setrecursionlimit(250000)

from file_utils import read_single_line_of_numbers
# part 1
def fish_decay(fishes):
  index = 0
  total_fishes = len(fishes)
  while index < total_fishes:
    if fishes[index] == 0:
      fishes[index] = 6
      new_fish = 8
      fishes.insert(len(fishes), new_fish)
    else:
      fishes[index] -= 1
    index += 1
  return fishes

def lanternfish_spawn_execute_1(fishes, days):
  initial_day = 0
  while initial_day < days:
    fishes = fish_decay(fishes)
    initial_day += 1
  return len(fishes)    

# smart way to solve part 2
def calculate_by_age_groups(fishes, end_day):
  result = [fishes.count(i) for i in range(0, 9)]
  
  for day in range(0, end_day):
    new_fishes = result[0]
    del result[0]
    result.append(0)
    result[6] += new_fishes
    result[8] = new_fishes
  
  return sum(result)
  
if __name__ == '__main__':
  # part 1
  input = read_single_line_of_numbers("advent-of-code/2021/day6/input.txt")
  end_days = 80
  print(f"part 1 - {lanternfish_spawn_execute_1(input, end_days)}") # part 1 - 374927, 80
  # part 2
  input = read_single_line_of_numbers("advent-of-code/2021/day6/input.txt")
  end_days = 256
  print(f"part 2 - {calculate_by_age_groups(input, end_days)}") # part 2 - 1791204533000676 256
    