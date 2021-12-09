public class LinkedListDeque<T> implements Deque<T> {
    private class Node {
        public T item;
        public Node pre;
        public Node next;

        Node(T item, Node pre, Node next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }
    }

    private int size;
    private Node dummy;

    public LinkedListDeque() {
        size = 0;
        dummy = new Node(null, null, null);
    }

    @Override
    public void addFirst(T item) {
        Node tmp = new Node(item, null, dummy.pre);
        if (size == 0) {
            dummy.pre = tmp;
            dummy.next = tmp;
        } else {
            dummy.pre.pre = tmp;
            dummy.pre = tmp;
        }
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node tmp = new Node(item, dummy.next, null);
        if (size == 0) {
            dummy.pre = tmp;
            dummy.next = tmp;
        } else {
            dummy.next.next = tmp;
            dummy.next = tmp;
        }
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node t = dummy.next;
        while (t != null) {
            System.out.print(t.item);
            t = t.next;
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node first = dummy.pre;
        dummy.pre = first.next;
        if (size == 1) {
            dummy.next = null;
        }
        size -= 1;
        if (dummy.pre != null) {
            dummy.pre.pre = null;
        }
        return first.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node last = dummy.next;
        dummy.next = last.pre;
        if (size == 1) {
            dummy.pre = null;
        }
        size -= 1;
        if (dummy.next != null) {
            dummy.next.next = null;
        }
        return last.item;
    }

    @Override
    public T get(int index) {
        Node t = dummy.pre;
        for (int i = 0; i < index; i++) {
            t = t.next;
        }
        return t.item;
    }

    public T getRecursive(int index) {
        return getRecursiveHelp(index, dummy.pre);
    }

    private T getRecursiveHelp(int index, Node head) {
        if (head == null) {
            return null;
        } else if (index == 0) {
            return head.item;
        } else {
            return getRecursiveHelp(index - 1, head.next);
        }
    }
}
