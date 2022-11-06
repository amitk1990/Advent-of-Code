import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import read_lines

dict = {"forward": +1, "down": +1, "up": -1 }

def dive(nums):
  horizontal_position , depth = 0, 0
  for direction, value in nums:
    if direction == "forward":
      horizontal_position += dict[direction]*value
    else:
      depth += dict[direction]*value
      
  return horizontal_position*depth

def dive_with_aim(nums):
  horizontal_position , depth = 0, 0
  aim = 0
  for direction, value in nums:
    if direction == "forward":
      horizontal_position += dict[direction]*value
      depth += aim*value
    elif direction == "down":
      aim += value
    else:
      aim += dict[direction]*value
      
  return dive(nums), horizontal_position*depth

if __name__ == '__main__':
  input = read_lines("advent-of-code/2021/day02/input.txt")

  result = []
  for item in input:
    temp = item.rstrip().split(" ")
    result.append((temp[0], int(temp[1])))

  res = dive_with_aim(result)
  print(f"part 1 & part 2 {res}") # 1989014 & 2006917119
  
    