import java.io.*;
import java.util.*;

/**
 * StreamProcessing
 * {{<a!>},{<a!>},{<a!>},{<ab>}}
 */
public class StreamProcessing {
  int result = 0;
  int garbage_counter = 0;
  class Node {
    char c;
    int count = 0;

    Node(char c, int count) {
      this.c = c;
      this.count = count;
    }
  }

  Stack<Node> st = new Stack<>();
  // {{<ab>},{<ab>},{<ab>},{<ab>}}
  // {{<a!>},{<a!>},{<a!>},{<ab>}}
  public void findGroups(String str) {
    Stack<Node> st = new Stack<>();
    int counter = 0;
    boolean ignore_next_character = false;
    for (char c : str.toCharArray()) {
      // System.out.println("LAST INPUT" + c+ ++counter);
      switch (c) {
        case '{':
          if (!ignore_next_character) {
            if (st.isEmpty()) {
              st.push(new Node(c, 1));
            } else {
              if (st.peek().c != '<') { // I don't want to add things if there is a garbage
                st.push(new Node(c,  st.peek().count + 1 ));
              }
            }
          } 
          ignore_next_character = false;
          break;
        case '}':
          if (!ignore_next_character && st.peek().c == '{') {
            result += st.peek().count;
            st.pop();
          }
          ignore_next_character = false;
          break;
        case '<':
          if (!ignore_next_character && st.peek().c != '<') {
            // IGNORE IS PUSHED
            st.push(new Node('<', st.peek().count));
          }
          ignore_next_character = false;
          break;
        case '>':
          if (!ignore_next_character && st.peek().c == '<') {
            // Don't count as this is garbage
            st.pop();
          }
          ignore_next_character = false;
          break;
        case '!':
          if (!ignore_next_character) {
            ignore_next_character = true;
            break;
          }
          ignore_next_character = false;
          break;
        default:
          // , ignore it  
          break;
      }
    }
  }

  public boolean isCharacterNotUnderGarbage(char c) {
    return (st.peek().c != '<');
  }

  public void groupings(String str) {
    int counter = 0;
    boolean ignore_flag  = false;
    for (char c : str.toCharArray()) {
      if (ignore_flag) {
        ignore_flag = false;
        continue;
      }
// "{{<!>},{<!>},{<!>},{<a>}}"
      switch (c) {
        case '{':
          if (st.isEmpty()) {
            counter++;
          } else {
            if (st.isEmpty()) {
              counter++;
            } else {
              garbage_counter++;
            }            // ignore
          }
          break;
        case '}':
          if (st.isEmpty()) {
            result += counter;
            counter--;
          } else {
            garbage_counter++;
          }
          break;
        case '!':
          ignore_flag = true;
          break;
        case '<':
          if (st.isEmpty()) {
            st.push(new Node('<', counter));
          } else {
            garbage_counter++;
          }
          break;
        case '>':
          st.pop();
        default:
          if (!st.isEmpty()) {
            garbage_counter++;
          }
          // ignore garabage
          break;
      }
    }
  }
  
  public static void main(String[] args) {
    try {
      StreamProcessing x = new StreamProcessing();

      // x.groupings("<{o'i!a,<{i<a>");
      File myObject = new File("/Users/amitkarunakaran/Desktop/coding/united/advent-of-code/2017/day9/input.txt");
      Scanner myReader = new Scanner(myObject);

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();

        System.out.println(data);
        x.groupings(data);
      }
  
      
      System.out.println("RESULT" +  x.result);
      System.out.println("GARBAGE COUNTER" + x.garbage_counter);
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
  }
}