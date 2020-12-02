const fs = require("fs");


function getMaxOfArray(nums) {
  let maximumObject = {
    maximum: -1,
    index: -1
  };

  for (let i = 0; i < nums.length; i++) {
    if (nums[i] > maximumObject.maximum) {
      maximumObject = {
        maximum: nums[i],
        index: i
      };
    }
  }

  return maximumObject;
}

function reallocateMemoryBank(input, {
  index,
  maximum
}) {
  const n = input.length;
  // reset the maximum index
  input[index] = 0;
  iterator = index + 1;
  iterator = (iterator% n == 0) ? 0 : iterator;

  while (maximum != 0) {
    maximum--;
    input[iterator]++;
    if ((iterator+1)% n == 0){
      iterator = 0;
    } else {
      iterator++;
    }
  }
  console.log("REALLOCATED", input);
  return input;
}

function countStepsToCatchLoop(input) {
  let set = new Set();
  let counter = 0;
  do {
    // add pattern to set
    set.add(input.toString())
    const maxValAndIndex = getMaxOfArray(input);
    input = reallocateMemoryBank(input, maxValAndIndex)
    counter++;
  } while(!set.has(input.toString()));

  return counter;
}

function cyclesUntilLoopDetected(input) {
  let map = new Map();
  let counter = 0;
  do {
    // add pattern to map
    map.set(input.toString(), counter);
    const maxValAndIndex = getMaxOfArray(input);
    input = reallocateMemoryBank(input, maxValAndIndex)
    counter++;
  } while(!map.has(input.toString()));

  const cycleNumber = map.get(input.toString());
  console.log("GAP between cycles", counter - cycleNumber);

  return counter;
}


(function() {
  const result = fs.readFileSync('test.txt', 'utf8')
  const input = result.split(" ").map((item) => parseInt(item));
  // part 1
  console.log("INPUT", input);
  console.log("OUTPUT", countStepsToCatchLoop(input));
  // part 2
  console.log("INPUT", input);
  console.log("OUTPUT", cyclesUntilLoopDetected(input));
})();