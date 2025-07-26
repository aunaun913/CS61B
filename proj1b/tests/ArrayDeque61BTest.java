import deque.ArrayDeque61B;

import deque.Deque61B;
import deque.LinkedListDeque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }
    @Test
    public void testGet(){
        ArrayDeque61B<Object> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addLast(7); // [4, 3, 7, null, null, null, null, 5]
        assertThat(lld1.get(1)).isEqualTo(3);
        assertThat(lld1.get(7)).isEqualTo(5);
        assertThat(lld1.get(6)).isEqualTo(null);
    }
    @Test
    public void testIsEmpty(){
        ArrayDeque61B<Object> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addLast(7); // [4, 3, 7, null, null, null, null, 5]
        ArrayDeque61B<Object> lld2 = new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isEqualTo(false);
        assertThat(lld2.isEmpty()).isEqualTo(true);
    }
    @Test
    public void testSize(){
        ArrayDeque61B<Object> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addLast(7); // [4, 3, 7, null, null, null, null, 5]
        ArrayDeque61B<Object> lld2 = new ArrayDeque61B<>();
        assertThat(lld1.size()).isEqualTo(4);
        assertThat(lld2.size()).isEqualTo(0);
    }
    @Test
    public void testToList(){
        ArrayDeque61B<Object> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addLast(7); // [4, 3, 7, null, null, null, null, 5]
        assertThat(lld1.toList()).containsExactly(4, 3, 7, null, null, null, null, 5).inOrder();
    }
    @Test
    public void testRemoveFirst(){
        ArrayDeque61B<Object> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addLast(7); // [4, 3, 7, null, null, null, null, 5]
        assertThat(lld1.removeFirst()).isEqualTo(5); // [4, 3, 7, null, null, null, null, null]
        assertThat(lld1.toList()).containsExactly(4, 3, 7, null, null, null, null, null).inOrder();
    }
    @Test
    public void testRemoveLast(){
        ArrayDeque61B<Object> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addLast(7); // [4, 3, 7, null, null, null, null, 5]
        assertThat(lld1.removeLast()).isEqualTo(7); // [4, 3, null, null, null, null, null, 5]
        assertThat(lld1.toList()).containsExactly(4, 3, null, null, null, null, null, 5).inOrder();
    }
    @Test
    public void testResizeUp() {
         // lld1 is triggered by addFirst function, lld3 is triggered by addLast function.
        ArrayDeque61B<Object> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addLast(7);
        lld1.addLast(8);
        lld1.addFirst(0);
        lld1.addFirst(1); // [4, 3, 7, 8, null, 1, 0, 5] content bigger than 75% arraysize.
        // lld1 --> [1, 0, 5, 4, 3, 7, 8, null, ... , null] arraysize = 16, ...stand for all omited nulls; 1 is first, 8 is last.
        ArrayDeque61B<Object> lld2 = new ArrayDeque61B<>();
        lld2.addFirst(3);
        lld2.addFirst(4);
        lld2.addFirst(5);
        lld2.addLast(7); // [4, 3, 7, null, null, null, null, 5] no resize.
        ArrayDeque61B<Object> lld3 = new ArrayDeque61B<>();
        lld3.addFirst(3);
        lld3.addFirst(4);
        lld3.addFirst(5);
        lld3.addLast(7);
        lld3.addLast(1);
        lld3.addLast(2);
        lld3.addLast(8); // [4, 3, 7, 1, 2, 8, null, 5] content bigger than 75% arraysize.
        // lld3 --> [5, 4, 3, 7, 1, 2, 8, null, ... ,null] arraysize = 16, ...stand for all omited nulls; 1 is first, 8 is last.
        assertThat(lld1.toList()).containsExactly(1, 0, 5, 4, 3, 7, 8, null, null, null, null, null, null, null, null, null).inOrder();
        assertThat(lld2.toList()).containsExactly(4, 3, 7, null, null, null, null, 5).inOrder();
        assertThat(lld1.size()).isEqualTo(7);
        assertThat(lld1.arraysize).isEqualTo(16);
        assertThat(lld3.size()).isEqualTo(7);
        assertThat(lld3.arraysize).isEqualTo(16);
        assertThat(lld3.toList()).containsExactly(5, 4, 3, 7, 1, 2, 8, null, null, null, null, null, null, null, null, null).inOrder();
    }
    @Test
    public void addLastTestBasicWithoutToList() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1).containsExactly("front", "middle", "back");
    }
    @Test
    public void testEqualLinkedListDeque61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    public void testEqualArrayListDeque61B() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    public void testLinkedListToString() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");


        String actual = lld1.toString();
        String expect = "[front, middle, back]";
        assert actual.equals(expect) : "Test failed: Expected " + expect + " but got " + actual;
        System.out.println(lld1);
    }
    @Test
    public void ArrayListToString() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");


        String actual = lld1.toString();
        String expect = "[front, middle, back]";
        assert actual.equals(expect) : "Test failed: Expected " + expect + " but got " + actual;
        System.out.println(lld1);
    }
}
