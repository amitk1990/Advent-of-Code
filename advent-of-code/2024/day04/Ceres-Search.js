const fs = require("fs");

class CeresSearch {
    // PART 1
    executePart1(input) {
        const rows = input.length;
        const columns = input[0].length;
        let counter = 0;

        const search = (input, i, j) => {
            // top
            if ((i-3) >= 0) {
                if (input[i][j] === 'X' && input[i-1][j] === 'M' && input[i-2][j] === 'A' && input[i-3][j] === 'S' ) {
                    counter += 1;
                }
            }

            // left
            if ((j-3) >= 0) {
                if (input[i][j] === 'X' && input[i][j-1] === 'M' && input[i][j-2] === 'A' && input[i][j-3] === 'S') {
                    counter += 1;
                }
            }

            // bottom
            if ((i + 3) < rows) {
                if (input[i][j] === 'X' && input[i+1][j] === 'M' && input[i+2][j] === 'A' && input[i+3][j] === 'S' ) {
                    counter += 1;
                }
            }

            // right
            if ((j+3) < columns) {
                if (input[i][j] === 'X' && input[i][j+1] === 'M' && input[i][j+2] === 'A' && input[i][j+3] === 'S') {
                    counter += 1;
                }
            }

            // top left
            if ((i-3) >= 0 && (j-3) >= 0) {
                if (input[i][j] === 'X' && input[i-1][j-1] === 'M' && input[i-2][j-2] === 'A' && input[i-3][j-3] === 'S') {
                    counter += 1;
                }
            }

            // bottom right
            if ((i+3) < rows && (j+3) < columns) {
                if (input[i][j] === 'X' && input[i+1][j+1] === 'M' && input[i+2][j+2] === 'A' && input[i+3][j+3] === 'S') {
                    counter += 1;
                }
            }

            // bottom left
            if ((i + 3) < rows && (j-3) >= 0) {
                if (input[i][j]  === 'X' && input[i+1][j-1] === 'M'&& input[i+2][j-2] === 'A' && input[i+3][j-3] === 'S') {
                    counter += 1;
                }
            }

            // top right
            if ((i-3) >= 0 && (j+3) < columns) {
                if (input[i][j]  === 'X' && input[i-1][j+1] === 'M'&& input[i-2][j+2] === 'A' && input[i-3][j+3] === 'S') {
                    counter += 1;
                }
            }
        };

        for (let i = 0; i < rows; i++) {
            for (let j = 0; j < columns; j++) {
                if (input[i][j] === 'X') {
                    search(input, i, j);
                }
            }
        }

        return counter;
    }

    executePart2(input) {
        const rows = input.length;
        const columns = input[0].length;
        let counter = 0;

        const search = (input, i, j) => {
            if (
                // top left
                (i-1) >= 0 && j-1 >=0
                // bottom right
                && (i+1) < rows && (j+1) < columns
                // bottom left
                && (i+1) < rows && (j-1) >= 0
                // top right
                && (i-1 >= 0 && j+1 < columns)
            ) {
                // checking for MAS formation
                // M . S
                // . A .
                // M . S
                if (
                    ((input[i-1][j-1] === 'M' && input[i+1][j+1] === 'S')
                    ||
                    (input[i-1][j-1] === 'S' && input[i+1][j+1] === 'M'))
                    &&
                    ((input[i+1][j-1] === 'M' && input[i-1][j+1] === 'S')
                        ||
                        (input[i-1][j+1] === 'M' && input[i+1][j-1] === 'S'))
                ) {
                    counter += 1;
                }
            }
        }
        for (let i = 0; i < rows; i++) {
            for (let j = 0; j < columns; j++) {
                if (input[i][j] === 'A') {
                    search(input, i, j);
                }
            }
        }

        return counter;
    }
}



(function () {
    const input1 = fs.readFileSync("./data/input.txt", "utf-8").split(/\r?\n/).map(item => item.split(''));

    console.log(`number of times found - ${new CeresSearch().executePart1(input1)}`); // 2493
    console.log(`number of Mas found - ${new CeresSearch().executePart2(input1)}`); // 1890
})();