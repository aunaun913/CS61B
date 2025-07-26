import de.erichseifert.vectorgraphics2d.VectorHints;

import java.util.Iterator;
import java.util.Set;

public class BSTMap <K extends Comparable<K>, V> implements Map61B{
    public class Node{
        K key;
        V value;
        Node left;
        Node right;
        public Node(K k, V v){
            key = k;
            value = v;
        }
    }
    Node root = null;
    int size = 0;
    public void fixNode(Node n, K puttedKey, V puttedValue){
        if (n.key.compareTo(puttedKey) == 0) {
            n.value = puttedValue;
        }else if (n.key.compareTo(puttedKey) < 0) {
            if (n.right == null) {
                n.right = new Node(puttedKey, puttedValue);
                size++;
            } else {
                fixNode(n.right, puttedKey, puttedValue);
            }
        }else {
            if (n.left == null) {
                n.left = new Node(puttedKey, puttedValue);
                size++;
            } else {
                fixNode(n.left, puttedKey, puttedValue);
            }
        }
    }
    public V helperGet(Node n, K key){
        if (n == null){
            return null;
        } else if (n.key.compareTo(key) == 0) {
            return n.value;
        } else if (n.key.compareTo(key) < 0) {
            return helperGet(n.right, key);
        }else {
            return helperGet(n.left, key);
        }
    }
    public boolean helperCK(Node n, K key){
        if (n == null){
            return false;
        } else if (n.key.compareTo(key) == 0) {
            return true;
        } else if (n.key.compareTo(key) < 0) {
            return helperCK(n.right, key);
        }else {
            return helperCK(n.left, key);
        }
    }
    @Override
    public void put(Object key, Object value) {
        if (root == null){
            root = new Node((K) key, (V) value);
            size++;
        }else {
            fixNode(root, (K) key, (V) value);
        }
    }

    @Override
    public Object get(Object key) {
        return helperGet(root, (K) key);
    }

    @Override
    public boolean containsKey(Object key) {
        return helperCK(root, (K) key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }
}
