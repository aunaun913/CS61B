import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B <T> implements Deque61B<T> {
    public class Node{
        T item;
        Node prev;
        Node next;
    }
    private Node sentinel;
    private int size;
    public LinkedListDeque61B(){
        this.sentinel = new Node();
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
        this.size = 0;
    }
    @Override
    public void addFirst(T x) {
        Node NewNode = new Node();
        NewNode.item = x;
        NewNode.next = this.sentinel.next;
        NewNode.prev = this.sentinel;
        this.sentinel.next.prev = NewNode;
        this.sentinel.next = NewNode;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node NewNode = new Node();
        NewNode.item = x;
        NewNode.prev = this.sentinel.prev;
        NewNode.next = this.sentinel;
        this.sentinel.prev.next = NewNode;
        this.sentinel.prev = NewNode;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnedList = new ArrayList<>();
        Node rightNow = this.sentinel.next;
        while (rightNow != this.sentinel){
            returnedList.add(rightNow.item);
            rightNow = rightNow.next;
        }
        return returnedList;
    }

    @Override
    public boolean isEmpty() {
        if (this.size == 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (this.size == 0){
            return null;
        }
        T returnedItem = this.sentinel.next.item;
        this.sentinel.next.next.prev = this.sentinel;
        this.sentinel.next = this.sentinel.next.next;
        return returnedItem;
    }

    @Override
    public T removeLast() {
        if (this.size == 0){
            return null;
        }
        T returnedItem = this.sentinel.prev.item;
        this.sentinel.prev.prev.next = this.sentinel;
        this.sentinel.prev = this.sentinel.prev.prev;
        return returnedItem;
    }

    @Override
    public T get(int index) {
        Node rightNow = this.sentinel.next;
        if (index > this.size || index < 0){
            return null;
        }
        while (index != 0){
            rightNow = rightNow.next;
            index -= 1;
        }
        return rightNow.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index > this.size || index < 0){
            return null;
        }
        return getRecursiveHelper(this.sentinel.next, index);
    }
    private T getRecursiveHelper(Node currentNode, int index){
        if (index == 0){
            return currentNode.item;
        }else {
            return getRecursiveHelper(currentNode.next, index - 1);
        }
    }
}
