import java.util.*;
import java.io.*;

/**
 * HandyHaversacks
 */
public class HandyHaversacks {
  // PART 1
  //1 white bag, 2 muted yellow bags.
  public List<String> parseContainsbag(String containsBag) {
    List<String> listOfbags = new ArrayList<>();
    String bags = containsBag.replaceAll("(bags|bag)", "").replaceAll("\\d", "").replace(".", "").trim();

    if (bags.indexOf(',') > 0) {
      String[] list = bags.split(",");
      for (String bag : list) {
        if (bag.indexOf("other") > 0) {
          continue;
        }
        listOfbags.add(bag.trim());
      }
    } else {
      if (bags.indexOf("other") > 0) {
        return listOfbags;
      }
      listOfbags.add(bags.trim());
    }

    return listOfbags;
  }
  // PART 1
  public int bfs(String searchTerm, Map<String, Set<String>> lookup) {
    Queue<String> q = new LinkedList<>();
    q.offer(searchTerm);

    int counter = -1;
    Set<String> visited = new HashSet<>();
    visited.add(searchTerm);
    while (!q.isEmpty()) {
      String currentTerm = q.poll();
      if (lookup.containsKey(currentTerm)) {
        Set<String> nextBags = lookup.get(currentTerm);
        for (String next : nextBags) {
          if (!visited.contains(next)) {
            visited.add(next);
            q.offer(next);
          }
        }
      }
      counter++;
    }

    return counter;
  }
  // PART 1
  public int findBagColors(List<String> input, String searchTerm) {
    // light red bags contain 1 bright white bag, 2 muted yellow bags.
    Map<String, Set<String>> lookup = new HashMap<>();
    for (String rule: input) {
      String[] bagRule = rule.split(" contain ");
      String containerbag = bagRule[0].replaceAll("(bags|bag)", "").trim();

      List<String> parseContainsbag = parseContainsbag(bagRule[1]);

      for (String s : parseContainsbag) {
        if (!lookup.containsKey(s)) {
          lookup.put(s, new HashSet<>());
        }
        lookup.get(s).add(containerbag);
      }
    }

    // searching via breadth first search and return bag count
    return bfs(searchTerm, lookup);
  }


  // Part 2
  // Bag object
  class Bag {
    int quantity;
    String bagColor;

    Bag(int quantity, String bagColor) {
      this.quantity = quantity;
      this.bagColor = bagColor;
    }
  }

  // { bagColor: <color>, quantity: <number> }
  public Bag createBag(String input) {
    int bagCount = 0;
    StringBuffer sb = new StringBuffer();

    for (char character : input.toCharArray()) {
      // For find the count of bags
      if (Character.isDigit(character)) {
        bagCount = Integer.parseInt(String.valueOf(character));
      } else {
        sb.append(character);
      }
    }
    String bagColor = sb.toString().replaceAll("(bags|bag)", "").replace(".", "").trim();
    if (bagColor.equals("no other")) {
      bagColor = "";
      bagCount = 0;
    }
    return new Bag(bagCount, bagColor);
  }

  //1 white bag, 2 muted yellow bags.
  public List<Bag> parseBag(String s) {
    List<Bag> listOfbags = new ArrayList<>();
    String bags = s.replace("(bags|bag)", "").replace(".", "").trim();

    if (s.indexOf(',') > 0) {
      String[] list = bags.split(",");
      for (String bag : list) {
        Bag object = createBag(bag);
        listOfbags.add(object);
      }
    } else {
      Bag object = createBag(bags);
      listOfbags.add(object);
    }

    return listOfbags;
  }
  public int totalBags(List<String> input, String searchTerm) {
    Map<String, List<Bag>> lookup = new HashMap<>();
    for (String rule: input) {
      String[] bagRule = rule.split(" contain ");
      String containerbag = bagRule[0].replaceAll("(bags|bag)", "").trim();
      // parser
      List<Bag> listOfBags = parseBag(bagRule[1]);

      for (Bag s : listOfBags) {
        if (!lookup.containsKey(containerbag)) {
          lookup.put(containerbag, new ArrayList());
        }
        lookup.get(containerbag).add(s);
      }
    }    

    Set<String> visited = new HashSet<>();
    visited.add(searchTerm);

    // Backtracking dfs - to find the total bags
    return dfs(lookup, searchTerm, visited, 0, 0);
  }

  public int dfs(Map<String, List<Bag>> lookup, String searchTerm, Set<String> visited, int index, int sum) {
    List<Bag> bags = lookup.get(searchTerm);
    if (bags.size() == 1 && bags.get(0).quantity == 0) {
      return bags.get(0).quantity;
    }

    for (int i = index; i < bags.size(); i++) {
      Bag bag = bags.get(i);
      visited.add(bag.bagColor);
      sum += bag.quantity;
      sum += bag.quantity*dfs(lookup, bag.bagColor, visited, 0, 0);
      visited.remove(bag.bagColor);
    }

    return sum;
  }
  public static void main(String[] args) {
    try {
      HandyHaversacks hh = new HandyHaversacks();
      File myObject = new File("advent-of-code/2020/day7/input.txt");
      Scanner myReader = new Scanner(myObject);

      List<String> list = new ArrayList<>();
      // PART 1
      while (myReader.hasNextLine()) {
        String input = myReader.nextLine();
        list.add(input);
      }
      String searchTerm = "shiny gold";

      int part1 = hh.findBagColors(list, searchTerm);
      System.out.println("Part 1 " + part1);

      int part2 = hh.totalBags(list, searchTerm);
      System.out.println("Part 2 " + part2);
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}