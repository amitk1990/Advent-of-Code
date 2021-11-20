import java.util.*;
import java.io.*;
/**
 * OperationOrder
 */
public class OperationOrder {

  public long evaluations(String expression) {
    for (int i = 0; i < expression.length; i++) {
      
    }
  }

  public long evaluate(List<String> input) {
    long result = 0;
    for (String s : input) {
      result += evaluations(s);
    }
    return result;
  }

  public static void main(String[] args) {
    long result = 0;
    try {
        File myObject = new File("advent-of-code/2020/day8/test.txt");
        Scanner myReader = new Scanner(myObject);

        List<String> input = new ArrayList<>();

        String text = "";
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            input.add(data);
        }
        myReader.close();
        result = evaluate(input);
        System.out.println("part 1 " + result);
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

    //   NOTE: PART 1 is subset of part 2, remove x.validateCredentials(s) that will be result 1
      System.out.println("result PART 2 "+ result); 
  }
}