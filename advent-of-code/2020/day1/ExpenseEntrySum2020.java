package day1;

import java.util.*;
import java.io.*;

/**
 * ExpenseEntrySum2020
 */
public class ExpenseEntrySum2020 {
  Set<Integer> set = new HashSet<>();
  public int ExpenseEntry2Sum(int num, int target) {
    if (set.contains(target-num)) {
      return (target-num)*num;
    }
    set.add(num);

    return -1;
  }
  public static void main(String[] args) {
    try {
      ExpenseEntrySum2020 x = new ExpenseEntrySum2020();
      File myObject = new File("/Users/amitkarunakaran/Desktop/coding/united/advent-of-code/2020/day1/input.txt");
      Scanner myReader = new Scanner(myObject);

      int result  = 0;
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        int input = Integer.parseInt(data);
        result = x.ExpenseEntry2Sum(input, 2020);
        if (result > 0) {
          System.out.println(result);
          break;
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}