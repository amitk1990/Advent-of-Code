const fs = require("fs");
const _ = require("lodash");

class CubeConundrum {
    transformInput(gameInput) {
        const transformedGameInput = {};
        gameInput.map(item => item.trim(" ").split(":"))
        .map((transformed) => {
            const gameId = transformed[0].split(" ")[1];
            const transformedInput = {};
            transformed[1]
                .split(";")
                .map((game) => {
                    const balls = game.split(",").map(item => item.split(" ").filter(item => item != ''));
                    // [ [ '3', 'blue' ], [ '14', 'red' ], [ '2', 'green' ] ]
                    balls.map(ball => {
                        const numberOfBalls = _.get(transformedInput, ball[1], []);
                        _.set(transformedInput, ball[1], numberOfBalls.concat(parseInt(ball[0], 10)))
                    });
                });

            _.set(transformedGameInput, gameId, transformedInput);
        });

        return transformedGameInput;
    }

    sumOfCubeIds(gameInput, gamePossibleCondition) {
        const transformedGameInput = this.transformInput(gameInput);
        const ballTypes = Object.keys(gamePossibleCondition);
        let part1 = 0;

        _.map(transformedGameInput, (item, index) => {
            let not_possible = false;
            ballTypes.forEach(ballType => {
                const ballCounts =  gamePossibleCondition[ballType];
                const combinationOfballs = item[ballType];
                for (let ball of combinationOfballs) {
                    if (ball > ballCounts) {
                        not_possible = true;
                    }
                }
            });

            if (!not_possible) {
                part1 += parseInt(index, 10);
            }
        });

        return {
            part1,
            part2: this.sumOfCubeIdsWithFewCubes(transformedGameInput, ballTypes)
        }
    }

    sumOfCubeIdsWithFewCubes(transformedGameInput, ballTypes) {
        let part2 = 0
        _.map(transformedGameInput, item => {
            let cubesMultiple = 1;
            ballTypes.forEach(ballType => { cubesMultiple *= Math.min(_.max(item[ballType]), _.sum(item[ballType])); });
            part2 += cubesMultiple;
        });

        return part2;
    }
}

(function execute() {
    const object = new CubeConundrum();
    const gameInput = fs.readFileSync("./day02/test/test1.txt", "utf-8").split(/\r?\n/);
    // input conditions
    const gamePossibleCondition = { "red": 12, "green": 13, "blue": 14 };
    const {part1, part2 } = object.sumOfCubeIds(gameInput, gamePossibleCondition)
    console.log(part1, part2); // 3099,  72970
})()