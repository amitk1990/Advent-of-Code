const fs = require("fs");

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

    pickOperators(part) {
        return part === '1' ? this.operators : this.includeHiddenOperators;
    }

    executePart(equations, part) {
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

        const operatorsToPermutate = this.pickOperators(part);
        console.log(operatorsToPermutate);
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

                for (let operator of operatorsToPermutate) {
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

    console.log(`part 1 ${new BridgeRepair().executePart(equations, '1')}`); // part 1 - 1545311493300
    console.log(`part 2 ${new BridgeRepair().executePart(equations, '2')}`); // part 2 - 169122112716571
})();