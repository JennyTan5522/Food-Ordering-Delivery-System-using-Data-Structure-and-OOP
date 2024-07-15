package ADT.Rider;

public class ArrayStack<T> implements StackInterface<T> {
    private T[] array;//Store generic type of stack array entries
    private int topIndex;// Top entry index
    private static final int DEFAULT_CAPACITY = 50;//Create a default array capacity of size 50

    //Constructs an empty array stack with an initial capacity of 50
    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    //Constructor with initial capacity given by user
    public ArrayStack(int initialCapacity) {
        //If capacity > 0 create array
        if (initialCapacity > 0) {
            array = (T[]) new Object[initialCapacity];
            topIndex = -1;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    //Adds a new item at the top of the stack
    @Override
    public T push(T newEntry) {
        topIndex++;
        if (isFull())
            ensureCapacity();
        else
            array[topIndex] = newEntry;
        return array[topIndex];
    }

    //Check whether array stack is full or not, if full returns false else returns true
    private boolean isFull() {
        return topIndex == array.length;
    }

    //Increase the ArrayStack capacity if necessary, to ensure that it can hold at least the number of elements specified by the minimum capacity argument
    private void ensureCapacity() {
        //If our variable size has become equal to the array length,we must multiply the array length *2
        //Example when create array list by default size =50 , if exceed size 50 we can use this method to check
        //Create a new array
        T[] newArray = (T[]) new Object[array.length * 2];
        //Assigning the value of the first array by index i to the new array by index i
        for (int i = 0; i < array.length; i++)
            newArray[i] = array[i];
        array = newArray;
    }

    //Remove the top entry on the stack
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new StackOverflowError();//If ArrayStack is empty
        } else {
            T top = array[topIndex--];
            return top;
        }
    }

    //View the top entry on the stack
    @Override
    public T peek() {
        if (isEmpty())
            throw new StackOverflowError();//If ArrayStack is empty
        else
            return array[topIndex];
    }

    //Check the stack whether it is empty or not
    @Override
    public boolean isEmpty() {
        return topIndex < 0;
    }

    //Get T based on the position given by the user
    @Override
    public T get(int position) {
        T result = null;
        //If user index is =0/negative or index>total size in array then throw exception
        if (indexValidation(position)) {
            result = array[position - 1];
        }
        return result;
    }

    //Index validation
    private boolean indexValidation(int position) {
        if (position <= 0 && position > topIndex) {
            throw new ArrayIndexOutOfBoundsException(position);
        }
        return true;
    }

    //Check whether the current stack contain checkEntry given by user or not
    @Override
    public boolean contains(T checkEntry) {
        if (linearSearch(checkEntry) != -1)
            return true;
        return false;
    }

    //Get the index of the searchEntry
    @Override
    public int indexOf(T searchEntry) {
        return linearSearch(searchEntry);
    }

    //Internal usage : Search in the stack
    private int linearSearch(T searchEntry) {
        for (int i = 0; i < getSize(); i++) {
            if (searchEntry.equals(array[i])) {
                return i + 1;
            }
        }
        return -1;
    }

    //Get the size of the stack
    @Override
    public int getSize() {
        return topIndex + 1;
    }

    @Override
    public String toString() {
        String toString = "";
        for (int i = 0; i < getSize(); i++) {
            toString += array[i] + " , ";
        }
        return toString;
    }

}
