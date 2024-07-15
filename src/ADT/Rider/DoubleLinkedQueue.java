package ADT.Rider;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DoubleLinkedQueue<T> implements QueueInterface<T> {
    private Node head;//References the first node in the chain, when head is null, means the queue is empty
    private Node tail;//Tail of the node
    private int length;//DoubleLinkedQueue size

    private class Node<T> {
        private T object;//Generic object
        private Node next;//Node reference of the next pointer
        private Node previous;//Node reference of the previous pointer

        //Node constructor
        public Node(T object) {
            this.object = object;
            this.next = null;
            this.previous = null;
        }
    }

    //Constructor
    public DoubleLinkedQueue() {
        head = null;
        tail = null;
        length = 0;
    }

    //Constructor with new node
    public DoubleLinkedQueue(T newEntry) {
        head = new Node(newEntry);
        tail = head;
        length = 1;
    }

    //Add a new node to queue
    //creating a Node with no neighbors and the element referenced as its value.
    //Pointing the head of our list to the newly created node
    //Pointing the tail of our list to the newly created node
    @Override
    public boolean enqueue(T newEntry) {
        //Create a new node
        Node newNode = new Node(newEntry);
        //Check whether the newEntry is a new linked queue or not
        if (length == 0) {
            head = newNode;
            tail = head;
        }
        //If got linkedQueue exists
        else {
            Node currentTail = tail;//Move the current tail to the 1 variable to store
            tail.next = newNode;//Set tail to the new node
            tail = newNode;//Update tail pointer to a new node
            tail.previous = currentTail;
        }
        length++;//Increase length
        return true;
    }

    //Retrieves and removes the head of this queue, or returns null if this queue is empty.
    @Override
    public boolean dequeue() {
        //Save current head pointer
        Node currentHead = head;
        //If queue is empty
        if (head == null)
            return false;
            //If only one item inside queue
        else if (head == tail) {
            tail = null;
        }
        //If length >1, queue not empty
        else {
            head.next.previous = null;
            head = currentHead.next;
            length--;//Reduce length size
        }
        return true;
    }

    //This method is searching based on the position and deque it
    //Used when one order has been successfully updated the order status to 'Delivered' then dequeue from DeliveryOrderQueue
    @Override
    public boolean dequeue(int position) {
        if (indexValidation(position)) {
            if (head == null) {
                return false;
            } else {
                int counter = 1;//Count from 1
                Node currentNode = head;
                while (counter != position && currentNode != null) {
                    currentNode = currentNode.next;
                    counter++;
                }

                if (position == 1) {
                    currentNode.previous = null;
                    head = currentNode.next;
                }
                //If not head
                else {
                    //1->3->2->4 , we want remove index 2
                    //Get the prev remove node =3
                    Node preNode = currentNode.previous;
                    //Update pointer
                    preNode.next = currentNode.next;
                    currentNode.next.previous = preNode;
                    currentNode = null;
                }
                length--;
                return true;
            }
        }
        return false;
    }

    //Retrieves the head of this queue, or returns null if this queue is empty (Not remove)
    @Override
    public T peek() {
        //If queue not empty return the head of the queue
        return (head != null) ? (T) head.object : null;
    }


    //Return true if length ==0
    @Override
    public boolean isEmpty() {
        return length == 0 ? true : false;
    }

    //Clear all the items in DoubleLinkedQueue
    @Override
    public void clear() {
        head = null;
        tail = null;
        length = 0;
    }

    //Get the size of the double linked queue
    @Override
    public int size() {
        return length;
    }

    //Get the object based on the position given
    @Override
    public T get(int position) {
        if (indexValidation(position)) {
            int counter = 1;//Count from 1
            Node currentNode = head;
            while (counter != position && currentNode != null) {
                currentNode = currentNode.next;
                counter++;
            }
            return (T) currentNode.object;
        }
        return null;
    }

    //Internal used purpose : Check whether the current position is valid or not
    private boolean indexValidation(int position) {
        if (position <= 0 || position > length) {
            throw new ArrayIndexOutOfBoundsException(position);
        }
        return true;
    }

    //Contains method checks whether the linked queue contain the element
    @Override
    public boolean contains(T element) {
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.object == element)
                return true;
            currentNode = currentNode.next;
        }
        return false;
    }

    //Get the searchEntry index
    @Override
    public int indexOf(T searchEntry) {
        //Set a current pointer then loop
        Node currentNode = head;
        int counter = 1;
        //While loop to search
        while (currentNode != null && currentNode.next != searchEntry) {
            if (currentNode.object == searchEntry)
                return counter;
            //Updater pointer
            currentNode = currentNode.next;
            counter++;

        }
        return -1;//If index not found return -1
    }

    //Set the current position of index to newEntry ,similar as replace function
    @Override
    public boolean set(int position, T newEntry) {
        //Check if current position is head or tail
        //Validate index
        //Current position is head
        Node replaceNode = new Node(newEntry);
        if (position == 1) {
            replaceNode.next = head.next;
            head = replaceNode;
        }
        //Current position is tail
        else if (position == length) {
            replaceNode.previous = tail.previous;
            tail.previous.next = replaceNode;
            tail = replaceNode;
        } else {
            if (indexValidation(position)) {
                int counter = 1;//Count from 1
                Node currentNode = head;
                while (counter != position && currentNode != null) {
                    currentNode = currentNode.next;
                    counter++;
                }
                replaceNode.previous = currentNode.previous;
                replaceNode.next = currentNode.next;
                currentNode.previous.next = replaceNode;
                currentNode.next.previous = replaceNode;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        Node currentNode = head;
        String toString = "";
        while (currentNode != null) {
            toString += currentNode.object + " -> ";
            //Update current pointer
            currentNode = currentNode.next;
        }
        return toString;
    }

    @Override
    public ListIterator<T> getListIterator() {
        return new LinkedQueueListIterator();
    }


    @Override
    public ListIterator<T> getListIterator(int position) {
        return new LinkedQueueListIterator(position);
    }

    private class LinkedQueueListIterator implements ListIterator<T> {
        private Node currentNode;

        public LinkedQueueListIterator() {
            currentNode = head;
        }

        //Get an instance of an iterator for the list starting at the specified position.
        public LinkedQueueListIterator(int position) {
            int counter = 1;
            currentNode = head;//Search start from head until the current position
            //Search the object based on the position given
            while (counter != position && currentNode != null) {
                currentNode = currentNode.next;
                counter++;
            }
        }

        //Check whether the Queue still has the next element, return true if have
        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        //Check whether the Queue still has the previous element, return true if have
        @Override
        public boolean hasPrevious() {
            return currentNode != null;
        }

        //Get next element's index
        @Override
        public int nextIndex() {
            return indexOf((T)currentNode.object);
        }

        //Get previous element's index
        @Override
        public int previousIndex() {
            return indexOf((T)currentNode.object)-1;
        }

        //Get the object of next element
        @Override
        public T next() {
            if (hasNext()) {
                T returnData = (T) currentNode.object;
                currentNode = currentNode.next;
                return returnData;
            } else {
                throw new NoSuchElementException("No next element available.");
            }
        }

        //Get the object of previous element
        @Override
        public T previous() {
            if (hasPrevious()) {
                T returnData = (T) currentNode.object;
                currentNode = currentNode.previous;
                return returnData;
            } else {
                throw new NoSuchElementException("No next element available.");
            }
        }

        //--------------- Extra methods, already implemented above ---------------
        @Override
        public void set(T element) {
        }

        @Override
        public void remove() {
        }

        @Override
        public void add(T element) {
        }

    }




}
