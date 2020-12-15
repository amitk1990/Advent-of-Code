import java.util.*;
import java.io.*;
import java.math.BigInteger;
/**
 * ShuttleSearchII
 */
public class ShuttleSearchII {

  public long earliestBus(List<String> input) {
    long arrivalTime = Long.parseLong(input.get(0));
    String[] schedules = input.get(1).split(",");
    List<Long> busSchedule = new ArrayList<>();
    for (String schedule: schedules) {
      if (!schedule.equals("x")) {
        busSchedule.add(Long.parseLong(schedule));
      }
    }

    long estimatedWaitTime = Long.MAX_VALUE;
    long busId = 0;
    for (long busDepartInterval : busSchedule) {
      long busWaitTime = busDepartInterval - (arrivalTime%busDepartInterval);

      if (estimatedWaitTime > busWaitTime) {
        estimatedWaitTime = busWaitTime;
        busId = busDepartInterval;
      }
    }
    return (estimatedWaitTime*busId);
  }

  public BigInteger matchingOffset(List<String> input) {
    String[] schedules = input.get(1).split(",");
    Map<String, String> map = new LinkedHashMap<>();
    String minimumBusId = "";

    for (int i = 0; i < schedules.length; i++) {
      if (!schedules[i].equals("x")) {
        String busId = schedules[i];

        if (i == 0) {
          minimumBusId = busId;
        }
        map.put(busId, i + "");
      }
    }

    BigInteger schedule = new BigInteger("0");
    Boolean foundFirst = false;
     while (!foundFirst) {
      // [{17=0, 13=2, 19=3}]
      BigInteger offsetForFirstBus = new BigInteger(map.get(minimumBusId));
      schedule.add(offsetForFirstBus);

      BigInteger arrivalTimeStamp = new BigInteger(minimumBusId).add(schedule);
      int count = 1; // first bus is already inclusive
      for (Map.Entry<String,String> entry : map.entrySet()) {
        if (entry.getKey() != minimumBusId) {
          BigInteger nextBusId = new BigInteger(entry.getKey());
          BigInteger busTimeOffset = new BigInteger(entry.getValue());
          BigInteger busIdTimestampArrival = arrivalTimeStamp.add(busTimeOffset);
          BigInteger result = busIdTimestampArrival.mod(nextBusId);
          if (!result.equals(new BigInteger("0"))) {
            break;
          }
          count++;
        }
      }
      if (count == map.size()) {
        foundFirst = true;
        return arrivalTimeStamp;
      }
      schedule = arrivalTimeStamp;
      
    }
    return new BigInteger("-1");
  }

  public long offsetPosition(List<String> input) {
    String[] schedules = input.get(1).split(",");
    Long firstBusTime = Long.parseLong(schedules[0]);
    Long time = new Long(0);

    for (int i = 1; i < schedules.length; i++) {
      if (!schedules[i].equals("x")) {
        Long nextTime = Long.parseLong(schedules[i]);
        System.out.println(schedules[i]);
        System.out.println(firstBusTime);
        while (true) {
          time += firstBusTime;
          if ((time+i)%nextTime  == 0) {
            System.out.println("first bus time" + firstBusTime);
            System.out.println("first bus time" + nextTime);
            System.out.println("TIME" + time);
            firstBusTime *= nextTime;
            break;
          }
        }
      }
    }
    return time;
  }

  public static void main(String[] args) {
    ShuttleSearchII SS = new ShuttleSearchII();
    try {
      File myObject = new File("advent-of-code/2020/day13/test2.txt");
      Scanner myReader = new Scanner(myObject);

      List<String> input = new ArrayList<>();
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        data.trim();
        input.add(data);
      }

      long waitTime = SS.earliestBus(input);
      System.out.println("Part 1 " + waitTime);

      long result = SS.offsetPosition(input);
      System.out.println("Part 2 " + result);

      // Takes a lot of time for input.txt
      // BigInteger timestampWithMatchingOffset = SS.matchingOffset(input);
      // System.out.println("Part 2 " + timestampWithMatchingOffset);
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    
  }
}