import sys
sys.path.append("advent-of-code/2021/utils")

from file_utils import read

QUIT = "QUIT" # part 1 QUIT the game after you find the first board which wins
CONTINUE = "CONTINUE" # part 2 Continue playing until the last board wins - Squid

def convert_string_to_int_array(elements):
  return list(map(int, elements))

def prepare_matrix_and_sequence(input):
  sequence, matrix_lookup = input[0].split(","), {}
  sequence = convert_string_to_int_array(sequence)
  matrix_no, temp = 1, []
  matrix_input = input[2:]
  
  for item in range(0, len(matrix_input)):
    if matrix_input[item] == '':
      matrix_lookup[matrix_no] = temp 
      matrix_no += 1
      temp = []
    else:
      elements = matrix_input[item].strip().split(' ')
      elements = list(filter(None, elements)) # filter falsey values ''
      elements = convert_string_to_int_array(elements) # convert to string
      if len(elements) == 0:
        matrix_lookup[matrix_no] = temp 
        break
      temp.append(elements)

  return sequence, matrix_lookup

def is_bingo_check(matrix):
  count = 0
  for row in range(0, 5):
    count = 0
    for column in range(0, 5):
      if matrix[row][column] == 'X':
        count += 1
        if count == 5:
          return True
  
  for column in range(0, 5):
    count = 0
    for row in range(0, 5):
      if matrix[row][column] == 'X':
        count += 1
        if count == 5:
          return True
  
  return False

def mark_item_checked(matrix, seq):
  for row in range(0, len(matrix)):
    for column in range(0, len(matrix[row])):
      if matrix[row][column] == seq:
        matrix[row][column] = 'X'

# part 1 & 2
boards_completed= {}  
def play_bingo(sequences, matrix_lookup, state):
  number_of_matrixes = len(matrix_lookup.keys())
  for seq in sequences:
    for key in matrix_lookup.keys():
      matrix = matrix_lookup.get(key)
      mark_item_checked(matrix, seq)
      if is_bingo_check(matrix) and state == "QUIT":
        return key, seq
      elif is_bingo_check(matrix) and state == "CONTINUE":
        boards_completed[key] = "COMPLETED"
        if len(boards_completed.keys()) == number_of_matrixes:
          return key, seq

def reduce_to_final_score(bingo_matrix):
  return sum([item for list in bingo_matrix for item in list if item != 'X'])

def execute(sequence, matrix_lookup, GAME_STATE):
  key, seq = play_bingo(sequence, matrix_lookup, GAME_STATE)
  bingo_matrix = matrix_lookup.get(key)

  return reduce_to_final_score(bingo_matrix) * seq

if __name__ == '__main__':
    input = read("advent-of-code/2021/day4/input.txt")
    sequence, matrix_lookup = prepare_matrix_and_sequence(input)
    
    print(f"part 1: {execute(sequence, matrix_lookup, QUIT)}") # 44088
    print(f"part 2: {execute(sequence, matrix_lookup, CONTINUE)}") # 23670
