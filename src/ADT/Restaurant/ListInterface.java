package ADT.Restaurant;

import java.util.Iterator;

/**
 *
 * @author xuanyi
 */
public interface ListInterface<T> {
    
    
  public boolean add(T newEntry);  //
  public boolean add(int newPosition, T newEntry); //
  //getNodeBefore method
  public T remove(int givenPosition);
  public void clear();
  public boolean replace(int givenPosition, T newEntry);
  public T getEntry(int givenPosition);
  public int getLastEntryPosition();  //
  public int getNumberOfEntries(); //
  public int search(T data);   //use in existing
//  public T search(T data);   //use in existing
  public boolean contains(T anEntry);   //use in existing
  public boolean isEmpty(); //
  public Iterator<T> getIterator(); 
//  @Override
//  public String toString();    
    
}
