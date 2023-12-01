const fs = require("fs");


class Trebuchet {
    constructor() {
        this.sum = 0;
        this.correctedSum = 0;
        this.numbers = {
            "one": 1,
            "two": 2,
            "three": 3,
            "four": 4,
            "five": 5,
            "six": 6,
            "seven": 7,
            "eight": 8,
            "nine": 9
        };
        this.stringNumbers = Object.keys(this.numbers);
    }

    findFirstDigit(input) {
        let first = '';
        let index1 = -1;
        for (let i = 0; i < input.length; i++) {
            if (input[i] >= '0' && input[i] <= '9') {
                first = input[i];
                index1 = i;
                break;
            }
        }


        return {
            left: first === '' ? 0 : parseInt(first, 10),
            index1
        }
    }

    findLastDigit(input) {
        let last = '';
        let index2 = -1;
        for (let i = input.length - 1; i >= 0; i--) {
            if (input[i] >= '0' && input[i] <= '9') {
                last = input[i];
                index2 = i;
                break;
            }
        }

        return {
            index2,
            right: last
        };
    }

    formDigits(left, right) {
        return right === '' ? parseInt(left, 10) : parseInt(`${left}${right}`, 10);
    }

    findCalibrationValues(input) {
        input.forEach((value) => {
            const calibrationValues = value.split("");
            const { left } = this.findFirstDigit(calibrationValues);
            const { right } = this.findLastDigit(calibrationValues);

            this.sum += this.formDigits(left, right);
        });

        return this.sum;
    }

    findFirstLetter(inputValue) {
        let found = { firstDigit: "", foundDigitIndex1: -1 };
        let min = Number.MAX_SAFE_INTEGER;

        for (let stringDigit of this.stringNumbers) {
            const foundDigitIndex1 = inputValue.indexOf(stringDigit);

            if (foundDigitIndex1 > -1 && min > foundDigitIndex1) {
                min = foundDigitIndex1
                found = {
                    firstDigit: stringDigit,
                    foundDigitIndex1: min
                };
            }
        }

        return found;
    }

    findLastLetter(inputValue) {
        let found = { lastDigit: "", foundDigitIndex2: -1 };
        let max = Number.MIN_SAFE_INTEGER;

        for (let stringDigit of this.stringNumbers) {
            const foundDigitIndex2 = inputValue.lastIndexOf(stringDigit);
            if (foundDigitIndex2 > -1 && max < foundDigitIndex2) {
                max = foundDigitIndex2;
                found = {
                    lastDigit: stringDigit,
                    foundDigitIndex2: max
                };
            }
        }
        return found;
    }

    leftMostDigitFormation(firstDigit, foundDigitIndex1, index1, left) {
        // if the number digit is not found, pick the string
        if (index1 === -1) {
            return this.numbers[firstDigit]
        }
        // if the string digit is not found.
        if (!firstDigit) {
            return left;
        }

        return foundDigitIndex1 < index1 ? this.numbers[firstDigit] : left
    }

    rightMostDigitFormation(lastDigit, foundDigitIndex2, index2, right) {
        // if the string digit is not found.
        if (!lastDigit) {
            return right;
        }
        // if the number digit is not found, pick the string
        if (index2 === -1) {
            return this.numbers[lastDigit];
        }

        return foundDigitIndex2 > index2 ? this.numbers[lastDigit] : right;
    }

    findCalibrationValuesWithLetters(inputs) {
        inputs.forEach((input) => {
            const calibrationValues = input.split("");
            // first digit comparison
            const { firstDigit, foundDigitIndex1 } = this.findFirstLetter(input);
            const { left, index1 } = this.findFirstDigit(calibrationValues);

            // last digit comparision
            const { lastDigit, foundDigitIndex2 } = this.findLastLetter(input);
            const { right, index2 } = this.findLastDigit(calibrationValues);


            const leftMostdigit = this.leftMostDigitFormation(firstDigit, foundDigitIndex1, index1, left);
            const rightMostdigit = this.rightMostDigitFormation(lastDigit, foundDigitIndex2, index2, right);

            this.correctedSum += this.formDigits(leftMostdigit, rightMostdigit);
        });

        return this.correctedSum;

    }
}

(function main() {
    const object = new Trebuchet();
    const input1 = fs.readFileSync("./day01/test/test.txt", "utf-8").split(/\r?\n/);
    console.log("part 1 " + object.findCalibrationValues(input1)); // 54927
    const input2 = fs.readFileSync("./day01/test/test2.txt", "utf-8").split(/\r?\n/);
    console.log("part 2 " + object.findCalibrationValuesWithLetters(input2)); // 54581)
})();