package day1;

import java.util.*;
import java.io.*;

class FuelCalculator {

    public Long fuelCalculate(Long fuel) {
        return Math.floorDiv(fuel, 3) - 2;
    }

    public Long totalFuelNeeded(List<Long> list) {
        Long result = 0L;
        for (Long fuel : list) {
            result += this.fuelCalculate(fuel);
        }

        return result;
    }

    public boolean isNegativeFuel(long input) {
        return (Math.floor((double) input/3.0) - 2) <= 0.0;
    }

    public long totalAdditionalFuel(List<Long> list) {
        Long result = 0L;
        for (Long fuel : list) {
            while (!this.isNegativeFuel(fuel)) {
                long temp = this.fuelCalculate(fuel);
                result += temp;
                fuel = temp;
            }
        }

        return result;
    }
    
    public static void main(String[] args) {
        try {
            System.out.println(new File(".").getAbsolutePath());
            FuelCalculator obj = new FuelCalculator();
            File file = new File("./advent-of-code/2019/day1/input.txt");
            Scanner myReader = new Scanner(file);
            List<Long> input = new ArrayList<Long>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                input.add(Long.parseLong(data));
            }
            myReader.close();

            Long total = obj.totalFuelNeeded(input);
            System.out.println("Total Fuel for Part 1 " + total);
            // Total Fuel for Part 1 3285627

            Long totalAdditionalFuel = obj.totalAdditionalFuel(input);
            System.out.println("Total Additional Fuel for Part 2 " + totalAdditionalFuel);
            // Total Additional Fuel for Part 2 4925580
        }  catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}