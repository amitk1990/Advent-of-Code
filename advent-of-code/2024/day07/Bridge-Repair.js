const fs = require("fs");
const {map} = require("lodash");

class BridgeRepair {
    operators = ['+', '*'];
    includeHiddenOperators = ['+', '*', '||'];

    transformInputs(equations) {
        return equations.map(equation => {
            const [result, operands] = equation.split(": ");
            let transformedOperands = operands.split(" ").map(item => parseInt(item, 10));
            return [parseInt(result, 10), transformedOperands];
        });
    }

    executePart1(equations) {
        const transformedEquations = this.transformInputs(equations);


        let total = 0;
        for (const equation of transformedEquations) {
            const [expectation, operands] = equation;

            const map = new Map();

            const permutate = (index, temp) => {
                if (index === operands.length - 1) {
                    // calculate the result
                    let tempResult = eval(temp);
                    if (tempResult === expectation && !map.has(tempResult)) {
                        map.set(expectation, temp);
                        total += tempResult;
                        return;
                    }
                    return;
                }

                for (let operator of this.operators) {
                    const expression = `(${temp}${operator}${operands[index + 1]})`;
                    permutate(index + 1, expression);
                }
            }


            permutate(0, `(${String(operands[0])})`);
        }

        return total;
    }

    executePart2(equations) {
        const transformedEquations = this.transformInputs(equations);

        const customEvaluation = (temp) => {
            const customExpr = temp.split(/(\+|\|\||\*)/);
            let result = parseInt(customExpr[0], 10);

            for (let i = 1; i<customExpr.length; i += 2) {
                const integers = parseInt(customExpr[i+1], 10);
                const operator = customExpr[i];

                if (operator === '+') {
                    let expression = `${result}+${integers}`;
                    result = eval(expression);
                } else if (operator === '*') {
                    let expression = `${result}*${integers}`;
                    result = eval(expression);
                } else {
                    result = parseInt(`${result}${integers}`, 10);
                }
            }

            return result;
        }

        let totalCalibrationResult = 0;
        for (const equation of transformedEquations) {
            const [expectation, operands] = equation;
            const map = new Map();

            const permutate = (index, temp) => {
                if (index === operands.length - 1) {
                    // evaluation
                    let tempCalibration = customEvaluation(temp)
                    if (tempCalibration === expectation && !map.has(tempCalibration)) {
                        map.set(expectation, tempCalibration);
                        totalCalibrationResult += tempCalibration;
                    }
                    return;
                }

                for (let operator of this.includeHiddenOperators) {
                    const expression = `${temp}${operator}${operands[index + 1]}`;
                    permutate(index + 1, expression);
                }
            }

            permutate(0, `${String(operands[0])}`);
        }

        return totalCalibrationResult;
    }
}

(function () {
    const equations = fs.readFileSync("./data/input.txt", "utf-8").split(/\r?\n/);

    console.log(`part 1 ${new BridgeRepair().executePart1(equations)}`); // 1545311493300
    console.log(`part 2 ${new BridgeRepair().executePart2(equations)}`); // 169122112716571
})();