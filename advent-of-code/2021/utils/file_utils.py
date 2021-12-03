def read(filename):
  input = []
  with open(filename, 'r') as file:
    input = file.read().split("\n")
    
  return input

def read_single_line(filename):
  input = []
  with open(filename, 'r') as file:
    input = file.readline().split(",")
  return input

def read_lines(filename):
  input = []
  with open(filename, 'r') as file:
    input = file.read().splitlines()

  return input

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
