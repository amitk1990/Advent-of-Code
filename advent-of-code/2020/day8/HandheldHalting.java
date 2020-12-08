import java.util.*;
import java.io.*;

/**
 * HandheldHalting
 */
public class HandheldHalting {
  public class Output {
    int accumulator = 0;
    boolean valid = false;

    Output(int result, boolean valid) {
        accumulator = 0;
        valid = false;
    }
  }

  public Output isConsoleFixed(List<String> input, Output result) {
    int i = 0;
    int n  = input.size();
    Set<Integer> visited = new HashSet<>();

    while (i < n){
        String[] instruction = input.get(i).split(" ");
        String type = instruction[0].trim().toLowerCase();
        String action = instruction[1].trim().toLowerCase();

        switch (type) {
            case "nop":
                if (visited.add(i)) {
                    i++;
                    break;
                }
                result.valid = false;
                return result;
            case "acc":
                if (visited.add(i)) {
                    result.accumulator += Integer.parseInt(action);
                    i++;                           
                    break;
                }
                result.valid = false;
                return result;
                
            case "jmp":
                if (visited.add(i)) {
                    i += Integer.parseInt(action);
                    break;
                }
                result.valid = false;
                return result;
            default:
                System.out.println("ERROR");
                result.valid = false;
                return result;
                
        }
        
    }
    result.valid = true;
    return result;
}

  public Output fixInstructions(List<String> input, Set<Integer> jmpNopInstructionIndices) {
    for (int index : jmpNopInstructionIndices) {
        List<String> instructions = new ArrayList<>(input);
        String instruction = instructions.get(index);
        if(instruction.contains("nop"))  {
            instructions.set(index, instruction.replace("nop", "jmp"));
        } else if (instruction.contains("jmp")) {
            instructions.set(index, instruction.replace("jmp", "nop"));
        }
        // try out with each fixed instruction
        Output result = new Output(0, false);
        Output response = isConsoleFixed(instructions, result);

        if (response.valid) {
            return response;
        }
    }
    return new Output(0, false);
  }

  public static void main(String[] args) {
      try {
        File myObject = new File("advent-of-code/2020/day8/input.txt");
        Scanner myReader = new Scanner(myObject);

        List<String> input = new ArrayList<>();
        Set<Integer> jmpNopInstructionIndices = new HashSet<>();
        int index = 0;

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data.contains("nop") || data.contains("jmp")) {
                // keep track of indices where there is nop || jmp for part 2
                jmpNopInstructionIndices.add(index);
            }
            index++;
            input.add(data);
        }
        myReader.close();
        HandheldHalting test = new HandheldHalting();
        Output object = test.new Output(0, false);

        Output result1 = test.isConsoleFixed(input, object);
        Output result2 = test.fixInstructions(input, jmpNopInstructionIndices);
        System.out.println("Part 1 " + result1.accumulator);
        System.out.println("Part 2 " + result2.accumulator);

      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
  }
}