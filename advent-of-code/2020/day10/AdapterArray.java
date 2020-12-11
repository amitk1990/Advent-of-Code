import java.util.*;
import java.io.*;

/**
 * AdapterArray
 */
public class AdapterArray {
  public int calculateJoltageDifference(List<Integer> input) {
    Collections.sort(input);

    int maxJoltage = Collections.max(input);
    int outlet = 0;
    int joltageRating  = 1;
    int result = 0;
    int oneJoltageDifference = 0, twoJoltageDifference = 0, threeJoltageDiffernce =0;

    while (outlet <= maxJoltage) {
      if (input.contains(outlet + joltageRating)) {
        if (joltageRating == 1) {
          oneJoltageDifference++;
        } else if (joltageRating == 2) {
          twoJoltageDifference++;
        } else if (joltageRating == 3) {
          threeJoltageDiffernce++;
        }
        outlet = outlet + joltageRating;
        joltageRating = 1;
      } else {
        if (joltageRating <= 3) {
          joltageRating++;
        } else {
          result = outlet;
          break;
        }
      }
    }
    threeJoltageDiffernce = threeJoltageDiffernce + 1;

    return oneJoltageDifference*threeJoltageDiffernce;
  }

  // cache all valid and invalid result paths.
  public long dfs(Set<Integer> input, int outlet, int max, Map<Integer, Long> cache) {
    if (outlet == max) {
      cache.put(outlet, (long) 1);
      return 1;
    }

    if (outlet > max) {
      cache.put(outlet, (long) 0);
      return 0;
    }

    if (!input.contains(outlet)) {
      cache.put(outlet, (long) 0);
      return 0;
    }

    if (cache.containsKey(outlet)) {
      return cache.get(outlet);
    }

    long result = dfs(input, outlet + 1, max, cache) + dfs(input, outlet + 2, max, cache) + dfs(input, outlet + 3, max, cache);
    cache.put(outlet, result);
    return cache.get(outlet);
  }

  public long distinctArrangements(List<Integer> input) {
    Collections.sort(input);
    int max  = Collections.max(input);
    input.add(0);
    input.add(max + 3);
    Set<Integer> inputSet = new HashSet<>(input);

    return dfs(inputSet, 0, max + 3 , new HashMap<Integer, Long>());
  }

  public static void main(String[] args) {

    try {
      AdapterArray AA = new AdapterArray();
      File myObject = new File("advent-of-code/2020/day10/input.txt");
      Scanner myReader = new Scanner(myObject);

      List<Integer> list = new ArrayList<>();
      // PART 1
      while (myReader.hasNextLine()) {
        String input = myReader.nextLine();
        list.add(Integer.parseInt(input));
      }

      int result = AA.calculateJoltageDifference(list);
      System.out.println("part 1 " +  result); // 2775

      // part 2
      long result2 = AA.distinctArrangements(list);
      System.out.println("part 2 " + result2); // 518344341716992
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}