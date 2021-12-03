import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import read_lines

MOST_FREQUENT, LEAST_FREQUENT = "most_frequent", "least_frequent"
# part 2
def filter_bits(reports, bit, index):
  narrowed_bits = []
  for report in reports:
    if report[index] == bit:
      narrowed_bits.append(report)
  
  return narrowed_bits

def find_most_frequent(dict):
  count = float('-inf')
  most_frequent = 0

  for key in dict:
    if dict[key] == count:
      most_frequent = "1"
    elif dict[key] > count:
      count = dict[key]
      most_frequent = key
  
  return most_frequent

def find_least_frequent(dict):
  count = float('inf')
  least_frequent = 0
  for key in dict:
    if dict[key] == count:
      least_frequent = "0"
    elif dict[key] < count:
      count = dict[key]
      least_frequent = key
  
  return least_frequent

def determine_rating(input_bits, pattern_check):
  bits, column_length = input_bits,  len(input_bits[0])
  rating_result = []
  for column in range(0, column_length):
      row_length = len(bits)
      if (row_length == 1):
        return rating_result

      index, dict = 0, {}
      while index < row_length:
        item = bits[index][column]
        dict[item] = dict.get(item, 0) + 1
        index += 1

      # check bits based on either most frequent or least frequent
      if pattern_check == MOST_FREQUENT:
        frequent = find_least_frequent(dict)
      elif pattern_check == LEAST_FREQUENT:
        frequent = find_most_frequent(dict)
      bits = filter_bits(bits, frequent, column)
      
      row_length = len(bits)
      rating_result = bits
      
  return rating_result

def calculate_life_support_of_submarine(input):
  oxygen, co2 = determine_rating(input, MOST_FREQUENT), determine_rating(input, LEAST_FREQUENT)
  return int(oxygen[0], 2) * int(co2[0], 2)

if __name__ == '__main__':
  input = read_lines("advent-of-code/2021/day3/input.txt")
  print(f"part 2 {calculate_life_support_of_submarine(input)}") # 4267809