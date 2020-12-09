
import java.util.*;
import java.io.*;

/**
 * EncodingError
 */
public class EncodingError {
  public boolean checkFor2Sum(long target, List<Long> set) {
    for (int i = 0; i < set.size(); i++) {
      if (set.contains(target - set.get(i))) {
        return true;
      }
    }
    return false;
  }

  public long findInvalidEncoding(List<Long> input, int window) {
    List<Long> set = new ArrayList<>(input.subList(0, window));

    for (int i = window; i < input.size(); i++) {
      if (set.size() < window) {
        set.add(input.get(i - 1));
      }

      long target = input.get(i);

      if (!checkFor2Sum(target, set)) {
        return target;
      }
      
      set.remove(input.get(i - window));
    }


    return 1;
  }

  public List<Long> subListEqualToTarget(List<Long> set, long target) {
    long sum = 0;
    for (int j = 0; j < set.size(); j++) {
      sum += set.get(j);

      if (sum == target) {
        return set.subList(0, j+1);
      }
    }
    return new ArrayList<Long>();
  }


  public List<Long> findEncryptionWeakness(List<Long> input, long target, int window) {
    List<Long> set = new ArrayList<>(input.subList(0, window));

    for (int i = window; i < input.size(); i++) {
      if (set.size() < window) {
        set.add(input.get(i - 1));
      }

      List<Long> list = subListEqualToTarget(set, target);

      if (list.isEmpty()) {
        set.remove(input.get(i - window));
      } else {
        return list;
      }
    }

    return new ArrayList<Long>();
  }

  public static void main(String[] args) {
    EncodingError test = new EncodingError();

    try {
      File myObject = new File("advent-of-code/2020/day9/input.txt");
      Scanner myReader = new Scanner(myObject);
  
      List<Long> input = new ArrayList<>();
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        input.add(Long.parseLong(data));
      }
      int length = 25;
      // part 1
      long result = test.findInvalidEncoding(input, length);
      System.out.println("Part 1  " + result);

      // part 2
      List<Long> setOfNumbers  = test.findEncryptionWeakness(input, result, length);
      long result2 = Collections.max(setOfNumbers) + Collections.min(setOfNumbers);
      System.out.println("Part 2 " + result2);

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    } 
  }
}