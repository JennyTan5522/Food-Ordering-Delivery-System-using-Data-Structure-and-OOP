package ADT.Customer;

import java.util.Iterator;

public interface ListInterface<T> {

    // append newEntry to the end of the list
    public boolean add(T newEntry);

    // remove all of the entries from the list
    public void clear();

    // check whether the list contains anEntry
    public boolean contains(T anEntry);

    // copy all of the entries from the list to a new list
    public ListInterface<T> copy();

    // retrieve the entry at givenPosition in the list
    public T getEntry(int givenPosition);

    // get total number of entries in the list
    public int getNumberOfEntries();

    // get the position of first occurrence of anEntry in the list
    public int getPositionOf(T anEntry);

    // check whether the list is empty
    public boolean isEmpty();

    // check whether the list is full
    public boolean isFull();

    // remove the entry at givenPosition in the list
    public T remove(int givenPosition);

    // remove anEntry in the list
    public boolean remove(T anEntry);

    // remove all of the entries from the list starting from startPosition
    public void removeFrom(int startPosition);

    // replace the entry at givenPosition in the list with newEntry
    public boolean replace(int givenPosition, T newEntry);

    // get the iterator for the list
    public Iterator<T> getIterator();

}
