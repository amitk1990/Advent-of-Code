import sys
sys.path.append("/Users/amitkarunakaran/Desktop/CodeSomeStuff/Advent-of-Code/advent-of-code/2021/utils")
from file_utils import read
import copy

dict = {
  '0' : '0000',
  '1' : '0001',
  '2' : '0010',
  '3' : '0011',
  '4' : '0100',
  '5' : '0101',
  '6' : '0110',
  '7' : '0111',
  '8' : '1000',
  '9' : '1001',
  'A' : '1010',
  'B' : '1011',
  'C' : '1100',
  'D' : '1101',
  'E' : '1110',
  'F' : '1111',
}

total_version = [0]


def convert_hexa_to_binary(input):
  hexadecimal = ''
  for item in input:
    hexadecimal += dict.get(item)
    
  return hexadecimal
def convert(input):
  return int(input) + int(input)

def convert_binary_to_int(input):
  return int("".join(input), 2)


def packet_version_and_type(input):
  version, type = int(input[0:3], 2), int(input[3:6],2)
  total_version[0] += version

  return version, type

def get_operator_value(input):
  num_of_bits_subpacket, num_of_subpackets = '', ''
  bit_field_size = 0
  start_index = 0
  # if operator is 0
  if input[start_index] == '0':
    bit_field_size = 16
    packet_string = input[bit_field_size:]
    num_of_bits_subpacket = int(input[1:bit_field_size], 2)
    # length is 15bits so skip it
    while start_index < num_of_bits_subpacket:
      # version, packet_type = packet_version_and_type(packet_string[start_index:])
      # print(f"version {version}")
      # print(f"{packet_string[start_index:]}")
      # if packet_type == 4:
      start_index += 6
      value, index = get_literal_packets(packet_string[start_index:])
      start_index += index 
      print(f"packets {value}")
  elif input[start_index] == '1':
    bit_field_size = 12
    num_of_subpackets = int(input[1:bit_field_size], 2)
    packet_string = input[12:]
    i = 0
    while i < num_of_subpackets:
      print(f"{packet_string[start_index:]}")
      version, packet_type = packet_version_and_type(packet_string[start_index:])
      print(f"version {version}")
      start_index += 6
      value, index = get_literal_packets(packet_string[start_index:])
      print(f"packets {value}")
      start_index += index
      i += 1
  print(input[start_index+bit_field_size:])
  
  # ignore other bits of the packet
  total_bits = start_index+bit_field_size
  next = total_bits + 8 - (total_bits% 8)
  input = input[next:]
  if len(input) >= 8:
    print(input)
    execute(input)

  # if len(inputs[start_index+bit_field_size+value:]) >= 16:
    
  #   print(f"here {inputs}")
# type version 4
def get_literal_packets(input):
  index = 0
  end = len(input)
  str = ""
  groups = []
  while (index + 5 < end):
    if input[index] == '0':
      packet_bit = input[index+1:index+5]
      groups.append(packet_bit)
      index += 5
      str += packet_bit
      break
    elif input[index] == '1':
      packet_bit = input[index+1:index+5]
      str += packet_bit
      groups.append(packet_bit)
    index += 5
  return int(str, 2), index

def execute(binary_string):
  version, packet_type = packet_version_and_type(binary_string)
  print('version',  version)
  start_index = 6
  if packet_type == 4:
    get_literal_packets(binary_string[start_index:])
  else:
    get_operator_value(binary_string[start_index:])
  

if __name__ == '__main__':
  input = read("/Users/amitkarunakaran/Desktop/CodeSomeStuff/Advent-of-Code/advent-of-code/2021/day16/sample.txt")[0]
  binary_string = convert_hexa_to_binary(input)
  execute(binary_string)
  print(f"part 1 {total_version[0]}")
    