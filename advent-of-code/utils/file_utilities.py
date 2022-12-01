'''
  read a line split by newline
'''
def read(filename):
  input = []
  with open(filename, 'r') as file:
    input = file.read().split("\n")

  return input

'''
  read matrix of integers
'''
def matrix_read(filename):
  result = []
  input = []
  with open(filename, 'r') as file:
    input = file.readlines()
    for line in input:
      line = line.strip()
      result.append(list(map(int, list(line))))

  return result

'''
read by line split by ,
'''
def read_single_line(filename):
  input = []
  with open(filename, 'r') as file:
    input = file.readline().split(",")
  return input
'''
read line of numbers split by , and  return int []
'''
def read_single_line_of_numbers(filename):
  return [int(item) for item in read_single_line(filename)]

'''
read all lines in a file contains newline
'''
def read_lines(filename):
  input = []
  with open(filename, 'r') as file:
    input = file.read().splitlines()

  return input

  """ read line based on direction eg R 190, L 50
  """
def read_with_directions(filename):
  input = []
  with open(filename, 'r') as file:
    input = file.readlines()

  result = []
  for line in input:
    direction = line.rstrip().split(",")
    for item in direction:
      result.append((item[:1], int(item[1:])))

  return result


def group_by_limiter(filename):
  input = []
  with open(filename, 'r') as file:
    result = file.read().split('\n')

  temp = []
  for item in result:
    calorie_count = int(item or 0)

    if calorie_count == 0:
      input.append(temp)
      temp = []
    temp.append(calorie_count)
  input.append(temp)
  return input