def read(filename):
  input = []
  with open(filename) as file:
    input = file.read().split("\n")
    
  return input
