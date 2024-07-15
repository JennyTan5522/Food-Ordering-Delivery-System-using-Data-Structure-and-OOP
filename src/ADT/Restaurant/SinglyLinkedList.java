package ADT.Restaurant;

import java.util.Iterator;

/**
 *
 * @author xuanyi
 */
public class SinglyLinkedList<T> implements ListInterface<T> {

  private Node firstNode; // reference to first node
  private Node lastNode;
  private int numberOfEntries;  	// number of entries in list
    
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
  
    public SinglyLinkedList() {
      clear();
    }   
  
    @Override
    public boolean add(T newEntry) {
        
        //Create a new node    
        Node newNode = new Node(newEntry);	

        if (isEmpty()) {
          firstNode = newNode;
          lastNode = newNode;
          
        } else {  
            lastNode.next = newNode;
            lastNode = newNode;
        }
        
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {

    Node prevNode;
    boolean isSuccessful = true;

    if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
      Node newNode = new Node(newEntry);

      if (newPosition == 1) { // case 1: add to beginning of list
          if(isEmpty()){
            firstNode = newNode;
            lastNode = newNode;              
          }else{
           newNode.next = firstNode;
           firstNode = newNode;             
          }

      }else{								
        
        prevNode = getNodeBefore(newPosition);
        newNode.next = prevNode.next;	
        prevNode.next = newNode;	
        
      }

      numberOfEntries++;
    } else {
      isSuccessful = false;
    }

    return isSuccessful;        

    }

    //get the node loacted before the givenPosition
    private Node getNodeBefore(int givenPosition){
        
        Node nodeBefore = firstNode;
        
        for (int i = 1; i < givenPosition - 1; ++i) {
          nodeBefore = nodeBefore.next;		// advance nodeBefore to its next node
        }      
        
        return nodeBefore;
        
    }
    
    @Override
    public T remove(int givenPosition) {

    T result = null;  
    Node prevNode;
    
    
    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
        
         
      if (givenPosition == 1) {      //remove first entry
          
        if(numberOfEntries == 1){ //if there's only one entry in the list
            result = firstNode.data;
            firstNode=null;
            lastNode=null;
            numberOfEntries--;
            
            return result;
        }  
          
        result = firstNode.data;     // save entry to be removed
        firstNode = firstNode.next;
        
      } else if(givenPosition==numberOfEntries){   //remove last entry
          result = lastNode.data;
          prevNode = getNodeBefore(givenPosition);
          lastNode = prevNode;
          prevNode.next=null;
          
      }else {                         
          
          prevNode = getNodeBefore(givenPosition);        
          result = prevNode.next.data;  // save entry to be removed
          prevNode.next = prevNode.next.next;	// make node before point to node after the one to be deleted (to disconnect node from chain)
      } 																

      numberOfEntries--;
    }

    return result; // return removed entry, or null if operation fails
    
        
    }

    @Override
    public void clear() {
    
    firstNode = null;
    lastNode = null;
    numberOfEntries = 0;
    
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {

    boolean isSuccessful = true;        
        
    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      Node prevNode , currentNode;
      
      if(givenPosition == 1){
          currentNode = firstNode;
      }else{
        prevNode = getNodeBefore(givenPosition); 
        currentNode = prevNode.next;          
      }


      
      currentNode.data = newEntry;	
    } else {
      isSuccessful = false;
    }

    return isSuccessful;        
        
        
        
    }

    @Override
    public T getEntry(int givenPosition) {

        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            Node prevNode , currentNode;

            if(givenPosition == 1){
                result = firstNode.data;
            }else{
                prevNode = getNodeBefore(givenPosition); 
                currentNode = prevNode.next;

                result = currentNode.data;	// currentNode is pointing to the node at givenPosition                
            }
            

        }

        return result;        
        
    }

    @Override    
    public int getLastEntryPosition() {
        return numberOfEntries+1;
}
    
    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;

    }     
    
//  @Override
//    public T search(T data) {
//        if(isEmpty()) {
//            return null;
//        }
//        
//        Iterator<T> iterator = getIterator();
//        T listData;
//
//        while (iterator.hasNext()) {
//            listData = iterator.next();
//
//            if(listData.equals(data)){
//                return listData;
//            }
//
//        }         
//        
//        return null;
//    }
    
  @Override
    public int search(T data) {
        
        int position=1;
        
        Iterator<T> iterator = getIterator();
        T listData;        
        
        if(isEmpty()) {
            return 0;
        }

        while (iterator.hasNext()) {
            listData = iterator.next();

            if(listData.equals(data)){
                return position;
            }else{
                position++;                
            }
            

        }         
        
        return 0;
    }    
    
    @Override    
    public boolean contains(T data) {
        return search(data) != 0;
    }       

//    @Override    
//    public boolean contains(T data) {
//        return search(data) != null;
//    }    

    @Override
    public boolean isEmpty() {

        return (firstNode == null && lastNode == null);
    }

    @Override
    public Iterator<T> getIterator() {
        return new SinglyLinkedListIterator();
    }    
    
//    @Override
//    public String toString() {
//      String outputStr = "";
//      int count=0;
//      
//      if(!isEmpty()){
//
//          Iterator<T> iterator = getIterator();
//
//          while (iterator.hasNext()) {
//              count++;
//              outputStr = outputStr + count + "." + iterator.next() + "\n";
//          }           
//
//      }
//
//      return outputStr;
//    }
  
    
    private class SinglyLinkedListIterator implements Iterator<T>{
    
        private Node currentNode;
        
        public SinglyLinkedListIterator() {
          currentNode = firstNode;
        }           
        
        public Node getCurrentNode(){
            return currentNode;
        }
        
        @Override
        //check does the current node have pointer to the next node
        public boolean hasNext() {

            if(currentNode ==  null){
                return false;
            }
            return true;
        }

        @Override
        //get the next node of the current node
        public T next() {


          if (hasNext()) {
            T returnEntry = currentNode.data;
            currentNode = currentNode.next;

            return returnEntry;
          } 

          return null;

        }
  
}
  
    
}
