const fs = require("fs");

// (0) 3  0  1  -3  - before we have taken any steps.
// (1) 3  0  1  -3  - jump with offset 0 (that is, don't jump at all). Fortunately, the instruction is then incremented to 1.
//  2 (3) 0  1  -3  - step forward because of the instruction we just modified. The first instruction is incremented again, now to 2.
//  2  4  0  1 (-3) - jump all the way to the end; leave a 4 behind.
//  2 (4) 0  1  -2  - go back to where we just were; increment -3 to -2.
//  2  5  0  1  -2  - jump 4 steps forward, escaping the maze.


const twistyTrampoline = (nums) => {
  let i = 0;
  let n = nums.length;
  let counter = 0;
  while (i < n)  {
    let temp = nums[i];
    nums[i]++;
    i += temp;
    counter++;
  }

  return counter;
};

const twistyTrampolinePart2 = (nums) => {
  let i = 0;
  let n = nums.length;
  let counter = 0;

  while (i < n) {
    let temp = nums[i];
    nums[i] += (temp >= 3) ? -1 : 1;

    i += temp;
    counter++;
  }
  return counter;
};


(function () {
  const result = fs.readFileSync('./input.txt', 'utf8')
  let input = result.split("\n").map((item) => parseInt(item));
  console.log(input);
  // console.log("PART 1", twistyTrampoline(input));
  console.log("PART 2", twistyTrampolinePart2(input));
})();