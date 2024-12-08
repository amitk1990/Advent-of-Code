const fs = require("fs");


class GuardGallivant {
    GUARD = '^';
    OBSTRUCTIONS = '#';
    ADDED_OBSTRUCTION = 'O';
    ORIGINAL_MATRIX = [];
    DEADCOUNTER = 2;

    findPosition(input) {
        const row = input.length;
        const cols = input[0].length;
        for (let i = 0; i < row; i++) {
            for (let j = 0; j < cols; j++) {
                if (input[i][j] === this.GUARD) {
                    return [i, j];
                }
            }
        }
    }

    changeDirection(guardDirection) {
        if (guardDirection === "top") {
            return "right";
        }

        if (guardDirection === "right") {
            return "bottom";
        }

        if (guardDirection === "bottom") {
            return "left";
        }

        if (guardDirection === "left") {
            return "top";
        }
    }

    boundaryCheck(newX, newY, row, cols) {
        return (newX >= 0 && newX < row) && (newY >= 0 && newY < cols);
    }

    isObstacle(input, {
        newX,
        newY
    }) {
        return input[newX][newY] === this.OBSTRUCTIONS;
    }

    isNewObstruction(input, {
        newX,
        newY
    }) {
        return input[newX][newY] === this.ADDED_OBSTRUCTION;
    }

    countDistinctPositionGuardVisited(visited) {
        return visited.flat().filter(Boolean).length;
    }

    deepCloneArray() {
        return this.ORIGINAL_MATRIX.map(row => [...row]);
    }

    generateNewMap(i, j) {
        const labMap = this.deepCloneArray();
        labMap[i][j] = this.ADDED_OBSTRUCTION;

        return labMap;
    }

    // TODO: something is getting messed up in the position.
    // GUARD POSITION 9 58
    isGuardStuckInLoop(matrix) {
        const row = matrix.length;
        const cols = matrix[0].length;
        let guardDirection = "top";

        const [x, y] = this.findPosition(matrix);
        let deadlock = 0;

        let newX = x, newY = y;
        let newPositionX = 0, newPositionY = 0;
        matrix[newX][newY] = '.';


        while (this.boundaryCheck(newX, newY, row, cols)) {
            if (guardDirection === "top") {
                newPositionX = newX - 1;

                if (this.boundaryCheck(newPositionX, newY, row, cols)) {
                    if (this.isObstacle(matrix, { newX: newPositionX,  newY })) {
                        guardDirection = this.changeDirection(guardDirection);
                    } else if (this.isNewObstruction(matrix, {
                        newX: newPositionX,
                        newY
                    })) {
                        guardDirection = this.changeDirection(guardDirection);
                        deadlock += 1;

                        if (deadlock === this.DEADCOUNTER) {
                            return true;
                        }
                    } else {
                        newX = newPositionX;
                    }
                } else {
                    break;
                }

            } else if (guardDirection === "right") {
                // move right
                newPositionY = newY + 1;

                if (this.boundaryCheck(newX, newPositionY, row, cols)) {
                    if (this.isObstacle(matrix, { newX,  newY: newPositionY })) {
                        guardDirection = this.changeDirection(guardDirection);
                    } else if (this.isNewObstruction(matrix, {
                        newX,
                        newY: newPositionY
                    })) {
                        guardDirection = this.changeDirection(guardDirection);
                        deadlock += 1;

                        if (deadlock === this.DEADCOUNTER) {
                            return true;
                        }
                    } else {
                        newY = newPositionY;
                    }
                } else {
                    break;
                }
            } else if (guardDirection === "bottom") {
                newPositionX = newX + 1;

                if (this.boundaryCheck(newPositionX, newY, row, cols)) {
                    if (this.isObstacle(matrix, { newX: newPositionX,  newY })) {
                        guardDirection = this.changeDirection(guardDirection);
                    } else if (this.isNewObstruction(matrix, {
                        newX: newPositionX,
                        newY
                    })) {
                        guardDirection = this.changeDirection(guardDirection);
                        deadlock += 1;

                        if (deadlock === this.DEADCOUNTER) {
                            return true;
                        }
                    }  else {
                        newX = newPositionX;
                    }
                } else {
                    break;
                }
            } else if (guardDirection === "left") {
                newPositionY = newY - 1;

                if (this.boundaryCheck(newX, newPositionY, row, cols)) {
                    if (this.isObstacle(matrix, {newX, newY: newPositionY})) {
                        guardDirection = this.changeDirection(guardDirection);
                    } else if (this.isNewObstruction(matrix, {
                        newX,
                        newY: newPositionY
                    })) {
                        guardDirection = this.changeDirection(guardDirection);
                        deadlock += 1;

                        if (deadlock === this.DEADCOUNTER) {
                            return true;
                        }
                    }  else {
                        newY = newPositionY;
                    }
                } else {
                    break;
                }
            }
        }

        return false;
    }

    executePart2(input) {
        const row = input.length;
        const cols = input[0].length;

        this.ORIGINAL_MATRIX = input;
        let result = 0;

        // console.log(input[9][58]);
        // const labMapWithBlocker = this.generateNewMap(9, 58);
        // if (this.isGuardStuckInLoop(labMapWithBlocker)) {
        //     result += 1;
        // }


        let labMapWithBlocker = [];
        for (let i = 0; i < row; i++) {
            for (let j = 0; j < cols; j++) {
                if (input[i][j] !== this.GUARD && input[i][j] !== this.OBSTRUCTIONS) {
                    labMapWithBlocker = this.generateNewMap(i, j);

                    if (this.isGuardStuckInLoop(labMapWithBlocker)) {
                        result += 1;
                    }
                }
            }
        }

        return result;
    }

    executePart1(input) {
        let guardDirection = "top";
        const row = input.length;
        const cols = input[0].length;

        const [x, y] = this.findPosition(input);

        let visitedPath = Array.from({
            length: row
        }, () => {
            return new Array(cols).fill(false);
        });
        let newX = x, newY = y;
        let newPositionX = 0, newPositionY = 0;
        visitedPath[newX][newY] = true;
        input[newX][newY] = '.';
        // within the boundary
        while ((newX >= 0 && newX < row) && (newY >= 0 && newY < cols)) {
            if (guardDirection === "top") {
                newPositionX = newX-1;

                if (this.boundaryCheck(newPositionX, newY, row, cols)) {
                    if (this.isObstacle(input, { newX: newPositionX,  newY })) {
                        guardDirection = this.changeDirection(guardDirection);
                    } else {
                        newX = newPositionX;
                        visitedPath[newX][newY] = true;
                    }
                } else {
                    break;
                }

            } else if (guardDirection === "right") {
                // move right
                newPositionY = newY+1;

                if (this.boundaryCheck(newX, newPositionY, row, cols)) {
                    if (this.isObstacle(input, { newX,  newY: newPositionY })) {
                        guardDirection = this.changeDirection(guardDirection);
                    } else {
                        newY = newPositionY;
                        visitedPath[newX][newY] = true;
                    }
                } else {
                    break;
                }
            } else if (guardDirection === "bottom") {
                newPositionX = newX+1;

                if (this.boundaryCheck(newPositionX, newY, row, cols)) {
                    if (this.isObstacle(input, { newX: newPositionX,  newY })) {
                        guardDirection = this.changeDirection(guardDirection);
                    } else {
                        newX = newPositionX;
                        visitedPath[newX][newY] = true;
                    }
                } else {
                    break;
                }
            } else if (guardDirection === "left") {
                newPositionY = newY-1;

                if (this.boundaryCheck(newX, newPositionY, row, cols)) {
                    if (this.isObstacle(input, {newX, newY: newPositionY})) {
                        guardDirection = this.changeDirection(guardDirection);
                    } else {
                        newY = newPositionY;
                        visitedPath[ newX ][ newY ] = true;
                    }
                } else {
                    break;
                }
            }
        }

        return this.countDistinctPositionGuardVisited(visitedPath);
    }
}

(function () {
    const input = fs.readFileSync("./data/input.txt", "utf-8").split(/\r?\n/).map(item => item.split(''));

    console.log(`part 1 ${new GuardGallivant().executePart1(input)}`); // 5242
    console.log(`part 2 ${new GuardGallivant().executePart2(input)}`); // N/A
})();