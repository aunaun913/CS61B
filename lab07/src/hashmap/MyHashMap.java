package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int initialCapacity;
    private double loadFactor;
    private int num_bucket;
    private int size = 0;
    /** Constructors */
    public MyHashMap() {
        this.initialCapacity = 16;
        this.loadFactor = 0.75;
        num_bucket = initialCapacity;
        initialization();
    }
    public MyHashMap(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = 0.75;
        num_bucket = initialCapacity;
        initialization();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        num_bucket = initialCapacity;
        initialization();
    }
    public void initialization(){
        buckets = (Collection<Node>[]) new Collection[num_bucket];
        int a = 0;
        while (a != num_bucket){
            buckets[a] = createBucket();
            a++;
        }
    }
    public void rehashing(){
        Collection<Node>[] old_Buckets = buckets;
        int old_num_bucket = num_bucket;
        num_bucket = 2 * num_bucket;
        initialization();
        for(int i = 0; i < old_num_bucket; i++){
            for (Node item : old_Buckets[i]){
                int targetBoxNum = Math.floorMod(item.key.hashCode(), num_bucket);
                buckets[targetBoxNum].add(item);
            }
        }
    }
    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }


    @Override
    public void put(K key, V value) {
        if (!containsKey(key)){
            if((double) (size + 1) / num_bucket <= loadFactor){
                Node item = new Node(key, value);
                int targetBoxNum = Math.floorMod(item.key.hashCode(), num_bucket);
                buckets[targetBoxNum].add(item);
            }else {
                rehashing();
                Node item = new Node(key, value);
                int targetBoxNum = Math.floorMod(item.key.hashCode(), num_bucket);
                buckets[targetBoxNum].add(item);
            }
            size++;
        }else {
            int targetBoxNum = Math.floorMod(key.hashCode(), num_bucket);
            for (Node item : buckets[targetBoxNum]){
                if (item.key.equals(key)){
                    item.value = value;
                }
            }
        }
    }

    @Override
    public V get(K key) {
        int targetBoxNum = Math.floorMod(key.hashCode(), num_bucket);
        for (Node item : buckets[targetBoxNum]){
            if (item.key.equals(key)){
                return item.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int targetBoxNum = Math.floorMod(key.hashCode(), num_bucket);
        for (Node item : buckets[targetBoxNum]){
            if (item.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        num_bucket = initialCapacity;
        initialization();
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
