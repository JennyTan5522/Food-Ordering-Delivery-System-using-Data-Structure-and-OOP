package ADT.Customer;

import java.io.Serializable;
import java.util.Iterator;

public class ArrayList<T> implements ListInterface<T>, Serializable {

    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 5;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        numberOfEntries = 0;
        array = (T[]) new Object[initialCapacity];
    }

    @Override
    public boolean add(T newEntry) {
        // ensure enough space to accommate new entry
        ensureCapacity(numberOfEntries + 1);
        // add newEntry to the end of array
        array[numberOfEntries] = newEntry;
        numberOfEntries++;
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < numberOfEntries; i++) {
            // set all entry to null
            array[i] = null;
        }
        numberOfEntries = 0;
    }

    @Override
    public boolean contains(T anEntry) {
        // check if anEntry in array
        return getPositionOf(anEntry) >= 1;
    }

    @Override
    public ListInterface<T> copy() {
        ListInterface<T> copiedArray = new ArrayList<>();
        for (int i = 0; i < numberOfEntries; i++) {
            // copy entry of (old) array into copiedArray
            copiedArray.add(array[i]);
        }
        return copiedArray;
    }

    @Override
    public T getEntry(int givenPosition) {
        if (checkPosition(givenPosition)) {
            // return the entry with given position if givenPosition is between 1 to numberOfEntries
            return array[givenPosition - 1];
        } else {
            // return null if givenPosition is not between 1 to numberOfEntries
            return null;
        }
    }

    @Override
    public int getNumberOfEntries() {
        // return number of entries in array
        return numberOfEntries;
    }

    @Override
    public int getPositionOf(T anEntry) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (anEntry.equals(array[i])) {
                // return position if anEntry able to match with any entry in array
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        // check if the numberOfEntries is 0
        return numberOfEntries == 0;
    }

    @Override
    public boolean isFull() {
        // check if the numberOfEntries same with array length
        return numberOfEntries == array.length;
    }

    @Override
    public T remove(int givenPosition) {
        if (checkPosition(givenPosition)) {
            T removedEntry = array[givenPosition - 1];
            // fill up the gap of entry being removed if givenPosition is between 1 to numberOfEntries
            removeGap(givenPosition);
            numberOfEntries--;
            // return the entry being removed
            return removedEntry;
        } else {
            // return null if givenPosition is not between 1 to numberOfEntries
            return null;
        }
    }

    @Override
    public boolean remove(T anEntry) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (array[i].equals(anEntry)) {
                // fill up the gap of entry being removed if enEntry able to match with any entry in array
                removeGap(i + 1);
                numberOfEntries--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeFrom(int startPosition) {
        if (checkPosition(startPosition)) {
            for (int i = startPosition - 1; i < numberOfEntries; i++) {
                // set the entries in array starting from startPosition to null if startPosition is between 1 to numberOfEntries
                array[i] = null;
            }
            numberOfEntries = startPosition - 1;
        }
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        if (checkPosition(givenPosition)) {
            // replace newEntry at givenPosition if givenPosition is between 1 to numberOfEntries
            array[givenPosition - 1] = newEntry;
            return true;
        } else {
            // return null if givenPosition is not between 1 to numberOfEntries
            return false;
        }
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < numberOfEntries; index++) {
            outputStr += array[index] + "\n";
        }
        return outputStr;
    }

    @Override
    public Iterator<T> getIterator() {
        // return iterator that is able to iterate through all entries in array
        return new IteratorForArrayList();
    }

//  utility method
    private void ensureCapacity(int minCapacity) {
        int oldSize = array.length;
        if (minCapacity > oldSize) {
            // double the size of array if not enough space to accommodate new entry
            T[] newArray = (T[]) new Object[oldSize * 2];
            for (int i = 0; i < oldSize; i++) {
                // copy entries from (old)array to newArray
                newArray[i] = array[i];
            }
            array = newArray;
        }
    }

    private void removeGap(int givenPosition) {
        int removedIndex = givenPosition - 1;
        int lastIndex = numberOfEntries - 1;
        for (int index = removedIndex; index < lastIndex; index++) {
            // shifting the entries behind removed index to fill up the space of entry being removed
            array[index] = array[index + 1];
        }
    }

    private boolean checkPosition(int givenPosition) {
        if (givenPosition < 1 || givenPosition > numberOfEntries) {
            // return false if givenPosition is not between 1 and numberOfEntries
            return false;
        }
        return true;
    }

    private class IteratorForArrayList implements Iterator<T> {

        private int index;

        public IteratorForArrayList() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            // check if iterator has next entry
            return index < numberOfEntries;
        }

        @Override
        public T next() {
            if (hasNext()) {
                // return next entry if hasNext is true
                return (T) array[index++];
            } else {
                return null;
            }
        }
    }

}
