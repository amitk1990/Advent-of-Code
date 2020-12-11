import java.util.*;
import java.io.*;
/**
 * SeatingSystem
 */
public class SeatingSystem {

// # occupied 
// L empty
  int row = 0;
  int col = 0;
  // all 8 direction 
  int[][] directions = new int[][]{
    {1, 0},
    {-1,0},
    {0, 1},
    {0, -1},
    {1, 1},
    {1, -1},
    {-1, 1},
    {-1, -1}
  };
  // check to see if there is occupied seat around me.
  public boolean canOccupy(int i, int j, List<char[]> input) {
    for (int[] direction: directions) {
      int newX = i + direction[0];
      int newY = j + direction[1];
      if (newX >= 0 && newY >= 0 && newX < row && newY < col && input.get(newX)[newY] == '#' ) {
        return false;
      }
    }
    return true;
  }

  // there should be 4 or more occupied seats adjacent to me, then make that empty
  public boolean canBeEmpty(int i, int j, List<char[]> input) {
    int count = 0;
    for (int[] direction: directions) {
      int newX = i + direction[0];
      int newY = j + direction[1];
      if (newX >= 0 && newY >= 0 && newX < row && newY < col && input.get(newX)[newY] == '#') {
        count++;
      }
    }
    return (count >=4);
  }

  public List<char[]> updateSeats(List<char[]> input, boolean[][] seatChange ) {
      for (int i = 0; i < row; i++) {
        char[] seats = input.get(i);
        for (int j = 0; j < col; j++) {
          if (seatChange[i][j]) {
            seats[j] = (seats[j] == 'L') ? '#' : 'L';
            input.set(i, seats);
          }
        }
      }

    return input;
  }
  // count occupiedSeats
  public int countOccupiedSeats(List<char[]> input) {
    int result = 0;

    for (int i = 0; i < row; i++) {
      char[] seats = input.get(i);
      for (int j = 0; j < col; j++) {
        if (seats[j] == '#') {
          result++;
        }
      }
    }

    return result;
  }
  // part 1
  public int findOccupiedSeatsPart1(List<char[]> input) {
    row = input.size();
    col = input.get(0).length;

    boolean chaos = false;
    do {
      boolean[][] seatChange  = new boolean[row][col];
      chaos = false;
      for (int i = 0; i < row; i++) {
        char[] seats = input.get(i);
        for (int j = 0; j < col; j++) {
          char seat  = input.get(i)[j];
          if (seat == '.') {
            seatChange[i][j] = false;
            continue;
          } else if (seat == 'L') {
            if (canOccupy(i, j, input)) {
              chaos = true;
              seatChange[i][j] = true;
            }
          } else if (seat == '#') {
            if (canBeEmpty(i, j, input)) {
              chaos = true;
              seatChange[i][j] = true;
            }
          }
        }
      }

      input = updateSeats(input, seatChange);

    } while(chaos);

    return countOccupiedSeats(input);
  }

  // has occupied seat # in 5 out of 8 directions, if empty seat 'L' found break
  public boolean hasFiveOccupiedSeats(int x, int y,  List<char[]> input) {
    int count = 0;
    for (int[] direction : directions) {
      int newX = x;
      int newY = y;
      while (newX + direction[0] >=0 & newY + direction[1] >= 0 && newX + direction[0] < row && newY + direction[1] < col) {
        newX += direction[0];
        newY += direction[1];

        char seat = input.get(newX)[newY];

        if (seat == '#') {
          count++;
          break;
        }
        if (seat == 'L') {
          break;
        }
      }

      if (count >= 5) return true;
    }

    return false;
  }

  // first seat
  // there are no # all directions then convert l -> #
  public boolean findFirstSeatCanSeeInAllDirection(int x, int y, List<char[]> input) {
    char firstSeat = ' ';
    int distance = Integer.MAX_VALUE;
    for (int[] direction : directions) {
      char seat = ' ';
      int newX = x;
      int newY = y;

      while (newX + direction[0] >=0 & newY + direction[1] >= 0 && newX + direction[0] < row && newY + direction[1] < col) {
        newX += direction[0];
        newY += direction[1];

        seat = input.get(newX)[newY];
        if (seat == '#') {
          return false;
        }
        if (seat == '.') {
          continue;
        }
        if (seat == 'L') {
          break;
        }
      }
    }
    return true;
  }

  // part 2
  public int findOccupiedSeatsPart2(List<char[]> input) {
    row = input.size();
    col = input.get(0).length;
    boolean chaos = false;

    do {
      char[][] seatChange  = new char[row][col];
      chaos = false;
      for (int i = 0; i < row; i++) {
        char[] seats = input.get(i);
        for (int j = 0; j < col; j++) {
          char seat  = input.get(i)[j];
          if (seat == 'L' && findFirstSeatCanSeeInAllDirection(i, j, input)) {
              chaos = true;
              seatChange[i][j] = '#';
          } else if (seat == '#' && hasFiveOccupiedSeats(i, j, input)) {
              chaos = true;
              seatChange[i][j] = 'L';
          }
        }
      }

      for (int i = 0; i < row; i++) {
        char[] seats = input.get(i);
        for (int j = 0; j < col; j++) {
          if (seatChange[i][j] == 'L' || seatChange[i][j] == '#') {
            seats[j] = seatChange[i][j];
            input.set(i, seats);
          }
        }
      }
    } while(chaos);

    return countOccupiedSeats(input);
  }

  
  public static void main(String[] args) {
    SeatingSystem ss = new SeatingSystem();

    try {
      File myObject = new File("advent-of-code/2020/day11/input.txt");
      Scanner myReader = new Scanner(myObject);

      List<char[]> input = new ArrayList<>();
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        data.trim();
        input.add(data.toCharArray());
      }
      // deep copy 
      List<char[]> testInputPart2 = new ArrayList<>();
      
      for (char[] c : input) {
        char[] newCh = new char[c.length];

        for (int i = 0; i < c.length; i++) {
          newCh[i] = c[i];
        }
        testInputPart2.add(newCh);
      }

      int count = ss.findOccupiedSeatsPart1(input);
      System.out.println("Part 1 " + count + "seats");
      
      int seatsOccupied = ss.findOccupiedSeatsPart2(testInputPart2);
      System.out.println("Part 2 " + seatsOccupied + "seats");
      // Part 1 2441seats
      // Part 2 2190seats

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}