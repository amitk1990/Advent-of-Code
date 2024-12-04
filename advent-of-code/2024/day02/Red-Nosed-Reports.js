const fs = require("fs");

class RedNosedReports {

    isIncreasing(report) {
        for (let i = 1; i < report.length; i += 1) {
            if (report[i] - report[i - 1] < 1 || report[i] - report[i - 1] > 3) {
                return false;
            }
        }

        return true;
    }

    isDecreasing(report) {
        for (let i = 1; i < report.length; i += 1) {
            if (report[i - 1] - report[i] < 1 || report[i - 1] - report[i] > 3) {
                return false;
            }
        }

        return true;
    }

    isSafe(report) {
        return (this.isIncreasing(report) || this.isDecreasing(report));
    }

    executePart1(reports) {
        let count = 0;
        reports.forEach((report) => {
            if (this.isSafe(report)) {
                count += 1;
            }
        });

        return count;
    }

    moderatelyIncrease(report) {
        for (let i = 1; i < report.length; i += 1) {
            if (report[i] - report[i - 1] < 1 || report[i] - report[i - 1] > 3) {
                let modifyReport1 = [...report];
                let modifyReport2 = [...report];

                modifyReport1.splice(i, 1);
                modifyReport2.splice(i - 1, 1);

                return this.isIncreasing(modifyReport1) || this.isIncreasing(modifyReport2);
            }
        }

        return true;
    }

    moderatelyDecrease(report) {
        for (let i = 1; i < report.length; i += 1) {
            if (report[i - 1] - report[i] < 1 || report[i - 1] - report[i] > 3) {
                let modifyReport1 = [...report];
                let modifyReport2 = [...report];

                modifyReport1.splice(i, 1);
                modifyReport2.splice(i - 1, 1);

                return this.isDecreasing(modifyReport1) || this.isDecreasing(modifyReport2);
            }
        }

        return true;
    }

    moderatelySafe(report) {
        return this.moderatelyIncrease(report) || this.moderatelyDecrease(report);
    }

    executePart2(reports) {
        let count = 0;

        reports.forEach((report) => {
            if (this.moderatelySafe(report)) {
                count += 1;
            }
        });
        return count;
    }
}

(function () {
    const input1 = fs.readFileSync("./data/input.txt", "utf-8").split(/\r?\n/);
    const transformedInputs = input1.map(item => item.split(" ").map(item => parseInt(item.trim(), 10)));

    const object = new RedNosedReports();
    console.log(`part 1  ${object.executePart1(transformedInputs)}`);  // 660
    console.log(`part 2  ${object.executePart2(transformedInputs)}`); /// 689
})();