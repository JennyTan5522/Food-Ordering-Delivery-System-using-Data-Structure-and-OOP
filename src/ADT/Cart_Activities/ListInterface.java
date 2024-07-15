package ADT.Cart_Activities;

import java.util.ListIterator;

public interface ListInterface<T> {
    public void add(T newEntry);
    public boolean add(T newEntry, int position);
    public T remove(int position);
    public T removeFirst();
    public T removeLast();
    public boolean replace(T newEntry, int position);
    public T get(int position);
    public T getFirst();
    public T getLast();
    public int size();
    public boolean isEmpty();
    public boolean isFull();
    public void clear();
    public boolean contains(T anEntry);
    public int positionOf(T anEntry);
    public void copy(ListInterface<T> newList);
    public ListIterator<T> getListIterator();
    public ListIterator<T> getListIterator(int position);
}
