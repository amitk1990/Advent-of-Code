def calculate_depth_measurement_increase(nums):
  count = 0
  for i in range(1, len(nums)):
    if nums[i] > nums[i-1]:
      count += 1
  
  return count

def calculate_three_measure_sliding_window(nums):
  count = 0
  n = len(nums)
  for i in range(2, n):
    if ((i + 1) < n):
      previous_sum = sum(nums[i-2:i+1])
      current_sum = sum(nums[i-1:i+2])
      
      if current_sum > previous_sum:
        count += 1
  
  return count
    
  
if __name__ == '__main__':
  input = []
  with open('./advent-of-code/2021/day1/input.txt') as day1:
    input = day1.read().split('\n')
  input_nums = [int(x) for x in input]
  # part 1
  print(f"part 1 - {calculate_depth_measurement_increase(input_nums)}") # 1527
  # part 2
  print(f"part 2 - {calculate_three_measure_sliding_window(input_nums)}") # 1575
    