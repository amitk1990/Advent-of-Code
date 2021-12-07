import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import read_single_line_of_numbers

def calculate_fuel_spent(input):
  count = float('inf')
  for i in input:
    fuel = 0 
    for item in input:
      fuel += abs(item - i)
    count = min(fuel, count)
  
  return count


def simulate_fuel_spent(low, high):
  n = abs(high - low)
  return (n*(n+1))/2

def calculate_fuel_spent_exponentially(inputs):
  count = float('inf')
  max_range, min_range = max(inputs), min(inputs)
  for value in range(min_range, max_range):
    cost = 0
    for item in inputs:
      cost += simulate_fuel_spent(value, item)
    count = min(cost, count)
  return count

if __name__ == '__main__':
  input = read_single_line_of_numbers("advent-of-code/2021/day7/input.txt")
  print(f"part 1 {calculate_fuel_spent(input)}") #352707
  print(f"part 2 {calculate_fuel_spent_exponentially(input)}") #95519693.0