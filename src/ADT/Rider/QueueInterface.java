package ADT.Rider;
import java.util.ListIterator;

public interface QueueInterface<T> {
    public boolean enqueue(T newEntry);

    public boolean dequeue();

    public boolean dequeue(int position);

    public T peek();

    public boolean isEmpty();

    public void clear();

    public int size();

    public T get(int position);

    public boolean contains(T element);

    public int indexOf(T element);

    public boolean set(int position, T newEntry);

    public String toString();

    public ListIterator<T> getListIterator(int position);

    public ListIterator<T> getListIterator();
}
