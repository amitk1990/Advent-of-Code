import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.*;

/**
 * valid
 */
public class valid {
  int count = 0;

  public void validate(String input) {
    String[] words = input.split(" ");
    Set<String> set = new HashSet<String>();
    for (String word : words) {
      if (set.contains(word)) {
        return;
      }
      set.add(word);
    }
    count++;
    return;
  }

  int counter = 0;
  public void numberOfPassphrases(String input) {
    String[] words = input.split(" ");

    Set<String> set = new HashSet<String>();

    for (String word: words) {
      char[] ch = word.toCharArray();
      Arrays.sort(ch);
      String sortedWord = Arrays.toString(ch);
      if (set.contains(sortedWord)) {
        return;
      }
      set.add(sortedWord);
    }
    System.out.println("VALID PASSPHRASE" + input);
    counter++;
    return;
  }

  public static void main(String[] args) {
    try {
      valid x = new valid();
      File myObject = new File("/Users/amitkarunakaran/Desktop/coding/united/advent-of-code/2017/day4/input.txt");
      Scanner myReader = new Scanner(myObject);

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        x.validate(data);
      }

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        x.numberOfPassphrases(data);
      }
      // part 1
      System.out.println("VALID paraphrases " + x.count);
      // part 2
      System.out.println("TOTAL VALID PASSPHRASES WITH NO ANAGRAMS" + x.counter);
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }
}