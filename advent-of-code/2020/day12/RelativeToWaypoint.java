import java.util.*;
import java.lang.*;
import java.io.*;
/**
 * RainRiskII
 */
public class RelativeToWaypoint {
  int shipX; 
  int shipY;
  String directionX;
  String directionY;
  Map<String, String> directionToCoordinates;
  List<String[]> waypoints = new ArrayList<>();

  RelativeToWaypoint(int x, int y, String dx, String dy) {
    this.shipX = x;
    this.shipY = y;
    this.directionX = dx;
    this.directionY = dy;
    directionToCoordinates = new HashMap<>();
    directionToCoordinates.put("E", "X");
    directionToCoordinates.put("W", "X");
    directionToCoordinates.put("N", "Y");
    directionToCoordinates.put("S", "Y");
    waypoints.add(new String[]{
      "10", "E"
    });
    waypoints.add(new String[]{
      "1", "N"
    });
  }

  public List<String> parseInstruction(String instruction) {
    List<String> temp = new ArrayList<>();
    temp.add(instruction.substring(0,1));
    temp.add(instruction.substring(1));

    return temp;
  }

  List<String> directionRotation = new ArrayList<>(List.of(
    "N",
    "E",
    "S",
    "W"
    )
  );

  public void rotateWaypoint(String key, int value) {
    for (String[] waypoint : waypoints) {
      System.out.println("Waypoint INPUT " + waypoint[0] + " "+ waypoint[1]);
      int indexOfRotation  = directionRotation.indexOf(waypoint[1]);
      int rotateAngle = 0;
      // E R 180 2 => 1 (1+ 1) = 2
      // E L180 1 => 1
      if (key.equals("R")) {
        rotateAngle = value/90;
      } else if (key.equals("L")) {
        int rDirection = Math.abs(360 - value);
        // R 270
        rotateAngle = rDirection/90;
      }

      int index =0;
      int count = indexOfRotation;
      while (index != rotateAngle) {
        index++; // 1 2 3 
        count++;// 2 3 4 =0
        if (count > 3) {
          count = (count%3) - 1;
        }
      }
      waypoint[1] = directionRotation.get(count);
      System.out.println("Waypoint DIRECTION CHANGED " + waypoint[0] + " "+ waypoint[1]);
    }
  }

  public void navigateShip(String instruction, int value) {
    System.out.println("***********************************************************");
    System.out.println("Instruction"+ instruction + value);
    System.out.println("Ship Coordinates" + this.shipX + this.directionX + " " + this.shipY + this.directionY);
    System.out.println("Waypoint" + waypoints.get(0)[0] + waypoints.get(0)[1]);
    System.out.println("Waypoint" + waypoints.get(1)[0] + waypoints.get(1)[1]);

    if (instruction.equals("F")) {
      for (String[] waypoint : waypoints) {
        if (waypoint[1].equals("E") || waypoint[1].equals("W")) {
          if (waypoint[1].equals(this.directionX)) {
            int result = Integer.parseInt(waypoint[0])*value;
            this.shipX = this.shipX + result;
          } else {
            int result = Integer.parseInt(waypoint[0])*value;
            this.shipX = result - this.shipX;
            this.directionX = waypoint[1];
          }
          // this.shipX += Integer.parseInt(waypoint[0])*value;
        } else {
          if (waypoint[1].equals(this.directionY)) {
            int result = Integer.parseInt(waypoint[0])*value; 
            this.shipY = this.shipY + result;
          } else {
            int result = Integer.parseInt(waypoint[0])*value; 
            this.shipY = result - this.shipY;
            this.directionY = waypoint[1];
          }
        }
      }
      System.out.println("F RESULT" + this.shipX + " " + this.shipY + " " + this.directionX + " " + this.directionY);
      return;
    }
    

    List<List<String>> pairs = new ArrayList<>();
    pairs.add(new ArrayList(Arrays.asList("N", "S")));
    pairs.add(new ArrayList(Arrays.asList("E", "W")));

    for (List<String> pair: pairs)  {
      for (String[] waypoint: waypoints) {
        if (pair.contains(instruction) && pair.contains(waypoint[1])) {
          if (waypoint[1].equals(instruction)) {
            int result = Integer.parseInt(waypoint[0]) + value;
            waypoint[0] = result + "";
          } else {
            int result = Integer.parseInt(waypoint[0]) - value;
            waypoint[0] = result + "";
          }
        }
      }
    }


  }

  public int findManhattanDistanceOfShipPosition(List<String> instructions) {
    for (String instructionSet : instructions) {
      List<String> navigation = new ArrayList<>();
      navigation = parseInstruction(instructionSet);
      String instruction =navigation.get(0);
      int instructionValue = Integer.parseInt(navigation.get(1));

      if (instruction.equals("R") || instruction.equals("L")) {
        rotateWaypoint(instruction, instructionValue);
        continue;
      }
      navigateShip(instruction, instructionValue);
    }
    
    // System.out.println(" X coordinate " + this.shipX + " Y corrdinate" + this.shipY);
    return Math.abs(this.shipX) + Math.abs(this.shipY);

  }


  public static void main(String[] args) {
    RelativeToWaypoint RR = new RelativeToWaypoint(0, 0, "E", "N");
    try {
      File myObject = new File("advent-of-code/2020/day12/input.txt");
      Scanner myReader = new Scanner(myObject);

      List<String> input = new ArrayList<>();
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        data.trim();
        input.add(data);
      }

      int count = RR.findManhattanDistanceOfShipPosition(input);
      System.out.println("Part 2 " + count);
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}