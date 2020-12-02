package day1;

import java.util.*;
import java.io.*;

/**
 * ExpenseEntry3Sum
 */
public class ExpenseEntry3Sum {

  public long calculate3Sum(List<Integer> list, int target) {
    Collections.sort(list);

    for (int i = 0; i < list.size() - 2; i++) {
      int first = i + 1;
      int end = list.size() - 1;

      while (first < end) {
        int sum = list.get(first) + list.get(end) + list.get(i);
        if (sum == target) return (long) list.get(first) * list.get(end) * list.get(i);
        if (sum > target) {
          end--;
        } else {
          first++;
        }
      }
    }

    return -1;
  }

  public static void main(String[] args) {
    try {
      ExpenseEntry3Sum x = new ExpenseEntry3Sum();
      File myObject = new File("/Users/amitkarunakaran/Desktop/coding/united/advent-of-code/2020/day1/input.txt");
      Scanner myReader = new Scanner(myObject);

      List<Integer> list = new ArrayList<>();
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        int input = Integer.parseInt(data);
        list.add(input);
      }

      long result = x.calculate3Sum(list, 2020);
      System.out.println("RESULT" +  result);
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}