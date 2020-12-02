const fs = require("fs");

function cleanKey(key) {
  let [program, weight] = key.trim().split(" ");
  weight = weight.replace(/[()]/g, '');
  program.trim();
  return {
    weight,
    program
  };
}

function cleanValue(value) {
  return value.split(',').map(item => item.trim());
}

function getSets(lists) {
  let branches = new Set();
  let programs = new Set();
  lists.forEach(item => {
    if (item.includes('->')) {
      const [key, value]= item.split("->");
      let children = [];
        children = cleanValue(value.trim());
        for (item of children) {
          branches.add(item);
        }
        programs.add(cleanKey(key).program);
    } else {
      programs.add(cleanKey(item).program);
    }
  });

  return [
    branches,
    programs
  ];
}


(function(){
  const input = fs.readFileSync('input.txt', 'utf8')
  const lists = input.split("\n").map(item => item.trim());

  const [
    branches, 
    programs
  ] = getSets(lists);
  console.log("BRANCHES", branches.size);
  console.log("Programs", programs.size);
  // difference should be 1
  branches.forEach(item => programs.delete(item));
  console.log("RESULT", programs);
})();