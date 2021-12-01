import java.io.File;
import java.util.stream.Collectors;
import java.util.*;
import java.io.*;

/**
 * SunnyChanceOfAsteriods
 */
public class SunnyChanceOfAsteriods {

  public int execute(int[] integerInputs) {
		int i = 0, length = input.length;
		outer:
		while (i < length) {
			/* Get this stuff sorted out */
			int opCode = input[i] % 100;
			int aMode = input[i] / 100 % 10;
			int bMode = input[i] / 1000 % 10;
			//int cMode = input[i] / 10000 % 10;
			/* We don't care about cMode at all */
			
			switch (opCode) {			
				case 1:
					input[input[i + 3]] = (aMode == 1 ? input[i + 1] : input[input[i + 1]]) 
						            + (bMode == 1 ? input[i + 2] : input[input[i + 2]]);
					i += 4;
					break;
				case 2:
					input[input[i + 3]] = (aMode == 1 ? input[i + 1] : input[input[i + 1]]) 
						            * (bMode == 1 ? input[i + 2] : input[input[i + 2]]);
					i += 4;
					break;
				case 3: 
					input[input[i+1]] = INPUT_VALUE; /* I think this what it means by provide input */
					i += 2;
					break;
				case 4: 
					output = input[input[i+1]];
					System.out.println("Output = " + output);
					i += 2;
					break;
				case 5:
					if ((aMode == 1) ? (input[i + 1] != 0) : (input[input[i + 1]] != 0)) 
						i = (bMode == 1 ? input[i + 2] : input[input[i + 2]]);
					else i += 3;
					break;
				case 6:
					if ((aMode == 1) ? (input[i + 1] == 0) : (input[input[i + 1]] == 0)) 
						i = (bMode == 1 ? input[i + 2] : input[input[i + 2]]);
					else i += 3;
					break;
				case 7:
					if ((aMode == 1 ? input[i + 1] : input[input[i + 1]]) < (bMode == 1 ? input[i + 2] : input[input[i + 2]]))
						input[input[i + 3]] = 1;
					else input[input[i + 3]] = 0;
					i += 4;
					break;
				case 8:
					if ((aMode == 1 ? input[i + 1] : input[input[i + 1]]) == (bMode == 1 ? input[i + 2] : input[input[i + 2]])) 
						input[input[i + 3]] = 1;
					else input[input[i + 3]] = 0;
					i += 4;
					break;
				case 99:
					break outer;
			}
		}
  }

  public static void main(String[] args) {
    SunnyChanceOfAsteriods object = new SunnyChanceOfAsteriods();
    try {
			File file = new File("./advent-of-code/2019/day5/input.txt");
      Scanner myReader = new Scanner(file);
			List<Integer> input = new ArrayList<>();
				
			while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					Arrays.asList(data.split(",")).stream().mapToInt(Integer::parseInt).collect(Collectors.toList());
			}
			myReader.close();
			System.out.println("CALLING");
      System.out.println("Output" + object.execute(input));
    } catch (Exception e) {
      System.err.println(e);
    }
    
  }
}