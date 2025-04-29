import java.util.ArrayList;
import java.util.List;

public class JavaExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        int[] result = new int[]{1, 2, 3, 4, 5, 6};
        return result;
    }

    /** Returns the order depending on the customer.
     *  If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        if (customer.equals("Ergun")){
            return new String[]{"beyti", "pizza", "hamburger", "tea"};
        }else if (customer.equals("Erik")) {
            return new String[]{"sushi", "pasta", "avocado", "coffee"};
        }else{
            return new String[3];
        }
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        int maxValue = array[0];
        int minValue = array[0];
        for (int item : array){
            if (item > maxValue){
                maxValue = item;
            } else if (item < minValue) {
                minValue = item;
            }
        }
        return maxValue - minValue;
    }

    /**
      * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
      * Hailstone sequence is described as:
      *    - Pick a positive integer n as the start
      *        - If n is even, divide n by 2
      *        - If n is odd, multiply n by 3 and add 1
      *    - Continue this process until n is 1
      */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        if (x == 1) {
            list.add(x);
            return list;
        } else if (x % 2 == 0) {
            list.add(x);
            x = x / 2;
            return hailstoneHelper(x, list);
        } else {
            list.add(x);
            x = x * 3 + 1;
            return hailstoneHelper(x, list);
        }
    }
}
