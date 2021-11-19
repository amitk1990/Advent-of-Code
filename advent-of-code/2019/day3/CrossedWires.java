package day3;
import java.io.*;
import java.util.*;
import java.math.*;

public class CrossedWires {
    int DO_NOT_MOVE = 0;
    int[] coordinates = new int[]{0, 0};
    Set<String> path = new HashSet<String>();
    List<int[]> intersectionPoints = new ArrayList<>();

    public void calculateCoordinatePosition(String direction, int distance) {
        switch (direction) {
            case "R":
                this.populatePath(1, 0, distance);
                return;
            case "L":
                this.populatePath(-1, 0, distance);
                return;
            case "U":
                this.populatePath(0, 1, distance);
                return;
            case "D":
                this.populatePath(0, -1, distance);
                return;
        }
    }

    private void populatePath(int movementX, int movementY, int distance) {
        int index = 0;
        // move x axis
        if (movementX != this.DO_NOT_MOVE) {
            while (index <= distance) {
                this.coordinates[0] += movementX;
                String point = this.coordinates[0] + "-" +  this.coordinates[1];

                if (path.contains(point)) {
                    System.out.println("OVERLAP" + movementY + distance);
                    int[] intersection = new int[]{this.coordinates[0], this.coordinates[1]};
                    intersectionPoints.add(intersection);
                } else {
                    path.add(point);
                }
                index += 1;
            }
            return;
        }

        // move Y axis
        if (movementY != this.DO_NOT_MOVE) {
            while (index <= distance) {
                this.coordinates[1] += movementY;
                String point = this.coordinates[0] + "-" +  this.coordinates[1];

                if (path.contains(point)) {
                    int[] intersection = new int[]{this.coordinates[0], this.coordinates[1]};
                    intersectionPoints.add(intersection);
                } else {
                    path.add(point);
                }
                index += 1;
            }
            return;
        }
    }

    public int findClosestDistanceToCentralPort(List<List<String>> input) {
        // first instruction
        for (String instruction : input.get(0)) {
            String direction = instruction.substring(0, 1);
            int distance = Integer.parseInt(instruction.substring(1));
            System.out.println(direction + " INSTRUCTION1 " + distance);
            this.calculateCoordinatePosition(direction, distance);
        }
    
        // reset to origin
        this.coordinates[0] = 0;
        this.coordinates[1] = 0;
        for (String instruction: input.get(1)) {
            String direction = instruction.substring(0, 1);
            int distance = Integer.parseInt(instruction.substring(1));
            System.out.println(direction + " INSTRUCTION 2 " + distance);
            this.calculateCoordinatePosition(direction, distance);
        }
    
        int minimum = Integer.MAX_VALUE;
        for (int[] intersectedPoint : intersectionPoints) {
            System.out.println(intersectedPoint[0] + " " + intersectedPoint[1]);
            minimum = Math.min(minimum, (Math.abs(intersectedPoint[0]) + Math.abs(intersectedPoint[1])));
        }
        return minimum;
    }

    public static void main(String[] args) {
        try {
            CrossedWires obj = new CrossedWires();
            File file = new File("./2019/day3/test2.txt");
            Scanner myReader = new Scanner(file);
            List<List<String>> input = new ArrayList<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                input.add(Arrays.asList(data.split(",")));
            }
            myReader.close();

            int result = obj.findClosestDistanceToCentralPort(input);
            System.out.println("part 1 "+ result);

        }  catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
