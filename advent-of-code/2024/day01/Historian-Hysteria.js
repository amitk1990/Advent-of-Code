const fs = require('fs');
class HistorianHysteria {

    executePart1(input) {
        let num1 = [], num2 = [];

        input.map(item => {
            num1.push(item[0])
            num2.push(item[1]);
        });
        num1.sort();
        num2.sort();

        let diff = 0;

        num1.forEach((item, index) => {
            diff += Math.abs((num1[index]) - num2[index]);
        });

        return diff;
    }

    executePart2(input) {
        let num1 = [];
        const map = new Map();

        input.forEach(item => {
            num1.push(item[0]);

            if (!map.has(item[1])) {
                map.set(item[1], 0);
            }
            map.set(item[1], map.get(item[1]) + 1);
        });

        let result = 0;
        num1.forEach((item, _) => {
            let count = 0;
            if (map.has(item)) {
                count = map.get(item);
            }

            result += item * count;
        });

        return result;
    }


}

(function () {
    const object = new HistorianHysteria();

    const input1 = fs.readFileSync("./data/input.txt", "utf-8").split(/\r?\n/);
    const transformedInputs = input1.map(item => item.split("  ").map(item => parseInt(item.trim(), 10)));

    console.log("part 1 ", object.executePart1(transformedInputs)); // 2192892
    console.log("part 2 ", object.executePart2(transformedInputs)); // 22962826
})();