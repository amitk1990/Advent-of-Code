package toboggantrajectory;

import java.util.*;
import java.io.*;

/**
 * TobogganTrajectory
 */
public class TobogganTrajectory {
  public int countTrees(List<char[]> map, int horizontalMovement, int verticalMovement) {
      int y = 0;
      int x = 0;
      char[] row = map.get(x);
      int rowLength = row.length;
      int tree = 0;

      while (y < map.size() ) {
          char X = map.get(y)[x];

          if (y > 0 && X == '#') { // Tree found
              tree++;
          }

          x = (x+horizontalMovement)%rowLength;
          y += verticalMovement;
      }
      
      return tree;
  }
  
  public static void main(String[] args) {
      TobogganTrajectory x = new TobogganTrajectory();

      try {
        File myObject = new File("advent-of-code/2020/day3/input.txt");
        Scanner myReader = new Scanner(myObject);

        List<char[]> input = new ArrayList<>();
        while (myReader.hasNextLine()) {
          String data = myReader.nextLine();
          data.trim();
          input.add(data.toCharArray());
        }
        int[][] slopes = new int[][]{
          {1, 1},
          {3, 1},
          {5, 1},
          {7, 1},
          {1, 2}
        };
        //  part 1
        int count = x.countTrees(input, slopes[1][0], slopes[1][1]);

        System.out.println("Part 1 " + count);
        // part 2 
        int part2Result = 1;
        for (int[] slope : slopes) {
          part2Result *= x.countTrees(input, slope[0], slope[1]);
        }
        System.out.println("Part 2 " + part2Result);
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
  }
}