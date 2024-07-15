package ADT.Cart_Activities;

import java.util.ListIterator;
import java.util.Comparator;

public class DoublyLinkedList<T> implements ListInterface<T> {

    private Node head;
    private Node tail;
    private int numEntries;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        numEntries = 0;
    }

    @Override
    public void add(T newEntry) {
        if (isEmpty()) {
            Node newNode = new Node(newEntry);
            head = newNode;
            tail = newNode;
        } else if (head == tail) {
            Node newNode = new Node(newEntry, tail);
            head.next = newNode;
            tail = newNode;
        } else {
            Node newNode = new Node(newEntry, tail);
            tail.next = newNode;
            tail = newNode;
        }

        numEntries++;
    }

    @Override
    public boolean add(T newEntry, int position) {
        int currPosition = 1;
        Node currNode = head;

        if (position >= 1 && position <= numEntries + 1) {
            while (currNode != null) {
                if (currPosition == position) {
                    Node newNode = new Node(newEntry, currNode.previous, currNode);
                    currNode.previous.next = newNode;
                    currNode.previous = newNode;
                    numEntries++;
                    
                    return true;
                }

                currNode = currNode.next;
                currPosition++;
            }
        }

        return false;
    }

    @Override
    public T remove(int position) {
        int currPosition = 1;
        Node currNode = head;

        if (position >= 1 && position <= numEntries) {
            while (currNode != null) {
                if (currPosition == position) {
                    if (head.equals(currNode)) {
                        head = currNode.next;
                    } else {
                        currNode.previous.next = currNode.next;
                    }

                    if (tail.equals(currNode)) {
                        tail = currNode.previous;
                    } else {
                        currNode.next.previous = currNode.previous;
                    }

                    numEntries--;
                    
                    return currNode.data;
                }

                currNode = currNode.next;
                currPosition++;
            }
        }

        return null;
    }

    @Override
    public T removeFirst() {
        if (!isEmpty()) {
            Node removedNode = head;
            remove(1);

            return removedNode.data;
        }

        return null;
    }

    @Override
    public T removeLast() {
        if (!isEmpty()) {
            Node removedNode = tail;
            remove(numEntries);

            return removedNode.data;
        }

        return null;
    }

    @Override
    public boolean replace(T newEntry, int position) {
        int currPosition = 1;
        Node currNode = head;

        if (position >= 1 && position <= numEntries) {
            while (currNode != null) {
                if (currPosition == position) {
                    currNode.data = newEntry;
                    
                    return true;
                }

                currNode = currNode.next;
                currPosition++;
            }
        }

        return false;
    }

    @Override
    public T get(int position) {
        int currPosition = 1;
        Node currNode = head;

        if (position >= 1 && position <= numEntries) {
            while (currNode != null) {
                if (currPosition == position) {
                    return currNode.data;
                }

                currNode = currNode.next;
                currPosition++;
            }
        }

        return null;
    }

    @Override
    public T getFirst() {
        if (!isEmpty()) {
            return head.data;
        }

        return null;
    }

    @Override
    public T getLast() {
        if (!isEmpty()) {
            return tail.data;
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
    public boolean isFull() {
        return false;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        numEntries = 0;
    }

    @Override
    public boolean contains(T anEntry) {
        return positionOf(anEntry) != -1;
    }

    @Override
    public int positionOf(T anEntry) {
        int position = 1;
        Node currNode = head;

        while (currNode != null) {
            if (currNode.data == anEntry) {
                return position;
            }

            currNode = currNode.next;
            position++;
        }

        return -1;
    }

    @Override
    public void copy(ListInterface<T> newList) {
        Node currNode = head;

        while (currNode != null) {
            newList.add(currNode.data);
            currNode = currNode.next;
        }
    }

    public void sort(Comparator comparator) {
        boolean sorted = false;

        for (int pass = 1; pass < numEntries && !sorted; pass++) {
            sorted = true;

            for (int position = 1; position <= numEntries - pass; position++) {
                if (comparator.compare(get(position), get(position + 1)) == -1) {
                    T temp = get(position);
                    replace(get(position + 1), position);
                    replace(temp, position + 1);
                    sorted = false;
                }
            }
        }
    }

    @Override
    public ListIterator<T> getListIterator() {
        return new DoublyLinkedListIterator();
    }

    @Override
    public ListIterator<T> getListIterator(int position) {
        if (position >= 1 && position <= numEntries) {
            return new DoublyLinkedListIterator(position);
        }

        return null;
    }

    private class DoublyLinkedListIterator<T> implements ListIterator<T> {

        Node currNode;

        public DoublyLinkedListIterator() {
            currNode = head;
        }

        public DoublyLinkedListIterator(int position) {
            int currPosition = 1;
            currNode = head;

            while (currNode != null) {
                if (currPosition == position) {
                    break;
                }

                currPosition++;
                currNode = currNode.next;
            }
        }

        @Override
        public boolean hasNext() {
            return currNode != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T currData = (T) currNode.data;
                currNode = currNode.next;

                return currData;
            }

            return null;
        }

        @Override
        public boolean hasPrevious() {
            return currNode != null;
        }

        @Override
        public T previous() {
            if (hasPrevious()) {
                T currData = (T) currNode.data;
                currNode = currNode.previous;

                return currData;
            }

            return null;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void set(T e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void add(T e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class Node {

        private T data;
        private Node previous;
        private Node next;

        public Node(T data, Node previous, Node next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }

        public Node(T data, Node node) {
            this.data = data;
            this.previous = node;
            next = null;
        }

        public Node(T data) {
            this.data = data;
            previous = null;
            next = null;
        }
    }
}
