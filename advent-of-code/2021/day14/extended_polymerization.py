import sys
sys.path.append("advent-of-code/2021/utils")
from collections import Counter, defaultdict

from file_utils import read_lines
# CN -> B
def build_pair_insertion_dictionary(input):
  map = defaultdict(str)
  for item in input:
     key, value = tuple(item.split(' -> '))
     map[key] = value
  return map

# NNCB
def build_polymer(template, dict, mapper):
  insert = {}
  
  for i in range(0, len(template) - 1):
    x = template[i]+template[i+1]
    if x in dict:
      index = i
      value = dict.get(x)
      mapper[value] = mapper.get(value, 0) + 1
      insert[index+1] = value
  parts = ''
  index = 0
  while index < len(template):
    if index in insert:
      value = insert.get(index)
      parts += value
    parts += template[index]
    index += 1
  template = parts
  return parts, mapper

# brute force
def execute(input):
  input  = list(filter(None, input))
  polymer_template = input[0]
  dict = build_pair_insertion_dictionary(input[1:])
  step = 0
  mapper = Counter(polymer_template)
  while step < 10:
    polymer_template, mapper = build_polymer(polymer_template, dict, mapper)
    step += 1
  return max(mapper.values()) - min(mapper.values())

# uses maps and inserts pairs simultaneous, no need to rebuild
def build_smart_polymer(dict, mapper, pair):
  new_pairs = Counter()
  for key, value in pair.items():
    # NN -> C
    new_key = dict.get(key)
    # NC
    polymer_x = key[0]+new_key
    # CN
    polymer_y = new_key+key[1]
    # CN -> 1, NC -> 1
    new_pairs[polymer_x] = new_pairs.get(polymer_x, 0) + value
    new_pairs[polymer_y] = new_pairs.get(polymer_y, 0) + value
    mapper[new_key] = mapper.get(new_key, 0) + value
  
  pair = new_pairs
  return pair, mapper

def execute_smarter(input, end):
  input  = list(filter(None, input))
  polymer_template = input[0]
  dict = build_pair_insertion_dictionary(input[1:])
  step = 0
  mapper = Counter(polymer_template)
  pair = Counter()
  for i in range(0, len(polymer_template) - 1):
    x = polymer_template[i]+polymer_template[i+1]
    pair[x] = pair.get(x, 0) + 1

  while step < end:
    pair, mapper = build_smart_polymer(dict, mapper, pair)
    step += 1

  return max(mapper.values()) - min(mapper.values())
  
if __name__ == '__main__':
  input = read_lines("advent-of-code/2021/day14/input.txt")
  print(f"part 1 {execute(input)}") #3058
  print(f"part 1 {execute_smarter(input, 10)}") #3058
  print(f"part 2 {execute_smarter(input, 40)}") #3447389044530