import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import read
# global variable
dict = {}

def parse_input(input):
  list = []
  for line in input:
    coordinates_array = line.split('->')
    x1, y1 = coordinates_array[0].strip().split(',')
    x2, y2 = coordinates_array[1].strip().split(',')
    list.append([(int(x1), int(y1)), (int(x2), int(y2))])

  return list

def increment(start_x, end_x, start_y, state):
  for i in range(start_x, end_x + 1):
    if state == "y_same":
      str = f"{i}#{start_y}"
    else:
      str = f"{start_y}#{i}"
    dict[str] = dict.get(str, 0) + 1
    
def decrement(start_x, end_x, start_y, state):
  i = start_x
  
  while start_x >= end_x:
    if state == "y_same":
      str = f"{start_x}#{start_y}"
    else:
      str = f"{start_y}#{start_x}"
    # print(str)
    dict[str] = dict.get(str, 0) + 1
    start_x -= 1

def find_overlap_of_lines_without_diagonal(list):
  for item in list:
    x1, y1 = item[0]
    x2, y2 = item[1]
    # 8,1 -> 4, 2
    if x1 != x2 and y1 != y2:
      continue
      # 0,9 -> 5,9
    if x1 <= x2 and y1 == y2:
      increment(x1, x2, y1, "y_same")
      # 0,3 -> 0,6
    elif y1 <= y2 and x1 == x2:
      increment(y1, y2, x1, "x_same")
    elif x1 > x2 and y1 == y2:
      decrement(x1, x2, y1, "y_same")
    else:
      decrement(y1, y2, x1, "x_same")
      
  return calculate_total()

def calculate_total():
  result = 0
  for value in dict.values():
    if (value >= 2):
      result += 1
      
  return result

def increment_both(x1, x2, y1, y2):
  while (x1 <= x2 and y1 <= y2):
    str = f"{x1}#{y1}"
    dict[str] = dict.get(str, 0) + 1
    x1 += 1
    y1 += 1

def decrement_both(x1, x2, y1, y2):
  while (x1 >= x2 and y1 >= y2):
    str = f"{x1}#{y1}"
    dict[str] = dict.get(str, 0) + 1
    x1 -= 1
    y1 -= 1
    
def alternate(x1, x2, y1, y2, state):
  if state == "x_increment":
    while (x1 <= x2 and y1 >= y1):
      str = f"{x1}#{y1}"
      dict[str] = dict.get(str, 0) + 1
      x1 += 1
      y1 -= 1
  elif state == "y_increment":
    # 9,7 -> 7,9
    while (x1 >= x2 and y1 <= y2):
      str = f"{x1}#{y1}"
      dict[str] = dict.get(str, 0) + 1
      x1 -= 1
      y1 += 1

def find_overlap_of_lines_with_diagonals(list):
  for item in list:
    x1, y1 = item[0]
    x2, y2 = item[1]
    
    # 0,9 -> 5,9
    if x1 <= x2 and y1 == y2:
      increment(x1, x2, y1, "y_same")
      # 0,3 -> 0,6
    elif y1 <= y2 and x1 == x2:
      increment(y1, y2, x1, "x_same")
    elif x1 > x2 and y1 == y2:
      decrement(x1, x2, y1, "y_same")
    elif y1 > y2 and x1 == x2:
      decrement(y1, y2, x1, "x_same")
    # 1,1 -> 3,3
    elif x1 <= x2 and y1 <= y2:
      increment_both(x1, x2, y1, y2)
    # 3,3 => 1,1
    elif x1 > x2 and y1 > y2:
      decrement_both(x1, x2, y1, y2)
    # 9,7 -> 7,9
    elif x1 > x2 and y1 < y2:
      alternate(x1, x2, y1, y2, "y_increment")
    # 9,7 -> 7,9
    elif x1 < x2 and y1 > y2:
      alternate(x1, x2, y1, y2, "x_increment")
  
  return calculate_total()

if __name__ == '__main__':
  input = read("advent-of-code/2021/day5/input.txt")
  list = parse_input(input)
  print(f"part 1 {find_overlap_of_lines_without_diagonal(list)}") # 8350
  dict = {}
  print(f"part 2 {find_overlap_of_lines_with_diagonals(list)}") # 19374
  

    