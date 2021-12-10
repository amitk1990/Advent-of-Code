import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import matrix_read
from functools import reduce

def is_valid(i, j, input):
  if i < 0 or j < 0 or i >= len(input) or j >= len(input[0]):
    return float('inf')
  return input[i][j]

def sum_risk_levels_in_heightmap(input):
  row, column, count = len(input), len(input[0]) , 0
  for i in range(0, row):
    for j in range(0, column):
      down = is_valid(i+1, j, input)
      up = is_valid(i-1, j, input)
      forward = is_valid(i, j+1, input)
      back = is_valid(i, j-1, input)
      
      if (input[i][j] < down and input[i][j] < up and input[i][j] < forward and input[i][j] < back):
        count += input[i][j] + 1
        
  return count

def dfs(input, i, j, area):
  if (i < 0 or j < 0 or i >= len(input) or j >= len(input[0]) or input[i][j] == '#' or input[i][j] == 9):
    return 0
  # visited
  input[i][j] = '#'
  return (1 + dfs(input, i + 1, j, area) + dfs(input, i - 1, j, area) + dfs(input, i, j + 1, area) + dfs(input, i, j - 1, area))

def find_largest_basin(input):
  row = len(input)
  column = len(input[0])
  result = []

  for i in range(0, row):
    for j in range(0, column):
      if input[i][j] != 9:
        area = dfs(input, i, j, 0)
        result.append(area)
  
  result.sort(reverse=True)
  top3 = result[0:3]
  return reduce(lambda x,y: x*y, top3)
      
        
if __name__ == '__main__':
  input = matrix_read("advent-of-code/2021/day9/input.txt")
  print(f"day 1 {sum_risk_levels_in_heightmap(input)}") # 528
  print(f"day 2 - {find_largest_basin(input)}") # 920448
    