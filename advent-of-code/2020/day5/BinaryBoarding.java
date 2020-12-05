import java.util.*;
import java.io.*;
/**
 * BinaryBoarding
 */
public class BinaryBoarding {

  public int findSeatGrid(String map,int first, int last, char lowerHalf, char upperHalf) {
    int low = first;
    int high = last; // total seats
    int index = 0;
    int MAP_SIZE = map.length(); // map size to look at

    while (low <= high) {
      int mid = (low + high)/2;
      if (map.charAt(index) == lowerHalf) {
        high = mid;
        if (index + 1 == MAP_SIZE) {
          return high;
        }
      } else if (map.charAt(index) == upperHalf) {
        low = mid+1;
        if (index + 1 == MAP_SIZE) {
          return low;
        }
      }
      index++;
    }

    return -1;
  }

  public int findSeatId(String input) {
    int row = findSeatGrid(input.substring(0, 7), 0, 127, 'F', 'B');
    int column = findSeatGrid(input.substring(7), 0, 7, 'L', 'R');

    if (row == -1 || column == -1) {
      System.out.println("ERROR");
    }

    return row*8 + column;
  }

  public static void main(String[] args) {
    try {
      BinaryBoarding bb = new BinaryBoarding();
      File myObject = new File("advent-of-code/2020/day5/input.txt");
      Scanner myReader = new Scanner(myObject);

      int max = Integer.MIN_VALUE;
      List<Integer> list = new ArrayList<>();
      // PART 1
      while (myReader.hasNextLine()) {
        String input = myReader.nextLine();
        int seatId = bb.findSeatId(input);
        list.add(seatId);
        max = Math.max(max, seatId);
      }
      System.out.println("PART 1: MAXIMUM SEAT ID " +  max);
      // PART 2
      Collections.sort(list);
      for (int i = list.get(0); i < list.size() - 1; i++) {
        if (!list.contains(i)) {
          System.out.println("PART 2: SEAT FOUND " + i);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}