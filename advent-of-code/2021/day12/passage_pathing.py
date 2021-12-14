from collections import defaultdict,Counter
import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import read_lines

matrix = defaultdict();

def add_edge(start, end):
  vertices = matrix.get(start, [])
  vertices.append(end)
  matrix[start] = vertices

def parse_input_matrix(input):
  for item in input:
    vertex = item.split('-')
    start = vertex[0]
    end = vertex[1]
    add_edge(start, end)
    add_edge(end, start)

def find_distinct_path():
  count = [0]

  def apply_dfs(start, visited, times, temp):
    if start == 'end':
      count[0] += 1
      # print(f"path is {temp}")
      return

    neighbors = matrix.get(start) or []
    
    for neighbor in neighbors:
      if not visited.get(neighbor):
        if neighbor.islower() and neighbor not in ['start', 'end']:
          visited[neighbor] = True
          times[neighbor] = times.get(neighbor, 0) + 1
        temp.append(neighbor)
        apply_dfs(neighbor, visited, times, temp)
        temp.remove(neighbor)
        if neighbor.islower() and neighbor not in ['start', 'end']:
          visited[neighbor] = False
          times[neighbor] = times.get(neighbor, 0) - 1
    

  start = 'start'
  visited = defaultdict()
  times = defaultdict()
  visited[start] = True
  apply_dfs(start, visited, times, ['start'])
  return count[0]

# part 2
def is_vertex_twice_visited(times):
    for value in times.values():
      if value >= 2:
        return True
    return False
  
def multiple_values(times):
  counter = 0
  for value in times.values():
    if value >= 2: counter += 1
  return counter >= 2

def find_distinct_path_atmost_twice():
  count = [0]

  def apply_dfs(start, visited, times, temp, is_visited_twice):
    if start == 'end':
      count[0] += 1
      # print(f"path is {temp}")
      return
    
    if is_visited_twice and multiple_values(times):
      return

    if start.islower() and is_vertex_twice_visited(times):
      is_visited_twice = True
      visited[start] = True
    neighbors = matrix.get(start) or []

    for neighbor in neighbors:
      if not visited.get(neighbor):
        if neighbor.islower() and neighbor not in ['start', 'end']:
          times[neighbor] = times.get(neighbor, 0) + 1
        temp.append(neighbor)
        apply_dfs(neighbor, visited, times, temp, is_visited_twice)
        temp.remove(neighbor)
        if neighbor.islower() and neighbor not in ['start', 'end']:
          visited[neighbor] = False
          times[neighbor] = times.get(neighbor, 0) - 1
    
    

  start = 'start'
  visited = defaultdict()
  times = defaultdict()
  visited[start] = True
  apply_dfs(start, visited, times, ['start'], False)
  return count[0]
    

if __name__ == '__main__':
  input = read_lines("advent-of-code/2021/day12/input.txt")
  parse_input_matrix(input)
  print(f"part 1 {find_distinct_path()}") # 5576  
  print(f"part 2 {find_distinct_path_atmost_twice()}") # 152837