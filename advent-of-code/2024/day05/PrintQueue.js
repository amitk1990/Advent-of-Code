const fs = require("fs");

class PrintQueue {

    parsePrinterSequence(sequence) {
        const printerSequence = [...sequence];

        return printerSequence.map((item) => {
            return item.split(',').map(item => parseInt(item, 10));
        });
    }

    middlePageNumber(item) {
         const index = Math.floor((item.length - 0)/2);

         return item[index];
    }

    transformInput(sequence) {
        const splitIndex = sequence.findIndex(item => {
            return item === ''
        });

        return [sequence.slice(0, splitIndex), sequence.slice(splitIndex + 1)];
    }

    parseOrderSequence(item) {
        item.split('|').map(item => parseInt(item, 10));
    }

    parseOrdering(ordering) {
        const pageOrdering = new Map();
        ordering.forEach(item => {
            const [key, value] =  item.split('|').map(item => parseInt(item, 10));

            if (!pageOrdering.has(key)) {
                pageOrdering.set(key, []);
            }
            const current = pageOrdering.get(key);
            current.push(value);
            pageOrdering.set(key, current);
        });

        return pageOrdering;
    }

    checkAndFixOrdering(transformedSeqs, pageOrdering, swapElementsEnabled) {
        let incorrect = [], correct = [];
        for (const printerSequence of transformedSeqs) {
            let correction = false;
            for (let i = 1; i < printerSequence.length; i++) {
                const currentItem = printerSequence[i];
                const prevItem = printerSequence[i-1];
                if (pageOrdering.has(prevItem)) {
                    const configPageOrdering = pageOrdering.get(prevItem);
                    if (!configPageOrdering.includes(currentItem)) {

                        if (pageOrdering.has(currentItem) && pageOrdering.get(currentItem).includes(prevItem)) {
                            correction = true;
                            // swap elements which are not in sequence
                            if (swapElementsEnabled) {
                                const temp = prevItem;
                                printerSequence[i-1] = currentItem;
                                printerSequence[i] = temp;
                            }
                        }
                    }
                } else {
                    correction = true;
                    // swap elements which are not in sequence
                    if (swapElementsEnabled) {
                        const temp = prevItem;
                        printerSequence[i-1] = currentItem;
                        printerSequence[i] = temp;
                    }
                }
            }
            // group the elements which are ordered and not ordered.
            correction ? incorrect.push(printerSequence) : correct.push(printerSequence);
        }

        return [incorrect, correct];
    }

    executePart2(input) {
        const [ordering, seq] = this.transformInput(input);
        const pageOrdering = this.parseOrdering(ordering);
        let transformedSeqs = this.parsePrinterSequence(seq);
        let middlePageNumber = 0;

        do {
            const [correctedSeq, _] = this.checkAndFixOrdering(transformedSeqs, pageOrdering, true);
            const [incorrect, correct] = this.checkAndFixOrdering(correctedSeq, pageOrdering, false);

            correct.forEach((correctSeq) => {
                middlePageNumber += this.middlePageNumber(correctSeq, false);
            });
            transformedSeqs = incorrect;
        } while (transformedSeqs.length !== 0);

        return middlePageNumber;
    }

    executePart1(input) {
        const [ordering, seq] = this.transformInput(input);
        const pageOrdering = this.parseOrdering(ordering);
        const transformedSeqs = this.parsePrinterSequence(seq);

        let middlePageNumber = 0;
        for (const printerSequence of transformedSeqs) {
            let notOrdered = false;
            for (let i = 0; i < printerSequence.length - 1; i++) {
                let seq = printerSequence[i];
                let correctOrder = [];

                if (pageOrdering.has(seq)) {
                    correctOrder = pageOrdering.get(seq);
                } else if (i !== printerSequence.length - 1) {
                    notOrdered = true;
                    break;
                } else {
                   correctOrder = [];
                }

                for (let j = i + 1; j < printerSequence.length; j++) {
                    if (!correctOrder.includes(printerSequence[j])) {
                        notOrdered = true;
                        break;
                    }
                }
            }

            if (!notOrdered) {
                middlePageNumber += this.middlePageNumber(printerSequence);
            }
        }

        return middlePageNumber;
    }
}


(function () {
    const input = fs.readFileSync("./data/input.txt", "utf-8").split(/\r?\n/);

    console.log(`part 1 ${new PrintQueue().executePart1(input)}`); // 4774
    console.log(`part 2 ${new PrintQueue().executePart2(input)}`); // 6004
})();