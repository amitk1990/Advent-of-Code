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
public class DockingDataII {

  Map<Long, Long> memoryMap = new HashMap<>();
  String mask = "";

  public Operands parseMemoryAssignment(String input) {
    String[] expressions = input.split("=");
    int memoryAddress = Integer.parseInt(expressions[0].substring(4, expressions[0].length() - 2));
    int memoryValue = Integer.parseInt(expressions[1].trim());
  
    return new Operands(memoryAddress, memoryValue);
  }

  public void generateAllBinaryStrings(int n, List<String> result, StringBuffer sb, int i) { 
    if (i == n){ 
      result.add(sb.toString());
      return; 
    } 

    sb.append(0);
    generateAllBinaryStrings(n, result, sb, i + 1); 
    sb.setLength(sb.length() - 1);

    sb.append(1);
    generateAllBinaryStrings(n, result, sb, i + 1); 
    sb.setLength(sb.length() - 1);
  }

  // address: 000000000000000000000000000000101010  (decimal 42)
  // mask:    000000000000000000000000000000X1001X
  // result:  000000000000000000000000000000X1101X
  public void computeInstruction(int leftOperand, long rightResult) {
      char[] masks = mask.toCharArray();
      String leftOperand36bit = convertTo36bit(Integer.toBinaryString(leftOperand));
      StringBuffer temp = new StringBuffer(leftOperand36bit);
      int occurrence = 0;

      for (int i = 0; i < masks.length; i++) {
        if (masks[i] != 'X') {
          // 1 overwritten
          if (masks[i] == '1') {
            temp.setCharAt(i, masks[i]);
          }
          // 0 unchanged
        } else {
          // X, Put X and find total number of x's to generate combination pattern
          temp.setCharAt(i, 'X');
          occurrence++;
        }
      }

      if (occurrence > 0) {
        List<String> result = new ArrayList<String>();
        generateAllBinaryStrings(occurrence, result, new StringBuffer(), 0);
        for (String combination : result) {
          StringBuffer sb = new StringBuffer(temp);
          char[] ch = combination.toCharArray();
          int k = 0;
          for (int j = 0; j < sb.length(); j++) {
            if (sb.charAt(j) == 'X') {
              sb.setCharAt(j, ch[k++]);;
            }
          }
          long output = Long.parseLong(sb.toString(), 2);
          memoryMap.put(output, rightResult);
        }
      } else {
        long output = Long.parseLong(temp.toString(), 2);
        memoryMap.put(output, rightResult);
      }
  }

  // helper to convert to 36 bit
  public String convertTo36bit(String unsigned36Bit) {
    StringBuffer sb = new StringBuffer(unsigned36Bit);

    while (sb.length() != 36) {
      sb.insert(0, "0");
    }

    return sb.toString();
  }

  // solving part 2
  public long computeValuesInMemoryPart2(List<String> inputs) {
    for (String input : inputs) {
      if (input.indexOf("mask") == 0) {
        // parse mask
        mask = input.split("=")[1].trim();
      }  else {
        // parse memory instruction
        Operands op = parseMemoryAssignment(input);
        computeInstruction(op.leftOperand, new Long(op.rightOperand));
      }
    }

    long result = 0;

    for (long val : memoryMap.values()) {
      result += val;
    }

    return result;
  }

  public static void main(String[] args) {
    DockingDataII test = new DockingDataII();
    try {
      File myObject = new File("advent-of-code/2020/day14/input.txt");
      Scanner myReader = new Scanner(myObject);
  
      List<String> input = new ArrayList<>();
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        input.add(data);
      }

      long result = test.computeValuesInMemoryPart2(input);
      System.out.println("Part 2 " + result);

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}