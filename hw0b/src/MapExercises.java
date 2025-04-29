import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> m = new HashMap<>();
        for (char c = 'a'; c <= 'z'; c++){
            m.put(c, c - 'a' + 1);
        }
        return m;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int item : nums){
            m.put(item, item*item);
        }
        return m;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> m = new HashMap<>();
        for (String item : words) {
            if (m.containsKey(item)) {
                m.put(item, m.get(item) + 1);
            } else {
                m.put(item, 1);
            }
        }
        return m;
    }
}
