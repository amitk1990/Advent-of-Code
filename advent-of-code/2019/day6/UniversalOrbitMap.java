import java.util.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

/**
 * UniversalOrbitMap
 *  b->a b => 1
 * d -> c d => 1
 * c -> b
 */
public class UniversalOrbitMap {
  Map<String, Integer> count = new HashMap();
  Map<String, String> map = new HashMap();

  public String findParent(String node) {
    for (Map.Entry<String, String> entry: map.entrySet()) {
      if (entry.getValue().equals(node)) {
        return entry.getKey();
      }
    }
    return "";
  }


  public void reconstructDepedencyGraph(String outer, String inner) {
    if (outer.equals("")) {
      return;
    }

    map.put(outer, inner);
    count.put(outer, count.getOrDefault(inner, 1) + 1);
    if (map.containsValue(outer)) {
        count.put(outer, count.getOrDefault(inner, 1) + 1);
        String parent = this.findParent(outer);
        this.reconstructDepedencyGraph(parent, outer);
    }
  }

  public int calculateDirectIndirectOrbits() {
    int result = 0;

    for (Map.Entry<String, Integer> counter : count.entrySet()) {
      result += counter.getValue();
    }

    return result;
  }

  public int findTotalNumberOfDirectIndirectOribits(List<List<String>> input) {
    for (List<String> s : input) {
      String innerPlanet = s.get(0); // Inner planet
      String outerPlanet = s.get(1); // outer planet
      this.reconstructDepedencyGraph(outerPlanet, innerPlanet);
    }
    return this.calculateDirectIndirectOrbits();
  }

  public static void main(String[] args) {
    UniversalOrbitMap object = new UniversalOrbitMap();

    try {
      File file = new File("./advent-of-code/2019/day6/test1.txt");
      Scanner myReader = new Scanner(file);
      List<List<String>> testInput = new ArrayList<>();
      while (myReader.hasNext()) {
        String data = myReader.nextLine();
        List<String> temp = Arrays.asList(data.split("\\)"));
        testInput.add(new ArrayList<>(temp));
      }
      System.out.println("Total number of Direct and Indirect Oribits " + object.findTotalNumberOfDirectIndirectOribits(testInput));
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}