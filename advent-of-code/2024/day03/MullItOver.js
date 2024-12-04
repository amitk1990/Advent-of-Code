const fs = require("fs");

class MullItOver {
    constructor(input1, input2) {
        this.input1 = input1;
        this.input2 = input2;
    }

    extractMul() {
        const result = [];
        this.input1.forEach((element) => {
            const regexPattern = /mul\(\d+,\d+\)/g;
            const matchedexpressions = element.match(regexPattern);

            result.push(...matchedexpressions);
        });
        return result;
    }

    calculateProductSum(input) {
        let product = BigInt(0);
        for (let i = 0; i < input.length; i++) {
            const regexPattern = /mul\((\d+),(\d+)\)/g;
            const [_, operand1, operand2] = regexPattern.exec(input[i]);

            product +=  BigInt(operand1) * BigInt(operand2);
        }

        return product;
    }

    executePart1() {
        const transformedInputForMultiplication = this.extractMul();

        return this.calculateProductSum(transformedInputForMultiplication);
    }

    extractMulForPart2() {
        const result = [];
        const res = this.input2
            .join('')
            .trim()
            .split("do()")
            .map(item => item
                .split("don't()")
                .shift())
            .join('');

        const regexPattern = /mul\((\d+),(\d+)\)/g;
        const matchedexpressions = res.match(regexPattern);

        result.push(...matchedexpressions);


        return result;
    }

    executePart2() {
        const transformedInput = this.extractMulForPart2();
        return this.calculateProductSum(transformedInput);
    }
}

(function () {
    const input1 = fs.readFileSync("./data/input.txt", "utf-8").split(/\r?\n/);
    const input2 = fs.readFileSync("./data/input.txt", "utf-8").split(/\r?\n/);
    const object = new MullItOver(input1, input2);

    console.log(` part 1 ${object.executePart1()}`) // 188116424
    console.log(` part 2 ${object.executePart2()}`); // 104245808
})();