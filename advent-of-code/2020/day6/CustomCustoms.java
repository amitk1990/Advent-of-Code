import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.*;
import java.io.*;

/**
 * CustomCustoms
 */
public class CustomCustoms {

  public int calculateResult(char[] answered, Set<Character> set) {
    int result = 0;
    for (char c : answered) {
      if (!set.contains(c)) {
        set.add(c);
        result++;
      }
    }
    return result;
  }

  public int totalCountOfQuestionsAnswered(List<List<String>> list) {
    int total = 0;
    for (int i = 0; i < list.size(); i++) {
      // abc or a
      List<String> questions= list.get(i);
      Set<Character>  set = new HashSet<>();
      for (String question : questions) {
        // abc or ab
        total += calculateResult(question.toCharArray(), set);
      }
    }

    return total;
  }

  public int commonQuestionsAnsweredByEntireGroup(List<String> questions) {
    int result = 0;
    int[] map = new int[26];
    int size = questions.size();
      // example :  2 groups [ab, ac]  map  [ a-2][b-1][c-1] . 
      // 1st group answered ab, 2nd grp anwered ac. common question between both grps is 'a'
      // hence result = 1
    for (String question : questions) {
      for (char c : question.toCharArray()) {
        map[c - 'a']++;
      }
    }
  
    for (int j = 0; j <  map.length; j++) {
      if (map[j] > 0 && map[j]%size == 0) {
        result++;
      }
    }

    return result;
  }

  public int totalQuestionsAnsweredByaSingleGroup(List<List<String>> list) {
    int total = 0;
    for (int i = 0; i < list.size(); i++) {
      List<String> questions= list.get(i);
      total += commonQuestionsAnsweredByEntireGroup(questions);
    }

    return total;
  }

  public static void main(String[] args) {
    try {
      File myObject = new File("advent-of-code/2020/day6/input.txt");
      Scanner myReader = new Scanner(myObject);
  
      List<List<String>> input = new ArrayList<>();
      List<String> temp = new ArrayList<>();

      while (myReader.hasNextLine()) {
          String data = myReader.nextLine();
          if (data.length() == 0) {
            input.add(temp);
            temp = new ArrayList<>();
          } else {
              temp.add(data.trim());
          }
      }
      myReader.close();
      // last line is getting skipped
      input.add(temp);
      CustomCustoms x = new CustomCustoms();

      int part1 = x.totalCountOfQuestionsAnswered(input);
      int part2 = x.totalQuestionsAnsweredByaSingleGroup(input);

      System.out.println("PART 1 " + part1);
      System.out.println("PART 2 " + part2);
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }  
}