import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import read_lines

# part 2
reports = []
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

def determine_oxygen_rating(diagonostic_report):
  reports = diagonostic_report
  column_length = len(diagonostic_report[0])
  oxygen_rating_result = []
  for column in range(0, column_length):
      row_length = len(reports)
      if (row_length == 1):
        return oxygen_rating_result

      index, dict = 0, {}
      while index < row_length:
        item = reports[index][column]
        dict[item] = dict.get(item, 0) + 1
        index += 1
      most_frequent = find_most_frequent(dict)
      reports = filter_bits(reports, most_frequent, column)
      # calculate reports length and shrink
      row_length = len(reports)
      oxygen_rating_result = reports
  
  return oxygen_rating_result

def determine_co2_rating(diagonostic_report):
  reports = diagonostic_report
  column_length = len(diagonostic_report[0])
  co2_rating_result = []
  for column in range(0, column_length):
      row_length = len(reports)
      if (row_length == 1):
        return co2_rating_result

      index, dict = 0, {}
      while index < row_length:
        item = reports[index][column]
        dict[item] = dict.get(item, 0) + 1
        index += 1

      least_frequent = find_least_frequent(dict)
      reports = filter_bits(reports, least_frequent, column)
      
      row_length = len(reports)
      co2_rating_result = reports
      
  return co2_rating_result

def calculate_life_support_of_submarine(input):
  oxygen = determine_oxygen_rating(input)
  co2 = determine_co2_rating(input)
  return int(oxygen[0], 2) * int(co2[0], 2)

if __name__ == '__main__':
  input = read_lines("advent-of-code/2021/day3/input.txt")
  life = calculate_life_support_of_submarine(input)
  print(f"part 2 {life}") # 4267809