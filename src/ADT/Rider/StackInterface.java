package ADT.Rider;

public interface StackInterface<T> {
    public T push(T newEntry);

    public T peek();

    public T pop();

    public boolean isEmpty();

    public T get(int position);

    public int getSize();

    public boolean contains(T searchEntry);

    public int indexOf(T searchEntry);

    public String toString();
}
