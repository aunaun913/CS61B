package deque;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class Maximizer61B {
    /**
     * Returns the maximum element from the given iterable of comparables.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable the Iterable of T
     * @return the maximum element
     */
    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        T max = null;
        for (T i : iterable) {
            if (max == null) {
                max = i;
            } else {
                if (max.compareTo(i) < 0) {
                    max = i;
                }
            }
        }
        if (max == null){
            return null;
        }
        return max;
    }

    /**
     * Returns the maximum element from the given iterable according to the specified comparator.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable the Iterable of T
     * @param comp     the Comparator to compare elements
     * @return the maximum element according to the comparator
     */

    public static <T> T max(Iterable<T> iterable, Comparator<T> comp) {
        T max = null;
        for (T i : iterable) {
            if (max == null) {
                max = i;
            } else if (i == null) {
                continue;
            } else {
                if (comp.compare(max, i) < 0) {
                    max = i;
                }
            }
        }
        if (max == null){
            return null;
        }
        return max;
    }
}