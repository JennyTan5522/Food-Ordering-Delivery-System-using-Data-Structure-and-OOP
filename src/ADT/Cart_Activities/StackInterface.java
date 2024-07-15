package ADT.Cart_Activities;

import java.util.Iterator;

public interface StackInterface<T> {
    public void push(T newEntry);
    public T pop();
    public T pop(int position);
    public T peek();
    public T peek(int position);
    public int size();
    public boolean isEmpty();
    public void clear();
    public boolean contains(T anEntry);
    public int search(T anEntry);
    public Iterator<T> getIterator();
}
