from os import close, error
import sys
import math
sys.path.append("advent-of-code/2021/utils")

from collections import Counter

from file_utils import read

# part 1 score chart
score_chart = {
  ')': 3,
  ']': 57,
  '}': 1197,
  '>': 25137
};

# part 2 score chart
fix_up_score_chart = {
  ')': 1,
  ']': 2,
  '}': 3,
  '>': 4
};

# pair of parenthesis 
paren_mapping = {
  '{': '}',
  '<': '>',
  '[': ']',
  '(': ')',
};

# paren 1
def calculate_part1(result):
  sum = 0
  for key, value in Counter(result).items():
    sum += score_chart.get(key)*value

  return sum

def calculate_part2(fixed_list):
  result = []
  for fixed_line in fixed_list:
    total_score = 0
    for paren in fixed_line:
      total_score = (total_score*5 + fix_up_score_chart[paren])
    result.append(total_score)

  result.sort()
  index = math.floor(len(result)/2)
  return result[index]
    

def fix_up_syntax(stack):
  close_paren = ''
  while stack:
    item = stack.pop()
    close_paren += paren_mapping[item]
  
  return close_paren

def check_if_illegal(item, last_open_paren):
  if item in ['}', ')', ']', '>'] and paren_mapping[last_open_paren] != item:
    return True
  return False

def syntax_validator(stack, expected_peek, expected_closed_paren, item, result):
  illegal = False
  exit = False
  if not stack:
      raise Exception("Invalid case")
  elif stack[-1] == expected_peek:
    stack.pop()
  else:
    result.append(expected_closed_paren)
    illegal = check_if_illegal(item, stack[-1])
    exit = True
  return illegal, result, exit
    

def syntax_score_calculator(lines):
  result, fix_up_list = [], []
  open_paren = ['{', '[', '(', '<']

  for line in lines:
    stack = []
    illegal = exit = False
    for item in line:
      if item in open_paren:
        stack.append(item)
      elif item  == '}':
        illegal, result, exit = syntax_validator(stack, '{', '}', item, result, )
        if exit:
          break
      elif item == ')':
        illegal, result, exit = syntax_validator(stack, '(', ')', item, result, )
        if exit:
          break
      elif item == '>':
        illegal, result, exit = syntax_validator(stack, '<', '>', item, result, )
        if exit:
          break
      elif item == ']':
        illegal, result, exit = syntax_validator(stack, '[', ']', item, result, )
        if exit:
          break

    if stack and not illegal:
      fix_up_list.append(fix_up_syntax(stack))

  score = calculate_part1(result)
  total_score = calculate_part2(fix_up_list)

  return score , total_score
          
      

if __name__ == '__main__':
    input = read("advent-of-code/2021/day10/input.txt")
    part1, part2 = syntax_score_calculator(input)
    print(f"part 1 {part1} part 2 {part2}") # part 1 294195 and part 2 3490802734
    