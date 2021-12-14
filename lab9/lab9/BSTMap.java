package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    private Set<K> keySet;

    /* Creates an empty BSTMap. */
    public BSTMap() {
        keySet = new HashSet<>();
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.equals(p.key)) {
            return p.value;
        }
        if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    private void keySetHelper(Node node) {
        if (node == null) {
            return;
        }
        keySet.add(node.key);
        keySetHelper(node.left);
        keySetHelper(node.right);
    }


    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        keySetHelper(root);
        return keySet;
    }

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        V res = get(key);
        if (res != null) {
            root = remove(key, root);
        }
        return res;
    }

    private Node remove(K key, Node node) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = remove(key, node.left);
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(key, node.right);
        } else {
            if (node.right == null) {
                size--;
                return node.left;
            }
            if (node.left == null) {
                size--;
                return node.right;
            }
            Node min = min(node.right);
            min.right = deletemin(node.right);
            min.left = node.left;
            node = min;
        }
        return node;
    }

    private Node deletemin(Node node) {
        if (node.left == null) {
            size--;
            return node.right;
        }
        node.left = deletemin(node.left);
        return node;
    }

    private Node min(Node node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) != value) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    public static void main(String[] args) {
        BSTMap<Integer,Integer> map = new BSTMap<>();
        map.put(4,4);
        map.put(2,2);
        map.put(6,6);
        map.put(5,5);
        map.put(7,7);
        map.remove(4);
        System.out.println(map.keySet());
        System.out.println(map.root.value);
    }
}
