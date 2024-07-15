package ADT.Restaurant;

import java.util.Iterator;

/**
 *
 * @author xuanyi
 */
public class CircularLinkedQueue<T> implements QueueInterface<T> {

    private Node lastNode;  // references node at back of queue
    
    // Structure of a Node
    private class Node {

      private T data; 
      private Node next; 

      private Node(T data) {
        this.data = data;
        this.next = null;
      } 

      private Node(T data, Node next) {
        this.data = data;
        this.next = next;
      } 
    } 
    

  public CircularLinkedQueue() {
    lastNode = null;
  }     
          
      
  
    @Override
    public boolean enqueue(T newEntry) {
        Node newNode = new Node(newEntry, null);
        boolean added = false;

        if (isEmpty())
        {
          lastNode = newNode;
          lastNode.next = lastNode;
          added = true;
        }
        else
        {   
          //links the newNode to the lastNode node's pointer and then 're'points the 
          //lastNode node to the newNode.
            newNode.next = lastNode.next;
            lastNode.next = newNode;
            added = true;
          
        }
          //'repositions' the reat node at the end of the queue.
          lastNode = newNode;
          return added;
    }

    @Override
    public T dequeue() {
        //removes front element from this queue and returns it.
        T front = null;
        
      if (!isEmpty()){

        front = getFront();
        lastNode.next = lastNode.next.next;

        if (lastNode.next == lastNode){
           lastNode = null;         
        }  
      
    }
      return front;
   }

    @Override
    public boolean clear() {
        boolean cleared = false;
       
        if (!isEmpty()) {
        lastNode.next=null;
        lastNode=null;
        cleared = true;
        }

        return cleared;
    }

    @Override
    public boolean isEmpty() {        
        return lastNode == null;
    }

        @Override
    public T getFront() {
        T front = null;

        if (!isEmpty()) {
          front = lastNode.next.data;
        }

        return front;
    }

    @Override
    public int getNumberOfEntries() {

        int count = 0;
        
        if(isEmpty()) {
            return 0;
        }
        
        Node entry = lastNode.next;
        
        do{
            count++;
            entry = entry.next;            
            
        }while(entry != lastNode.next);
             
        return count;

    }
    
    public T search(T data) {
        if(isEmpty()) {
            return null;
        }
//Method 1: (when larger integer is being search cnt work)
//        Node nodeSearched = lastNode.next;
//        if(nodeSearched.data == data) {
//            return nodeSearched.data;
//        }
//        nodeSearched = nodeSearched.next;
//        while(nodeSearched != lastNode.next) {
//            if(nodeSearched.data == data) {
//                return nodeSearched.data;
//            }
//            nodeSearched = nodeSearched.next;
//        }
//        return null;

            Iterator<T> iterator = getIterator();
            T queueData;

            while (iterator.hasNext()) {
                queueData = iterator.next();
                
                if(queueData.equals(data)){
                    return queueData;
                }
                
            } 

            return null;

    }

    public boolean contains(T data) {
        return search(data) != null;
    }

  
    
    

    @Override
    public Iterator<T> getIterator() {
        return new CircularLinkedQueueIterator();
    }
    
    
    public String toString(){
        String outputStr = ""; 
        
        if(!isEmpty()){
            Iterator<T> iterator = getIterator();

            while (iterator.hasNext()) {
                outputStr = outputStr + iterator.next();
            }                  
            
        }   
          
        return outputStr;
    }
    
  private class CircularLinkedQueueIterator implements Iterator<T> {

    private Node currentNode;
    boolean visitingAgain = false;

    public CircularLinkedQueueIterator() {
      currentNode = lastNode;
    }    

    @Override
    //check does the current node have pointer to the next node
    public boolean hasNext() {

        if (currentNode.next == lastNode.next && visitingAgain){
            return false;
        }
        
        
        visitingAgain = true; // once you start iterating change this flag.
        return true;

    }

    @Override
    //get the next node of the current node
    public T next() {

        //move currentNode to the following node
        currentNode = currentNode.next;
        
        
      if (hasNext()) {
        T returnEntry = currentNode.data;
        return returnEntry;
      } 
      
      return currentNode.data;
      
    }
    
    
    
  }    
    
}
