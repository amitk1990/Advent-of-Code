package day3;
import java.io.*;
import java.util.*;

public class CrossedWires {
    // part 1
    int[] coordinates = new int[]{0, 0};
    Set<String> set = new HashSet<String>();
    List<int[]> intersectionPoints = new ArrayList<>();
    // part 2
    Map<String, Integer> map = new HashMap<>();
    int totalDistanceCoveredByWire1 = 1;
    int totalDistanceCoveredByWire2 = 1;
    int result2 = Integer.MAX_VALUE;

    public int[] calculateCoordinatePosition(String direction, int distance) {
        switch (direction) {
            case "R":
                return new int[] {1, 0};
            case "L":
                return new int[] {-1, 0};
            case "U":
                return new int[] {0, 1};
            case "D":
                return new int[] {0, -1};
            default: 
                return new int[] {0, 0};
        }
    }

    public void populatePath(int[] points, int distance) {
        int index = 0;
        while (index < distance) {
            this.coordinates[0] += points[0];
            this.coordinates[1] += points[1];
            String path = this.coordinates[0] + "#" + this.coordinates[1];
            set.add(path);
            map.put(path, totalDistanceCoveredByWire1);
            totalDistanceCoveredByWire1 += 1;
            index += 1;
        }
    }

    public void findIntersectingPath(int[] points, int distance) {
        int index = 0;
        while (index < distance) {
            this.coordinates[0] += points[0];
            this.coordinates[1] += points[1];
            String path = this.coordinates[0] + "#" + this.coordinates[1];
            if (set.contains(path)) {
                int stepsWire1Took = map.get(path);
                // calculate the combined wire length.
                result2 = Math.min(result2, stepsWire1Took + totalDistanceCoveredByWire2);
                int[] intersection = new int[]{this.coordinates[0], this.coordinates[1]};
                intersectionPoints.add(intersection);
            }
            totalDistanceCoveredByWire2 += 1;
            index += 1;
        }
    }

    public int execute(List<List<String>> input) {
        // first wire
        for (String instruction : input.get(0)) {
            String direction = instruction.substring(0, 1);
            int distance = Integer.parseInt(instruction.substring(1));

            int[] points = this.calculateCoordinatePosition(direction, distance);
            this.populatePath(points, distance);
        }
    
        // reset to origin
        this.coordinates[0] = 0;
        this.coordinates[1] = 0;

        // second wire
        for (String instruction: input.get(1)) {
            String direction = instruction.substring(0, 1);
            int distance = Integer.parseInt(instruction.substring(1));

            int[] points = this.calculateCoordinatePosition(direction, distance);
            // intersecting path
            this.findIntersectingPath(points, distance);
        }

        // calculate min distance from central port
        int minimum = Integer.MAX_VALUE;
        for (int[] intersectedPoint : intersectionPoints) {
            minimum = Math.min(minimum, (Math.abs(intersectedPoint[0]) + Math.abs(intersectedPoint[1])));
        }
        return minimum;
    }

    public static void main(String[] args) {
        try {
            CrossedWires obj = new CrossedWires();
            File file = new File("./advent-of-code/2019/day3/input.txt");
            Scanner myReader = new Scanner(file);
            List<List<String>> input = new ArrayList<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                input.add(Arrays.asList(data.split(",")));
            }
            myReader.close();

            int result = obj.execute(input);
            System.out.println("part 1 "+ result); // 1211
            System.out.println("part 2 "+ obj.result2); // 101386

        }  catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
