package ADT.Cart_Activities;

import java.util.Iterator;

public class ArrayQueueDynamicFront<T> implements QueueInterface<T> {

    private T[] queueArray;
    private int headPosition;
    private int numEntries;
    private static final int DEFAULT_CAPACITY = 3;

    public ArrayQueueDynamicFront() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayQueueDynamicFront(int capacity) {
        queueArray = (T[]) new Object[capacity];
        headPosition = 1;
        numEntries = 0;
    }

    @Override
    public void enqueue(T newEntry) {
        if (isArrayFull()) {
            doubleArray();
        }

        numEntries++;
        queueArray[(headPosition - 1) + (numEntries - 1)] = newEntry;
    }

    @Override
    public T dequeue() {
        if (!isEmpty()) {
            T head = queueArray[headPosition - 1];
            queueArray[headPosition - 1] = null;
            headPosition++;
            numEntries--;

            return head;
        }

        return null;
    }

    @Override
    public T dequeue(int position) {
        if (position >= 1 && position <= numEntries) {
            T entry = queueArray[(headPosition - 1) + (position - 1)];
            shiftElements(headPosition + position - 1);
            numEntries--;

            return entry;
        }

        return null;
    }

    @Override
    public T getFront() {
        if (!isEmpty()) {
            return queueArray[headPosition - 1];
        }
        
        return null;
    }

    @Override
    public T element(int position) {
        if (position >= 1 && position <= numEntries) {
            return queueArray[(headPosition - 1) + (position - 1)];
        }
        
        return null;
    }
    
    @Override
    public int size() {
        return numEntries;
    }

    @Override
    public boolean isEmpty() {
        return numEntries == 0;
    }

    @Override
    public void clear() {
        headPosition = 1;
        numEntries = 0;
    }

    @Override
    public boolean contains(T anEntry) {
        return positionOf(anEntry) != -1;
    }

    @Override
    public int positionOf(T anEntry) {
        for (int i = headPosition; i <= (numEntries + headPosition - 1); i++) {
            if (queueArray[i - 1] == anEntry) {
                return (i - headPosition) + 1;
            }
        }

        return -1;
    }

    @Override
    public Iterator<T> getIterator() {
        return new ArrayQueueDynamicHeadIterator();
    }

    private class ArrayQueueDynamicHeadIterator<T> implements Iterator<T> {

        private int nextPosition;

        public ArrayQueueDynamicHeadIterator() {
            nextPosition = headPosition;
        }

        @Override
        public boolean hasNext() {
            return nextPosition <= (numEntries + headPosition - 1);
        }

        @Override
        public T next() {
            if (hasNext()) {
                T nextElement = (T) queueArray[nextPosition - 1];
                nextPosition++;
                return nextElement;
            }

            return null;
        }

    }

    private boolean isArrayFull() {
        return numEntries >= queueArray.length;
    }

    private void doubleArray() {
        T[] oldArray = queueArray;
        T[] newArray = (T[]) new Object[queueArray.length * 2];

        queueArray = newArray;

        for (int i = headPosition; i <= oldArray.length; i++) {
            queueArray[i - headPosition] = oldArray[i - headPosition];
        }
    }

    private void shiftElements(int position) {
        for (int i = position; i <= (headPosition + numEntries - 1); i++) {
            queueArray[i - 1] = queueArray[i];
        }
    }
}
