import java.util.*;
import java.io.*;
/**
 * PassportProcessing
 */
public class PassportProcessing {
  Set<String> keys;
  List<String> eyeColor;
  PassportProcessing() {
    eyeColor = Arrays.asList(new String[]{
        "amb", "blu", "brn", "gry", "grn", "hzl","oth"
    });
  }

 // PART 1 solution
  public boolean validatePassports(String input) {
      String[] fields = input.split(" ");
      keys = new HashSet<String>();
      keys.addAll(Arrays.asList("byr","iyr","eyr","hgt","hcl","ecl","pid"));

      for (String field: fields) {
          String fieldName = field.trim().split(":")[0];
          if (keys.contains(fieldName)) {
              keys.remove(fieldName);
          }
      }

      if (keys.size() > 0) {
        return (keys.size() == 1 && keys.contains("cid"));
      }

      return true;
  }

  public boolean validationAndBoundaryCheck(String credential, int credentialLength, int min, int max) {
    if (credential.length() != credentialLength) return false;

    int credentialValue = Integer.parseInt(credential);
    return credentialValue >= min && credentialValue <= max;
  }

  // PART 2 solution
  public boolean validateCredential(String key, String credential) {
    switch(key) {
        case "byr":
        // (Birth Year) - four digits; at least 1920 and at most 2002.
        return validationAndBoundaryCheck(credential, 4, 1920, 2002);
        case "iyr":
        // (Issue Year) - four digits; at least 2010 and at most 2020.
        return validationAndBoundaryCheck(credential, 4, 2010, 2020);
        case "eyr":
        // (Expiration Year) - four digits; at least 2020 and at most 2030.
            return validationAndBoundaryCheck(credential, 4, 2020, 2030);
        case "hgt":
        // (Height) - a number followed by either cm or in:
        // If cm, the number must be at least 150 and at most 193.
        // If in, the number must be at least 59 and at most 76.
            if (credential.contains("cm")) {
                String centimeters = credential.replaceAll("(cm|in)", "");
                return validationAndBoundaryCheck(centimeters, 3, 150, 193);
            } else if (credential.contains("in")) {
                String inches = credential.replaceAll("(cm|in)", "");
                return validationAndBoundaryCheck(inches, 2, 59, 76);
            } 
            return false;

        case "hcl":
        // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
            return credential.matches("^#[0-9a-z]{6}$");
        case "ecl":
        // eyl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.)
            return eyeColor.contains(credential.toLowerCase());
        case "pid":
        // pid (Passport ID) - a nine-digit number, including leading zeroes.
            return credential.matches("^[0-9]{9}$");
        case "cid":
        // cid (Country ID) - ignored, missing or not.
            return true;
        default:
            return false;

    }
  }

  public boolean validateCredentials(String input){
    String[] fields = input.split(" ");
    boolean invalidCredential = true;

    for (String field: fields) {
        String[] credentials = field.trim().split(":");
        if (!validateCredential(credentials[0].trim(), credentials[1].trim())) {
            invalidCredential = false;
            break;
        }
    }
    return invalidCredential;
  }
  
  public static void main(String[] args) {
    int result = 0;

    try {
        File myObject = new File("advent-of-code/2020/day4/input.txt");
        Scanner myReader = new Scanner(myObject);

        List<String> input = new ArrayList<>();

        String text = "";
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data.length() == 0) {
                input.add(text);
                text = "";
            } else {
                text += data +  " ";
            }
        }
        myReader.close();
        // last line is getting skipped
        input.add(text); 
        PassportProcessing x = new PassportProcessing();

        for (String s : input) {
            if (x.validatePassports(s) && x.validateCredentials(s)) {
                result++;
            }
        }
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

    //   NOTE: PART 1 is subset of part 2, remove x.validateCredentials(s) that will be result 1
      System.out.println("result PART 2 "+ result); 
  }
}