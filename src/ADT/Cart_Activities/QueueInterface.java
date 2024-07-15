package ADT.Cart_Activities;

import java.util.Iterator;

public interface QueueInterface<T> {
    public void enqueue(T newEntry);
    public T dequeue();
    public T dequeue(int position);
    public T getFront();
    public T element(int position);
    public int size();
    public boolean isEmpty();
    public void clear();
    public boolean contains(T anEntry);
    public int positionOf(T anEntry);
    public Iterator<T> getIterator();
}
