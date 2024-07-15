package ADT.Restaurant;

import java.util.Iterator;

/**
 *
 * @author xuanyi
 */
public interface QueueInterface<T> {
    
    public boolean enqueue(T newEntry);
    public T dequeue();
    public boolean clear();
    public boolean isEmpty();
    public T getFront();
    public int getNumberOfEntries();
    
    public T search(T data);
    public boolean contains(T data);
    
    public Iterator<T> getIterator();
    

}
