package ADT.Cart_Activities;

import java.util.Iterator;

public class LinkedStack<T> implements StackInterface<T> {
    private Node head;
    private int numEntries;
    
    public LinkedStack() {
        head = null;
        numEntries = 0;
    }
    
    @Override
    public void push(T newEntry) {
        Node newNode = new Node(newEntry, head);
        
        head = newNode;
        numEntries++;
    }

    @Override
    public T pop() {
        Node topNode;
        
        if (!isEmpty()) {
            topNode = head;
            head = head.next;
            numEntries--;
            
            return topNode.data;
        }
        
        return null;
    }
    
    @Override
    public T pop(int position) {
        Node currNode, popNode;
        int currPosition = 1;
        
        if (position >= 1 && position <= numEntries) {
            currNode = head;
            
            while (currNode != null) {
                if ((currPosition + 1) == position) {
                    popNode = currNode.next;
                    currNode.next = currNode.next.next;
                    numEntries--;
                    
                    return popNode.data;
                }
                
                currNode = currNode.next;
                currPosition++;
            }
        }
        
        return null;
    }

    @Override
    public T peek() {
        Node topNode;
        
        if (!isEmpty()) {
            topNode = head;
            
            return topNode.data;
        }
        
        return null;
    }
    
    @Override
    public T peek(int position) {
        Node currNode;
        int currPosition = 1;
        
        if (position >= 1 && position <= numEntries) {
            currNode = head;
            
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
    public int size() {
        return numEntries;
    }

    @Override
    public boolean isEmpty() {
        return numEntries == 0;
    }

    @Override
    public void clear() {
        head = null;
        numEntries = 0;
    }
    
    @Override
    public boolean contains(T anEntry) {
        return search(anEntry) != -1;
    }
    
    @Override
    public int search(T anEntry) {
        Node currNode = head;
        int position = 1;
        
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
    public Iterator<T> getIterator() {
        return new LinkedStackIterator();
    }
    
    private class LinkedStackIterator<T> implements Iterator<T> {
        Node currNode;
        
        public LinkedStackIterator() {
            currNode = head;
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
    }
    
    private class Node {
        private T data;
        private Node next;
        
        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
