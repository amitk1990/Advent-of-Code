package day2;

import java.util.*;
import java.io.*;
/**
 * PassportPhiloshophy
 */
public class PassportPhiloshophy {

  public boolean part1(String input) {
    String[] parsedInputs = input.split(" ");
    String range = parsedInputs[0];
    String character = parsedInputs[1];
    String testInput = parsedInputs[2];
    char[] parseCharacter = character.substring(0, character.length() - 1).toCharArray();
    String[] ranges = range.split("-");
    int minVal = Integer.parseInt(ranges[0]);
    int maxVal = Integer.parseInt(ranges[1]);
    
    int[] count = new int[26];
    for (char c : testInput.toCharArray()) {
        count[c - 'a']++;
    }
    
    return (count[parseCharacter[0] - 'a'] >= minVal) && (maxVal >= count[parseCharacter[0] - 'a']);
    
  }


  // 1-3 a: abcde is valid: position 1 contains a and position 3 does not.
  // 1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
  // 2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
  public boolean part2(String input) {
    String[] parsedInputs = input.split(" ");
    String range = parsedInputs[0];
    String character = parsedInputs[1];
    String testInput = parsedInputs[2];
    char[] parseCharacter = character.substring(0, character.length() - 1).toCharArray();
    String[] ranges = range.split("-");


    int minVal = Integer.parseInt(ranges[0]);
    int maxVal = Integer.parseInt(ranges[1]);

    return (testInput.charAt(minVal - 1) == parseCharacter[0] ^ testInput.charAt(maxVal - 1) == parseCharacter[0]);
  }


  public static void main(String[] args) {
    PassportPhiloshophy x = new PassportPhiloshophy();

    int result  = 0;
    int part2Result = 0;
    try {
      File myObject = new File("/Users/amitkarunakaran/Desktop/coding/united/advent-of-code/2020/day2/input.txt");
      Scanner myReader = new Scanner(myObject);

      
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        if (x.part1(data)) {
          result++;
        }

        if (x.part2(data)) {
          part2Result++;
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
      
      System.out.println("Part 1: " + result);
      System.out.println("Part 2: " + part2Result);
    }
}