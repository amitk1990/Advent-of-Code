import java.util.*;
import java.lang.*;
import java.io.*;
/**
 * RainRiskII
 */
public class RainRiskI {

  int shipX; 
  int shipY;
  String direction;
  Map<String, String> directionToCoordinates;

  RainRiskI(int x, int y, String d) {
    System.out.println("CALLING");
    this.shipX = x;
    this.shipY = y;
    this.direction = d;
    directionToCoordinates = new HashMap<>();
    directionToCoordinates.put("E", "X");
    directionToCoordinates.put("W", "X");
    directionToCoordinates.put("N", "Y");
    directionToCoordinates.put("S", "Y");
  }

  // idea is R = 360-left
//   F10 would move the ship 10 units east (because the ship starts by facing east) to east 10, north 0.
// N3 would move the ship 3 units north to east 10, north 3.
// F7 would move the ship another 7 units east (because the ship is still facing east) to east 17, north 3.
// R90 would cause the ship to turn right by 90 degrees and face south; it remains at east 17, north 3.
// F11 would move the ship 11 units south to east 17, south 8.
  // public void updateShipPosition(int rotateAngle) {

    List<String> directionRotation = new ArrayList<>(List.of(
      "N",
      "E",
      "S",
      "W"
      )
    );
  public void changeShipDirection(String key, int value) {
    // N R90
    int initialShipDirection  = directionRotation.indexOf(this.direction);

    // N L90
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
    int count = initialShipDirection;
    while (index != rotateAngle) {
      index++; // 1 2 3 
      count++;// 2 3 4 =0
      if (count > 3) {
        count = (count%3) - 1;
      }
    }
    this.direction = directionRotation.get(count);
    System.out.println("SHIP DIRECTION CHANGED " + this.direction);
  }


  public List<String> parseInstruction(String instruction) {
    List<String> temp = new ArrayList<>();
    temp.add(instruction.substring(0,1));
    temp.add(instruction.substring(1));

    return temp;
  }




  public void updateShipCoordinatesBasedOnDirection(String key, int value, String coordinateToUpdate, boolean forward) {
    if (forward) {
      if (coordinateToUpdate.equals("X")) {
        this.shipX = this.shipX + value;
      } else {
        this.shipY = this.shipY + value;
      }
    } else {
      if (coordinateToUpdate.equals("X")) {
        this.shipX = this.shipX - value;
      } else {
        this.shipY = this.shipY - value;
      }
    }
  }

  public void navigateShip(String instruction, int value) {
    System.out.println("***********************************************************");
    System.out.println("Ship Coordinates" + this.shipX + " " + this.shipY + "Direction " + this.direction + " Instruction " + instruction + " " + value);

    // F equals same directions as the ship is in currently
    if (instruction.equals("F")) {
      instruction = this.direction;
    }

    List<List<String>> pairs = new ArrayList<>();
    pairs.add(new ArrayList(Arrays.asList("N", "S")));
    pairs.add(new ArrayList(Arrays.asList("E", "W")));


    // check if instruction and direction is in the same axial plane
    boolean sameAxis = false;
    // direction is same as the instruction W  direction E [E, W] same axis
    for (List<String> pair: pairs)  {
      if (pair.contains(instruction) && pair.contains(this.direction)) {
        sameAxis = true;
      }
    }
    System.out.println("AXIS "  + sameAxis);

    boolean forward = false;
    if (sameAxis) {
      if (this.direction == "W" || this.direction == "S") {
        value = -value;
      }
  
      if (this.direction == "E" || this.direction == "N") {
        value = Math.abs(value);
      }

      forward = instruction.equals(this.direction);
      String coordinateToUpdate = directionToCoordinates.get(this.direction);
      System.out.println("FORWARD" + forward);
      System.out.println("Coordinate to update " + coordinateToUpdate);
      // instruction is same as ship direction [add] else subtract
      updateShipCoordinatesBasedOnDirection(instruction, value, coordinateToUpdate, forward);
    } else {
      String coordinateToUpdate = directionToCoordinates.get(instruction); // x or y
      if (instruction.equals("W") || instruction.equals("S")) {
        forward = false;
      }  else {
        forward = true;
      }
      updateShipCoordinatesBasedOnDirection(instruction, value, coordinateToUpdate, forward);
    }
    System.out.println("___________________________________________________________");
    System.out.println("COORDINATES OF SHIP " + this.shipX + " " + this.shipY);
  }


  public int findManhattanDistanceOfShipPosition(List<String> instructions) {
    for (String instructionSet : instructions) {
      List<String> navigation = new ArrayList<>();
      navigation = parseInstruction(instructionSet);
      String instruction =navigation.get(0);
      int instructionValue = Integer.parseInt(navigation.get(1));

      if (instruction.equals("R") || instruction.equals("L")) {
        changeShipDirection(instruction, instructionValue);
        continue;
      }
      navigateShip(instruction, instructionValue);
    }
    
    // System.out.println(" X coordinate " + this.shipX + " Y corrdinate" + this.shipY);
    return Math.abs(this.shipX) + Math.abs(this.shipY);

  }


  public static void main(String[] args) {
    RainRiskI RR = new RainRiskI(0, 0, "E");

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
      System.out.println("Part 1 " + count);
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}