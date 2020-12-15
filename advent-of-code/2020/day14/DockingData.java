import java.util.*;
import java.io.*;

class Operands  {
  int leftOperand;
  int rightOperand;
  Operands(int i, int j) {
    this.leftOperand = i;
    this.rightOperand = j;
  }
}

/**
 * DockingData
 */
public class DockingData {

  Map<Integer, Long> memoryMap = new HashMap<>();
  String mask = "";

  public Operands parseMemoryAssignment(String input) {
    String[] expressions = input.split("=");
    int memoryAddress = Integer.parseInt(expressions[0].substring(4, expressions[0].length() - 2));
    int memoryValue = Integer.parseInt(expressions[1].trim());
    return new Operands(memoryAddress, memoryValue);
  }


  public void compute(Operands op) {
    String unsigned36Bit = Integer.toBinaryString(op.rightOperand);
    StringBuffer sb = new StringBuffer(unsigned36Bit);

    while (sb.length() != 36) {
      sb.insert(0, "0");
    }
    unsigned36Bit = sb.toString();
    char[] masks = mask.toCharArray();
    StringBuffer temp = new StringBuffer(unsigned36Bit);
    for (int i = 0; i < masks.length; i++) {
      if (masks[i] != 'X') {
        temp.setCharAt(i, masks[i]);
      }
    }
    unsigned36Bit = temp.toString();
    long result = Long.parseLong(unsigned36Bit, 2);
    // System.out.println("Memory address" + op.leftOperand + " value"  + result);
    memoryMap.put(op.leftOperand, result);
  }

  public long computeValuesInMemory(List<String> inputs) {
    for (String input : inputs) {
      if (input.indexOf("mask") == 0) {
        // parse mask
        mask = input.split("=")[1].trim();
      }  else {
        // parse memory
        // System.err.println(input);
        Operands temp = parseMemoryAssignment(input);
        compute(temp);
      }
    }

    long result = 0;

    for (long val : memoryMap.values()) {
      result += val;
    }

    return result;
  }

  public static void main(String[] args) {
    DockingData test = new DockingData();
    try {
      File myObject = new File("advent-of-code/2020/day14/test2.txt");
      Scanner myReader = new Scanner(myObject);
  
      List<String> input = new ArrayList<>();
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        input.add(data);
      }

      long result = test.computeValuesInMemory(input);
      System.out.println("Part 1 " + result);

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}