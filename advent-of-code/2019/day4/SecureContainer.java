import java.lang.*;
import java.util.*;
/**
 * SecureContainer
 */
public class SecureContainer {
  // test input range
  int UPPER_LIMIT = 824795;
  int LOWER_LIMIT = 278384;

  public boolean isValidLength(String number) {
    return (number.length() == 6);
  }

  public boolean withinRange(int number) {
    return (this.LOWER_LIMIT < number  && this.UPPER_LIMIT > number);
  }

  public boolean verifyDigitsAreIncreasingOrSame(int number) {
    String input = number+"";

    for (int i = 1; i < input.length(); i++) {
      int current = Character.getNumericValue(input.charAt(i));
      int previous = Character.getNumericValue(input.charAt(i-1));

      if (current < previous) {
        return false;
      }
    }

    return true;
  }

  public boolean isAdjacentDigitsSamePart1(int digit) {
    String input = digit + "";
    int adjacentSame = 0;
    for (int i = 1; i < input.length(); i++) {
      int current = Character.getNumericValue(input.charAt(i));
      int previous = Character.getNumericValue(input.charAt(i-1));

      if (current == previous) {
        adjacentSame += 1;
      }
    }

    return (adjacentSame >= 1);
  }

  public boolean isAdjacentDigitsSamePart2(int digit) {
    int[] map = new int[10];
    String input = digit + "";
    for (int i = 0; i < input.length(); i++) {
        map[input.charAt(i)-'0'] += 1;
    }

    for (int i = 0; i < map.length; i++) {
      if (map[i] == 2) {
        return true;
      }
    }

    return false;
  }

  public int execute(int[] input) {
    int lowerLimit = input[0];
    int upperLimit = input[1];
    int count = 0;

    while (lowerLimit <= upperLimit) {
      if (this.isValidLength(String.valueOf(lowerLimit)) 
          && this.withinRange(lowerLimit)
          && this.verifyDigitsAreIncreasingOrSame(lowerLimit)
          // changes here in part 1
          && this.isAdjacentDigitsSamePart1(lowerLimit)) {
        count += 1;
      }
      lowerLimit += 1;
    }

    return count;
  }

  public int execute2(int[] input) {
    int lowerLimit = input[0];
    int upperLimit = input[1];
    int count = 0;

    while (lowerLimit <= upperLimit) {
          if (this.isValidLength(String.valueOf(lowerLimit)) 
          && this.withinRange(lowerLimit) 
          && this.verifyDigitsAreIncreasingOrSame(lowerLimit)
          // changes here in part2 
          && this.isAdjacentDigitsSamePart2(lowerLimit)) {
        count += 1;
      }
      lowerLimit += 1;
    }

    return count;
  }

  public static void main(String[] args) {
    SecureContainer object = new SecureContainer();
    // test- input
    int[] input = new int[]{278384, 824795};
    // int[] input = new int[]{111122, 111122};
    System.out.println("Different password within range " + object.execute(input)); // 921
    System.out.println("Different password within range " + object.execute2(input)); // 603
    // Different password within range 921
    // Different password within range 603
  }
}