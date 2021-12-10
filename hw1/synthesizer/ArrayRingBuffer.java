// TODO: Make sure to make this class a part of the synthesizer package
 package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T>  extends AbstractBoundedQueue<T>{
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int nextLast;
    /* Array for storing the buffer data. */
    private T[] rb;

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator{

        int size;
        int t;

        public ArrayIterator() {
            this.size = 0;
            this.t = first;
        }

        @Override
        public boolean hasNext() {
            return size < fillCount;
        }

        @Override
        public T next() {
            T tmp = rb[t];
            size++;
            t = (t == capacity-1)? 0: t+1;
            return tmp;
        }
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.capacity = capacity;
        this.fillCount = 0;
        rb = (T[]) new Object[capacity];
        this.first = 0;
        this.nextLast = 0;
    }

    private int next(int x) {
        if (x == capacity - 1) {
            return 0;
        } else {
            return x + 1;
        }
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if(isFull()){
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[nextLast] = x;
        if(fillCount==0){
            first = nextLast;
        }
        nextLast = next(nextLast);
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if(isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }
        T tmp = rb[first];
        rb[first] = null;
        fillCount--;
        first = next(first);
        return tmp;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if(isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
