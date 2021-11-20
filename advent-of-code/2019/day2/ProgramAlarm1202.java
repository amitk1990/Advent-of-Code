package day2;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ProgramAlarm1202
 */
public class ProgramAlarm1202 {
    private void calculateTypeOfOperand(int operand, int positionX, int positionY, int outputPosition, List<Integer> result) {
        switch (operand) {
            case 1: {
                // add
                int element = result.get(positionX) + result.get(positionY);
                result.set(outputPosition, element);
                return;
            }

            case 2: {
                // multiply
                int element = result.get(positionX) * result.get(positionY);
                result.set(outputPosition, element);
                return;
            }
            case 99: {
                return;
            }
        }
    }

    public List<Integer> calculateState(List<Integer> input) {
        int index = 0;
        while (input.get(index) != 99) {
            int operand = input.get(index);
            int positionX = input.get(index + 1);
            int positionY = input.get(index + 2);
            int outputPosition = input.get(index + 3);
            this.calculateTypeOfOperand(operand, positionX, positionY, outputPosition, input);
            index += 4;
        }
        return input;
    }

    public int findPair(List<Integer> originalInput, int expectedOutput) {
        List<Integer> array = new ArrayList<Integer>(originalInput);
        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++ ) {
                array = new ArrayList<Integer>(originalInput);
                array.set(1, noun);
                array.set(2, verb);
                List<Integer> temp = this.calculateState(array);

                if (temp.get(0) == expectedOutput) {
                    // noun 52 verb 8
                    return 100*noun + verb;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        try {
            ProgramAlarm1202 obj = new ProgramAlarm1202();
            File file = new File("./2019/day2/input.txt");
            Scanner myReader = new Scanner(file);
            List<Integer> input = new ArrayList<Integer>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                input = Arrays.asList(data.split(",")).stream().map(Integer::valueOf).collect(Collectors.toList());
            }
            myReader.close();
            List<Integer> input2 = new ArrayList<>(input);
            input.set(1, 12);
            input.set(2, 2);
            List<Integer> result = obj.calculateState(input);
            System.out.println("part 1 " + result.get(0)); // 5866714
            int expectedOutput = 19690720;
            System.out.println("part 2 " + obj.findPair(input2, expectedOutput)); // 5208
        }  catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}