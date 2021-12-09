public class ArrayDeque<T> {

    private T[] array;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        size = 0;
        array = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
    }

    private int next(int x) {
        if (x == array.length - 1) {
            return 0;
        } else {
            return x + 1;
        }
    }

    private int pre(int x) {
        if (x == 0) {
            return array.length - 1;
        } else {
            return x - 1;
        }
    }

    private void resize(int x) {
        T[] tmp = (T[]) new Object[x];
        int start = (x - size) / 2;
        int end = start + size - 1;
        int head = next(nextFirst);
        for (int i = start; i <= end; i++) {
            tmp[i] = array[head];
            head = next(head);
        }
        nextFirst = start - 1;
        nextLast = end + 1;
        array = tmp;
    }

    private void check() {
        if (nextFirst == nextLast) {
            resize((int) (size * 1.5));
        } else if (size >= 16 && ((double) size / array.length < 0.25)) {
            resize((int) (array.length * 0.25));
        }
    }

    public void addFirst(T item) {
        array[nextFirst] = item;
        nextFirst = pre(nextFirst);
        size++;
        check();
    }

    public void addLast(T item) {
        array[nextLast] = item;
        nextLast = next(nextLast);
        size++;
        check();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int head = next(nextFirst);
        int tail = pre(nextLast);

        if (size == 0) {
            System.out.println("");
        } else {
            while (head != tail) {
                System.out.print(array[head] + " ");
                head = next(head);
            }
            System.out.print(array[head]);

            /*
            if (head < tail) {
                for (int i = head; i <= tail; i++) {
                    System.out.print(array[i] + " ");
                }
            } else {
                for (int i = head; i < array.length; i++) {
                    System.out.print(array[i] + " ");
                }
                for (int i = 0; i <= tail; i++) {
                    System.out.print(array[i] + " ");
                }
            }
             */
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int head = next(nextFirst);
        T tmp = array[head];
        array[head] = null;
        nextFirst = head;
        size--;
        check();
        return tmp;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int tail = pre(nextLast);
        T tmp = array[tail];
        array[tail] = null;
        nextLast = tail;
        size--;
        check();
        return tmp;
    }

    public T get(int index) {
        if (size == 0 || index >= size) {
            return null;
        }
        int cur = next(nextFirst);
        int tmp = index;
        while (tmp != 0) {
            cur = next(cur);
            tmp--;
        }
        return array[cur];
    }
}
