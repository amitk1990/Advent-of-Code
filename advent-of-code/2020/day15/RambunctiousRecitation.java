import java.util.*;

/**
 * RambunctiousRecitation
 */
public class RambunctiousRecitation {

    // 0, 3, 6
    // 1 - > 0
    // 2 -> 3
    // 3 -> 6
    // 4 -> 0
    // 5 -> 4 - 1 = 3
    // 6 -> 5 - 2 = 3
    // 7 -> 6 - 5 = 1
    // 8 -> 0
    public void fixedAdd(List<Long> list, long val, int size) {
        list.add(new Long(val));
        if(list.size() > size) list.remove(0);
    }
    
    public long findNumber(int[] input, int target) {
        Map<Long, List<Long>> seen = new HashMap<>();
        long k = new Long(input.length + 1);
        long previous = 0;
        for (int i = 0; i < input.length; i++) {
            List<Long> temp = new ArrayList<>();
            previous = new Long(input[i]);
            temp.add(new Long(i+1));
            seen.put(new Long(input[i]), temp);
        }

        while (k <= target) {
            if (seen.containsKey(previous)) {
                if (seen.get(previous).size() == 1) {
                    if (seen.get(new Long(0)) == null) {
                        seen.put(new Long(0), new ArrayList<Long>());
                    }
                    List<Long> temp = seen.get(new Long(0));
                    fixedAdd(temp, k, 2);
                    previous = 0;
                } else {
                    List<Long> list = seen.get(previous);
                    long result = 0;

                    for (int j = list.size() -1; j >=0; j--) {
                        result = Math.abs(result) - list.get(j);
                    }
                    List<Long> temp = new ArrayList<>();
                    if (seen.containsKey(result)) {
                        temp = seen.get(result);
                        fixedAdd(temp, k, 2);
                    } else {
                        fixedAdd(temp, k, 2);
                    }
                    seen.put(result, temp);
                    previous = result;
                }
            } else {
                List<Long> temp = new ArrayList<>();
                fixedAdd(temp, k ,2);
                seen.put(previous, temp);
            }
            if (k == target) {
                System.out.println("Turn " + k + " Previous " + previous);
                return previous;
            }
            // System.out.println("previous " + previous + " turn " + k);
            k++;
            
        }
      return -1;
    }


  public static void main(String[] args) {
    RambunctiousRecitation rr = new RambunctiousRecitation();
    // int[] input = new int[]{0, 3, 6};
    // int[] input = new int[]{1, 3, 2};
    // int[] input = new int[]{2, 1, 3};
    // int[] input = new int[]{1, 2, 3};
    // int[] input = new int[]{2, 3, 1};
    // int[] input = new int[]{3, 2, 1};
    // int[] input = new int[]{3, 1, 2};
    int[] input = new int[]{6,19,0,5,7,13,1};
    long result = rr.findNumber(input, 2020);
    System.out.println("part 1 " + result); // 468

    long result2 = rr.findNumber(input, 30000000);
    System.out.println("part 2 " + result2); // 1801753
  }
}