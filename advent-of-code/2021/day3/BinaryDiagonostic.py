import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import read_lines

def find_most_frequent(dict):
  count = 0
  most_frequent = 0
  for key in dict:
    if dict[key] >= count:
      count = dict[key]
      most_frequent = key
  
  return most_frequent

def calculate_power_consumption(diagonostic_report):
  row_length = len(diagonostic_report)
  column_length = len(diagonostic_report[0])
  gamma_rate = ""
  epsilon_rate = ""
  for column in range(0, column_length):
    dict = {}
    i = 0
    while i < row_length:
      item = diagonostic_report[i][column]
      dict[item] = dict.get(item, 0) + 1
      i += 1
    most_frequent = find_most_frequent(dict)

    least_frequent = "0"
    if most_frequent == "1":
      least_frequent = "0"
    else:
      least_frequent = "1"

    gamma_rate += str(most_frequent)
    epsilon_rate += str(least_frequent)
  
  # convert to decimal and return product
  return (int(gamma_rate, 2) * int(epsilon_rate, 2))

if __name__ == '__main__':
  input = read_lines("advent-of-code/2021/day3/input.txt")
  print(f"part 1 {calculate_power_consumption(input)}") # 3969000