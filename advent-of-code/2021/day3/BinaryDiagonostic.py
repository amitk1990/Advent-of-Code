import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import read_lines

def calculate_power_consumption(diagonostic_report):
  row_length, column_length = len(diagonostic_report), len(diagonostic_report[0])
  gamma_rate = epsilon_rate =  ""

  for column in range(0, column_length):
    index, input_bits = 0, []
    while index < row_length:
      item = diagonostic_report[index][column]
      input_bits.append(item)
      index += 1

    if (input_bits.count("1") >= input_bits.count("0")):
      most_frequent, least_frequent = "1", "0"
    else:
      least_frequent, most_frequent = "1", "0"

    gamma_rate += str(most_frequent)
    epsilon_rate += str(least_frequent)
  
  # convert from binary to decimal and return product
  return (int(gamma_rate, 2) * int(epsilon_rate, 2))

if __name__ == '__main__':
  input = read_lines("advent-of-code/2021/day3/input.txt")
  print(f"part 1 {calculate_power_consumption(input)}") # 3969000