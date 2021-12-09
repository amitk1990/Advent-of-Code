import sys
sys.path.append("advent-of-code/2021/utils")

from collections import defaultdict

from file_utils import read_lines

# part 1
def find_unique_segments(input):
  count = 0
  for string in input:
    line_of_segments = string.split('|')[1].strip()
    
    for segment in line_of_segments.split(' '):
      if len(segment) in [2, 3, 4, 7]:
        count += 1
  return count

# part 2
def map_to_dict(input):
  dict = defaultdict(list)
  for value in input:
    new_value = ''.join(sorted(value))
    dict[len(new_value)].append(new_value)

  return dict

def decode_numbers(dict):
  display_8 = dict.get(7)
  display_6 = dict.get(6)
  display_5 = dict.get(5)
  display_1 = dict.get(2)[0]
  display_4 = dict.get(4)[0]
  display_7 = dict.get(3)[0]
  NUMBER_0 = ''
  NUMBER_1 = display_1
  NUMBER_4 = display_4
  NUMBER_6 = ''
  NUMBER_7 = display_7
  NUMBER_8 = display_8[0]
  NUMBER_9 = ''
  segments = {}
  # Based on signal value we can figure out 1, 4, 7, 8
  segments[NUMBER_1] = 1
  segments[NUMBER_4] = 4
  segments[NUMBER_7] = 7
  segments[NUMBER_8] = 8
  for display in display_6:
    disp_1 = set(display_1)
    # set difference  6,9,0 with 1 -> 6 
    result = set(disp_1).difference(display)
    if len(result) == 1:
      NUMBER_6 = display
      segments[NUMBER_6] = 6
      display_6.remove(display)
  
  for display in display_6:
    # set difference  9, 0 with 4 -> 0
    result = set(display_4).difference(display)
    if len(result) == 1:
      NUMBER_0 = display
      display_6.remove(display)

  # left over is 9
  NUMBER_9 = display_6[0]
  segments[NUMBER_9] = 9
  
  for input in display_5:
    # set difference 2, 3, 5 with 6 == 1 indicates it is 5
    result = set(NUMBER_6).difference(input)
    
    if len(result) == 1:
      NUMBER_5 = input
      segments[NUMBER_5] = 5
      display_5.remove(input)
  
  for input in display_5:
    # set difference 2, 3 with 9 == 1 indicates it is 3
    result = set(NUMBER_9).difference(input)
    if len(result) == 1:
      NUMBER_3 = input
      segments[NUMBER_3] = 3
      display_5.remove(input)

  # left over is 2
  NUMBER_2 = display_5[0]
  segments[NUMBER_2] = 2
  segments[NUMBER_0] = 0
  return segments

def calculate_segment_pattern(input, segments):
  dict = map_to_dict(input)
  signal_mapping = decode_numbers(dict)
  decoded_signal = ''
  for segment in segments:
    input  = ''.join(sorted(segment))
    decoded_signal += str(signal_mapping[input])
  
  return int(decoded_signal)
  
def find_total_segment(input):
  sum = 0
  for string in input:
    line_of_segments = string.split('|')
    signal_wires = line_of_segments[0].strip().split(' ')
    segments = line_of_segments[1].strip().split(' ')
    sum += calculate_segment_pattern(signal_wires, segments)
  
  return sum
    
    

if __name__ == '__main__':
  input = read_lines("advent-of-code/2021/day8/input.txt")
  print(f"part 1 {find_unique_segments(input)}") # 456
  print(f"part 2 {find_total_segment(input)}") # 1091609
    