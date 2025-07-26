package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B <T> implements Deque61B<T> {
    private class Myiter implements Iterator<T>{
        private int pos = 0;
        @Override
        public boolean hasNext() {
            return pos < size;
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
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof LinkedListDeque61B<?>)){
            return false;
        } else if (((LinkedListDeque61B<?>)obj).size != size) {
            return false;
        }
        Node first = sentinel.next;
        Node otherFirst = (Node) ((LinkedListDeque61B<?>) obj).sentinel.next;
        while(first != sentinel){
            if (first.item != otherFirst.item || first.item != null && first.item.equals(otherFirst)){
                return false;
            }
            first = first.next;
            otherFirst = otherFirst.next;
        }
        return true;
    }
    @Override
    public String toString(){
        Node first = sentinel.next;
        String result = "[";
        while(first != sentinel.prev){
            result = result + first.item + ", ";
            first = first.next;
        }
        result = result + sentinel.prev.item + "]";
        return result;
    }
}
