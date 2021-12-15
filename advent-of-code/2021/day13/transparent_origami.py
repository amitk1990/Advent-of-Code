from collections import defaultdict,Counter
import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import read_lines

matrix_grid = [0, 0]

def parse_input(input):
  coordinates = []
  fold_along = []
  for item in input:
    if len(item):
      if item.startswith("fold along"):
        fold = item.split(" ")[2]
        direction, value = fold.split("=")
        fold_along.append((direction, int(value)))
      else:
        x, y = item.split(',')
        matrix_grid[0] = max(matrix_grid[0], int(x))
        matrix_grid[1] = max(matrix_grid[1], int(y))
        coordinates.append((int(x), int(y)))
  return coordinates, fold_along

def build_matrix(grid):
  matrix = [[ '.' for j in range(matrix_grid[0] + 1)] for i in range(matrix_grid[1] + 1)]
  
  for x, y in grid:
    matrix[y][x] = '#'
  
  return matrix

def fold_paper_up(grid_x, grid_y):
  row = len(grid_y)
  column = len(grid_y[0])
  new_grid = []
  for i in range(row):
    temp = []
    for j in range(column):
      if grid_x[i][j] == '#' or grid_y[row - i - 1][j] == '#':
        temp.append('#')
      else:
        temp.append('.')
    new_grid.append(temp)

  return new_grid
  

def fold_paper_left(grid_x, grid_y):
  row = len(grid_y)
  column = len(grid_y[0])
  new_grid = []

  for i in range(row):
    temp = []
    for j in range(column):
      if grid_x[i][j] == '#' or grid_y[i][column - j - 1] == '#':
        temp.append('#')
      else:
        temp.append('.')
    new_grid.append(temp)

  return new_grid

def execute_fold_paper(instruction, matrix):
  direction, value = instruction
  length = len(matrix[0]) if direction == 'y' else len(matrix) 
  index = 0
  while (index < length):
    if direction == 'y':
      matrix[value][index] = '-'
    else:
      matrix[index][value] = '|'
    index += 1
  
  # y
  if direction == 'y':
    upper_half = matrix[0:value]
    lower_half = matrix[value + 1:]
    matrix = fold_paper_up(upper_half, lower_half)
  else:
    left_half = [matrix[i][0: value] for i in range(len(matrix))]
    right_half = [matrix[i][value+1:] for i in range(len(matrix))]

    matrix = fold_paper_left(left_half, right_half)
  return matrix

def calculate_dot_count(matrix):
  result = 0
  for item in range(len(matrix)):
    result += matrix[item].count('#')

  return result

def calculate_dot_visibility(coordinates, fold_along):
  matrix = build_matrix(coordinates)
  index = 0
  for instruction in fold_along:
    matrix = execute_fold_paper(instruction, matrix)
    if index == 0:
      part_1 = calculate_dot_count(matrix)
    index += 1

  return part_1
    
if __name__ == '__main__':
  input = read_lines("advent-of-code/2021/day13/input.txt")
  coordinates, fold_along = parse_input(input)
  print(f"part 1 {calculate_dot_visibility(coordinates, fold_along)}") # part 1 775 and part 2 REUPUPKR
    