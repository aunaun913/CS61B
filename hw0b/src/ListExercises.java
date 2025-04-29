import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        int result = 0;
        for(int item : L){
            result += item;
        }
        return result;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> result = new ArrayList<>();
        for (int item : L){
            if (item % 2 == 0){
                result.add(item);
            }
        }
        return result;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> result = new ArrayList<>();
        for (int item : L1){
            if (L2.contains(item)){
                result.add(item);
            }
        }
        return result;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int result = 0;
        for (String item : words) {
            for (int i = 0; i < item.length(); i++) {
                if (item.charAt(i) == c) {
                    result += 1;
                }
            }
        }
        return result;
    }
}