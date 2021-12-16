import sys
sys.path.append("advent-of-code/2021/utils")
from file_utils import matrix_read

import heapq
import math
import copy

directions = [[0,1], [1,0], [0, -1], [-1, 0]]

# compute 5*5 map
def build_5x5matrix(input):
  row, column = len(input), len(input[0])
  new_column = column*5
  first = []
  index = i = 0

  while i < row:
    temp = []
    j = 0
    while j < new_column:
      value = math.floor(j/column)
      score = input[i][j%column] + 1*value
      if score >= 10:
        score = score%10 + 1
      
      temp.append(score)
      j += 1
    i += 1
    first.append(temp)  
  # 1 * 5 dimension matrix 
  new_matrix = copy.deepcopy(first)

  # setup remaining part of the matrix
  index = 1
  while index <= 4:
    second = []
    for item in first:
      temp = []
      for score in item:
        new_score = score + 1
        if new_score >= 10:
          new_score = new_score%10 + 1
        temp.append(new_score)
      second.append(temp)
      new_matrix.append(temp)

    first = second
    index += 1
  return new_matrix

def calculate_total_risk(input):
  new_matrix = build_5x5matrix(input)
  return apply_bfs(input, 0, 0), apply_bfs(new_matrix, 0, 0)

# Dijkstra's algorithm
def apply_bfs(input, x, y):
  row, column = len(input), len(input[0])
  
  def is_valid(x, y):
    return (x >= 0 and y >= 0 and x < row and y < column)
  
  def is_safe(x, y):
    return not visited[x][y]

  visited = [[False for j in range(0, column)] for i in range(0, row)]    
  # 0, 0 and distance 0
  queue = []
  heapq.heappush(queue, (0, x, y))
  visited[x][y] = True
  small = float('inf')
  while queue:
    size = len(queue)
    for i in range(size):
      (distance, x, y) = heapq.heappop(queue)
      
      if x == row - 1 and y == column - 1:
        small = min(small, distance)
        return small

      for direction in directions:
        newX = x + direction[0]
        newY = y + direction[1]
        # boundary check and not visited
        if is_valid(newX, newY) and is_safe(newX, newY):
          new_distance = distance + input[newX][newY]
          visited[newX][newY] = True
          heapq.heappush(queue, (new_distance, newX, newY))
          
  return small

if __name__ == '__main__':
  input = matrix_read("advent-of-code/2021/day15/input.txt")
  part_1_ans, part_2_ans = calculate_total_risk(input)
  print(f"part 1 & 2 {part_1_ans, part_2_ans}") # (741, 2976)
    