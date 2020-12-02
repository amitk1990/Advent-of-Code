import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sun.tools.java.Scanner;

/**
 * MazeEscape
(0) 3  0  1  -3  - before we have taken any steps.
(1) 3  0  1  -3  - jump with offset 0 (that is, don't jump at all). Fortunately, the instruction is then incremented to 1.
 2 (3) 0  1  -3  - step forward because of the instruction we just modified. The first instruction is incremented again, now to 2.
 2  4  0  1 (-3) - jump all the way to the end; leave a 4 behind.
 2 (4) 0  1  -2  - go back to where we just were; increment -3 to -2.
 2  5  0  1  -2  - jump 4 steps forward, escaping the maze.
 */
public class MazeEscape {
  public int findStepsToEscapeMaze(int[] nums) {
    int i = 0;
    int n = nums.length;
    int count = 1;
    while (i < n) {
      count++;
      if (nums[i] == 0) {
        nums[i] = 1;
      } else if (nums[i] > 0) {
        int temp = nums[i];
        nums[i]++;
        i += temp;
      } else {
        int temp = nums[i];
        nums[i]++;
        i -= temp;
      }
    }

    return count;
  }

  public static void main(String[] args) {
    MazeEscape x = new MazeEscape();
    int[] input = new int[]{0, 3, 0, 1, -3};
    List<Integer> steps = new ArrayList<String>()
    try {
      File myObject = new File("/Users/amitkarunakaran/Desktop/coding/united/advent-of-code/2017/day4/test.txt");
      Scanner myReader = new Scanner(myObject);
  
      while (myReader.hasNextLine()) {
        String s = myReader.next();
        System.out.println("OUT " + s);
        steps.add(Integer.parseInt(s));
      }
      
      int resultForList = x.findStepsToEscapeMaze(steps);
      int result = x.findStepsToEscapeMaze(input);
      System.out.println("Steps to escape the maze" + result);
    } catch (FileNotFoundException e) {
      System.out.println("File not "+ e);
    }
  }  
}