import java.util.*;
import java.io.*;
/**
 * TicketTranslationII
 */
public class TicketTranslationII {

  public Map<String, List<List<Integer>>> parseTicketFields(List<String> inputs) {
    // String[] inputs = s.split("#");
    Map<String, List<List<Integer>>> ticketFields = new HashMap<>();
    for (String input: inputs) {
      String leftOperand = input.substring(0, input.indexOf(": ")).trim();
      String rightOperand = input.substring(input.indexOf(": ") + 1).trim();
      // System.out.println(leftOperand);
      // System.out.println(rightOperand);
      String[] minMaxValues = rightOperand.split("or");
      ticketFields.put(leftOperand, new ArrayList<>());
      for (String val : minMaxValues) {
        List<Integer> output = new ArrayList<>();
        String[] minMax = val.trim().split("-");
        output.add(Integer.parseInt(minMax[0].trim(), 10));
        output.add(Integer.parseInt(minMax[1].trim(), 10));
        // System.out.println("Minimum Maximum" + output);
        if (ticketFields.containsKey(leftOperand)) {
          List<List<Integer>> list = ticketFields.get(leftOperand);
          list.add(new ArrayList<>(output));
          ticketFields.put(leftOperand, list);
        } else {
          List<List<Integer>> list = new ArrayList<>();
          list.add(output);
          ticketFields.put(leftOperand, list);
        }
        
      }
    }
    return ticketFields;
  }

  public List<Integer> parseTicket(List<String> ticket) {
    List<Integer> result = new ArrayList<>();

    for (String input : ticket) {
      if (input.isEmpty() || (input.indexOf("nearby") == 0 || (input.indexOf("your") == 0))) {
        continue;
      }
      String[] ticketNumbers = input.split(",");
      for (String number : ticketNumbers) {
        result.add(Integer.parseInt(number, 10));
      }
    }

    return result;
  }

  public boolean checkWithinTheRange(List<List<Integer>> ticketRanges, int ticket) {
    boolean isInRange = false;
    for (List<Integer> tRange : ticketRanges) {
      int min = tRange.get(0);
      int max = tRange.get(1);
      if (ticket >= min && ticket <= max) {
        isInRange = true;
      }
    }

    return isInRange;
  }
  Map<String, List<Integer>> possibleSolution = new HashMap<>();

  public void possibleGridValues(String field, boolean[] visited) {
    for (int i = 0; i < visited.length; i++) {
      if (!visited[i]) {
        if (!possibleSolution.containsKey(field)) {
          List<Integer> temp = new ArrayList<>();
          temp.add(i);
          possibleSolution.put(field, temp);
        } else {
          List<Integer> temp = possibleSolution.get(field);
          temp.add(i);
          possibleSolution.put(field, temp);
        }
      }
    }
  }

  public void computePossibilitySeats(Set<String> fields, Map<String, List<List<Integer>>> map, List<Integer> nearbyList, int length) {
    for (String field : fields) {
      boolean[] possibleFieldPosition = new boolean[length];
      Arrays.fill(possibleFieldPosition, Boolean.TRUE);

      List<List<Integer>> list = map.get(field);
      for (int i = 0; i < nearbyList.size(); i++) {
        int ticketNumber = nearbyList.get(i);
        boolean result = checkWithinTheRange(list, ticketNumber);

        if (!result && possibleFieldPosition[i%(length)]) {
          possibleFieldPosition[i%(length)] = result;
        }
      }
      possibleGridValues(field, possibleFieldPosition);
    }
  }

  public List<Integer> compute(List<Integer> tickets, Map<String, List<List<Integer>>> map) {
    List<Integer> result = new ArrayList<>();
    System.out.println(tickets);
    for (int ticket : tickets) {
      boolean isInRange = false;
      for (Map.Entry<String, List<List<Integer>>> e : map.entrySet()) {
        String key = e.getKey();
        List<List<Integer>> ticketRanges = map.get(key);

        for (List<Integer> tRange : ticketRanges) {
          int min = tRange.get(0);
          int max = tRange.get(1);
          if (ticket >= min && ticket <= max) {
            isInRange = true;
          }
        }
      }

      if (!isInRange) {
        result.add(ticket);
      }
    }

    return result;
  }


  public long findTicketScanningErrorRate(List<String> grid, List<String> yourTicket, List<String> nearby) {
    Map<String, List<List<Integer>>> map = parseTicketFields(grid);
    System.out.println("INPUT---------------------");
    System.out.println(map.keySet());
    System.out.println(map.values());
    System.out.println("INPUT---------------------");
    List<Integer> yourTicketList = parseTicket(yourTicket);
    List<Integer> nearbyList = parseTicket(nearby);
    List<Integer> missingTickets = compute(nearbyList, map);
    long z = 0;
    for (int missing : missingTickets) {
      z += missing;
    }
    System.out.println("part 1 " + z);
    System.out.println("MISSING" + missingTickets);
    if (!missingTickets.isEmpty()) {
      for (int missing : missingTickets) {
        nearbyList.remove(Integer.valueOf(missing));
      }
    }
    System.out.println("NEAER" + nearbyList);
    Set<String> fields = map.keySet();
    System.out.println("your ticket list" + yourTicketList);
    System.out.println("nearby: " +  nearbyList);
    computePossibilitySeats(fields, map, nearbyList, yourTicketList.size());
    System.out.println("POSSIBLE SOLUTION" + possibleSolution);
    
    PriorityQueue<Map.Entry<String, List<Integer>>> pq = new PriorityQueue<>((a, b) -> {
      return (b.getValue().size() - a.getValue().size());
    });

    for (Map.Entry<String, List<Integer>> e : possibleSolution.entrySet()) {
      pq.offer(e);
    }

    // Map<String, List<Integer>> positionMapper = new HashMap<>(); 
    Map<String, List<Integer>> positionMapper = new HashMap<>(); 

    for (String field : fields) {
      List<Integer> temp = new ArrayList<>();
      for (int i = 0; i < yourTicketList.size(); i++) {
        temp.add(i);
      }
      positionMapper.put(field, temp);
    }

    boolean[] marked = new boolean[yourTicketList.size()];
    while(!pq.isEmpty()) {
      Map.Entry<String, List<Integer>> e = pq.poll();
      // System.out.println(e.getKey());
      // System.out.println(e.getValue());
      String field = e.getKey();
      List<Integer> list = e.getValue();

      for (int notValidGrid : list) {
        List<Integer> arrangements = positionMapper.get(field);
        arrangements.remove(Integer.valueOf(notValidGrid));
      }
    }

    System.out.println("Mapper" + positionMapper);

    PriorityQueue<Map.Entry<String, List<Integer>>> arrange = new PriorityQueue<>((a, b) ->{
      return a.getValue().size() - b.getValue().size();
    });

    for (Map.Entry<String, List<Integer>> e : positionMapper.entrySet()) {
      arrange.offer(e);
    }

    while (!arrange.isEmpty()) {
      Map.Entry<String, List<Integer>> e = arrange.poll();
      String key = e.getKey();
      List<Integer> value = e.getValue();
      for (Map.Entry<String, List<Integer>> entry : positionMapper.entrySet()) {
        if (!entry.getKey().equals(key)) {
          // System.out.println("KEY FROM QUEUE" + key);
          // System.out.println("KEY FROM MApper" + entry.getKey());
          List<Integer> temp = positionMapper.get(entry.getKey());
          // System.out.println("REmove" + Integer.valueOf(value.get(0)));
          // if (!temp.isEmpty() && !value.isEmpty()) {
            System.out.println(value);
            temp.remove(Integer.valueOf(value.get(0)));
          // }
          
        }
      }
    }
    // System.err.println("FINALLY");
    System.out.println(positionMapper);
    long result = 1;

    for (Map.Entry<String, List<Integer>> e : positionMapper.entrySet()) {
      if (e.getKey().indexOf("departure") == 0) {
          int index = e.getValue().get(0);
          System.out.println("Key " + e.getKey() + " Value " + index);
          System.out.println(yourTicketList.get(index));
          result *= yourTicketList.get(index);
      }
    }
    return result;
  }

  public static void main(String[] args) {
    TicketTranslationII TT = new TicketTranslationII();

    try {
      File myObject = new File("advent-of-code/2020/day16/test2.txt");
      Scanner myReader = new Scanner(myObject);
      List<String> grid = new ArrayList<>();
      List<String> yourTicket = new ArrayList<>();
      List<String> nearby = new ArrayList<>();
      String instruction = "";
      boolean enableYourTicket = false;
      boolean nearbyTickets = false;
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        data.trim();
        if (data.equals("")) {
          enableYourTicket = false;
          nearbyTickets = false;
        } else if (data.indexOf("nearby") == 0 || nearbyTickets == true) {
          nearbyTickets = true;
          nearby.add(data);
        } else if (data.indexOf("your") == 0 || enableYourTicket == true) {
          enableYourTicket = true;
          yourTicket.add(data);
        } else {
          grid.add(data);
        }
      }
      // System.out.println(grid);
      // System.out.println(yourTicket);
      // System.out.println(nearby);
      long count = TT.findTicketScanningErrorRate(grid, yourTicket, nearby);
      System.out.println("Part 2 " + count);
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}