import java.util.*;
import java.io.*;

/**
 * TicketTranslation
 */
public class TicketTranslation {
// class: 1-3 or 5-7|row: 6-11 or 33-44|seat: 13-40 or 45-50|

// |nearby tickets:|7,3,47|40,4,50|55,2,20|38,6,12|
  public Map<String, List<List<Integer>>> parseTicketFields(String s) {
    String[] inputs = s.split("#");
    // System.out.println(Arrays.toString(inputs));
    Map<String, List<List<Integer>>> ticketFields = new HashMap<>();
    for (String input: inputs) {
      String leftOperand = input.substring(0, input.indexOf(": ") - 1).trim();
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

  // List<List<Integer>> x = map.get("class");
  // for (List<Integer> temp : x) {
  //   System.out.println("HERE"+ temp.get(0));
  //   System.out.println("PLAN" + temp.get(1));
  // }
  // #nearby tickets:#7,3,47#40,4,50#55,2,20#38,6,12#
  public List<Integer> parseTicket(String ticket) {
    String[] inputs = ticket.split("#");
    List<Integer> result = new ArrayList<>();

    for (String input : inputs) {
      if (input.isEmpty() || (input.indexOf("nearby") == 0)) {
        continue;
      }
      String[] ticketNumbers = input.split(",");
      for (String number : ticketNumbers) {
        result.add(Integer.parseInt(number, 10));
      }
    }

    return result;
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

  public long findTicketScanningErrorRate(List<String> input) {
    Map<String, List<List<Integer>>> map = parseTicketFields(input.get(0));
    System.out.println(map.keySet());
    // System.out.println("input" + input.get(2));
    // parseTicket(input.get(1));
    List<Integer> nearbyTickets = parseTicket(input.get(2));
    List<Integer> missingTickets =  compute(nearbyTickets, map);
    System.out.println(missingTickets);
    long result = 0;
    for (int missing : missingTickets) {
      result += missing;
    }
    return result;
  }

  public static void main(String[] args) {
    TicketTranslation TT = new TicketTranslation();

    try {
      File myObject = new File("advent-of-code/2020/day16/test.txt");
      Scanner myReader = new Scanner(myObject);
      List<String> yourTicket = new ArrayList<>();
      List<String> input = new ArrayList<>();
      String instruction = "";
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        data.trim();
        if (data.equals("")) {
          input.add(instruction);
          instruction = "";
        }

        instruction += data + "#";
      }
      input.add(instruction);
      long count = TT.findTicketScanningErrorRate(input);
      System.out.println("Part 1 " + count);
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}