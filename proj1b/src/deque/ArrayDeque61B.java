package deque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static java.lang.Math.floorMod;

public class ArrayDeque61B<T> implements Deque61B<T>{
    private T[] item;
    private int size;
    private int first;
    private int last;
    public int arraysize;
    public ArrayDeque61B(){
    item = (T[]) new Object[8];
    size = 0;
    arraysize = 8;
    first = 1;
    last = 2;
    }
    @Override
    public void addFirst(T x) {
        if (size >= 3 * arraysize / 4){
            T[] newItem = (T[]) new Object[2 * arraysize];
            newItem[0] = x;
            int cnt = 0;
            int i = 1;
            while (cnt != size){
                newItem[i] = item[(first + 1) % arraysize];
                item[(first + 1) % arraysize] = null;
                first = (first + 1) % arraysize;
                cnt++;
                i++;
            }
            item = newItem;
            arraysize = 2 * arraysize;
            size++;
            first = 0;
            last = size - 1;
        }else {
            item[first] = x;
            first = floorMod(first - 1, arraysize);
            size++;
        }

    }

    @Override
    public void addLast(T x) {
        if (size >= 3 * arraysize / 4){
            T[] newItem = (T[]) new Object[2 * arraysize];
            newItem[size] = x;
            int cnt = size - 1;
            while (cnt >= 0){
                newItem[cnt] = item[floorMod(last - 1, arraysize)];
                item[floorMod(last - 1, arraysize)] = null;
                last = floorMod(last - 1, arraysize);
                cnt--;
            }
            item = newItem;
            size++;
            arraysize = 2 * arraysize;
            first = 0;
            last = size - 1;
        }else{
            item[last] = x;
            last = (last + 1) % arraysize;
            size++;
        }
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < arraysize; i++){
            returnList.add(get(i));
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        T result = item[(first + 1) % arraysize];
        item[(first + 1) % arraysize] = null;
        first = (first + 1) % arraysize;
        size--;
        return result;
    }

    @Override
    public T removeLast() {
        T result = item[floorMod(last - 1, arraysize)];
        item[floorMod(last - 1, arraysize)] = null;
        last = floorMod(last - 1, arraysize);
        size--;
        return result;
    }

    @Override
    public T get(int index) {
        return item[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
    private class Myiter implements Iterator<T>{
        private int pos = 0;
        @Override
        public boolean hasNext() {
            return pos < arraysize;
        }

        @Override
        public T next() {
            T result = get(pos);
            pos++;
            return result;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new Myiter();
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ArrayDeque61B<?>)){
            return false;
        } else if (((ArrayDeque61B<?>) obj).size != size) {
            return false;
        }
        int cnt = size;
        while (cnt != 0){
            if (get(cnt) != ((ArrayDeque61B<?>) obj).get(cnt)) {
                return false;
            }
            cnt--;
        }
        return true;
    }
    @Override
    public String toString(){
        String result = "";
        int cnt = size;
        for (int i = 0; i < arraysize; i++){
            if (get(i) != null && cnt != 1){
                result = result + get(i) + ", ";
                cnt--;
            } else if (get(i) != null && cnt == 1) {
                result = result + get(i);
                break;
            }
        }
        return "[" + result + "]";
    }
}
