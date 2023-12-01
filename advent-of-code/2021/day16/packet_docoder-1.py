import sys
sys.path.append("/Users/amitkarunakaran/Desktop/CodeSomeStuff/Advent-of-Code/advent-of-code/2021/utils")
from file_utils import read
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

def convert_hexa_to_binary(input: str):
    output = ''

    for item in input:
        output += dict.get(item)

    return output

def is_packet_operator(input: str):
    return int(input[3:6], 2) != 4

def packet_version_and_type(input):
  version, type = int(input[0:3], 2), int(input[3:6],2)

  return version, type

# 1
# first 3 bits - version
# second 3 bits - type -
## type - 4 - literal value
## next bit starts with 1 - not a final value then 4 bits
## next bit starts with 0 - 4 bits after that if not pad 0
def generate_bits(bits):
    decode_bits = []
    for input in range(0, len(bits), 5):
        print(f"{input} {bits} {bits[input]}")
        if bits[input] == '1':
            print("slicing")
            print(bits[input+1:input+5])
            decode_bits.append(bits[input+1:input+5])
        elif bits[input] == '0':
            # handle edge case
            print("slicing 0")
            print(bits[input+1:])
            if len(bits[input+1:]) < 4:
                print(decode_bits)
                return decode_bits
            decode_bits.append(bits[input+1:input+5])
    print(decode_bits)
    return decode_bits

def convert_binary_to_decimal(input: str):
    return int("".join(input), 2)


def decode_literal(packet: str):
    print(f"\nHERE IS INPUT {packet}\n")
    version, type = packet_version_and_type(packet)
    number_bits = packet[6:]
    print(f"\nGENERATE  BITS {number_bits}\n")
    decode_bits = generate_bits(number_bits)
    transformed_input = "".join(decode_bits)
    print(f"version type decode bits {version} {type} {convert_binary_to_decimal(transformed_input)}")
    convert_binary_to_decimal(transformed_input)

# 2
# first 3 bits - version
# second 3 bits - type not 4
# next bit - 0 - next 15 bits indicate length of packet
# repeat the #1 approach
# next bit - 1 - next 11 is length to traverse
# arrange 11 bits each
def decode_operator(packet: str):
    print("DECODE OPERator")
    version, type = packet_version_and_type(packet)
    number_bits = packet[6:]

    if number_bits[0] == '0':
        length_of_packet = convert_binary_to_decimal(number_bits[1:16])
        literals = number_bits[16:]
        print("*****************")
        print(f"length {length_of_packet} literals {literals}")
        decode_literal(literals)
    elif number_bits[0] == '1':
        print("hello")


def packet_decoder(packet: str):
    print(f"input {packet}")
    if not is_packet_operator(packet):
        print("literal")
        decode_literal(packet)
    else:
        print("decode operator")
        decode_operator(packet)




if __name__ == '__main__':
    input = read("/Users/amitkarunakaran/Desktop/CodeSomeStuff/Advent-of-Code/advent-of-code/2021/day16/test.txt")[0]
    binary_digits = convert_hexa_to_binary(input)
    print(f" decoded packet {packet_decoder(binary_digits)}")
