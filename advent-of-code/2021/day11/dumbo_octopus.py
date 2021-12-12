import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import matrix_read

directions = [(1, 0), (-1, 0), (0, 1), (0, -1), (1, 1), (-1, -1), (-1, 1), (1, -1)]

def apply_bfs(queue, input, flash, visited):
  row, column =  len(input), len(input[0])
  while queue:
    x, y = queue.pop(0) 
    for directionX, directionY in directions:
      newX = x + directionX
      newY = y + directionY

      # boundary check
      if 0 <= newX < row and 0 <= newY < column:
        if input[newX][newY] == 9 and not visited[newX][newY]:
          flash += 1
          queue.append((newX, newY))
          visited[newX][newY] = True
          input[newX][newY] = 0
        elif input[newX][newY] == 0:
            continue
        else:
          input[newX][newY] += 1
  return input, flash

def verify_all_octopus_have_flashed(visited):
  for row in range(len(visited)):
    if not all(visited[row]): return False
  return True

def calculate_flashes(input):
  step = flash = 0
  while step != 10000:
    row, column =  len(input), len(input[0])
    queue = []
    visited = [[False for y in range(column)] for x in range(row)] 

    for x in range(0, row):
      for y in range(0, column):
        if input[x][y] == 9:
          input[x][y] = 0
          flash += 1
          queue.append((x, y))
          visited[x][y] = True
        else:
          input[x][y] += 1

    input, flash = apply_bfs(queue, input, flash, visited)
    step += 1
    # part 1
    if step == 100:
      part1 = flash
    # part 2
    if verify_all_octopus_have_flashed(visited):
      return part1, step

if __name__ == '__main__':
  input = matrix_read("advent-of-code/2021/day11/input.txt")
  part1, part2 = calculate_flashes(input)
  print(f"part 1 {part1} part 2 {part2}") # part 1 1694 part 2 346
    