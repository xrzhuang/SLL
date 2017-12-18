/**
 * SLL.java
 * 
 * Implementation of singly linked list.
 * WARNING: This implementation is guaranteed to work only if always given
 * immutable objects (or at least ones that do not change).
 * 
 * @author THC
 * @author Scot Drysdale converted to Java
 * @author Scot Drysdale, THC have made a number of modifications.
 * @author Scot Drysdale most recently modified on 1/12/2011
 * @author Prasad Jayanti changed the interface to CS10ListADT
 * @author Max Zhuang changed HeaderSLL to use a singly linked list and a currentPred rather than current
 */
public class HeaderTailSLL<T> implements CS10LinkedList<T> {
  // Instance variables.
  private Element<T> currentPred;    // position one before current position in the list
  private Element<T> head;       // head of list
  private Element<T> tail;  // tail of list
  
  /**
   * A private class inner representing the elements in the list.
   */
  private static class Element<T> {
    // Because this is a private inner class, these can't be seen from outside SLL.
    private T data;         // reference to data stored in this element
    private Element<T> next;   // reference to next item in list
    
    /**
     * Constructor for a linked list element, given an object.
     * @param obj the data stored in the element.
     */
    public Element(T obj) {
      next = null;          // no element after this one, yet
      data = obj;           // OK to copy reference, since obj references an immutable object
    }

    /**
     * @return the String representation of a linked list element.
     */
    public String toString() {
      return data.toString();  // return data within linked list element as a string
    }
  }

  /**
   * Constructor to create an empty singly linked list.
   */
  public HeaderTailSLL() {
  	head = new Element<T>(null);  // nothing is inside head, dummy header
  	currentPred = head;  // set currentPred to head
  	tail = head;
    clear();
  }

  /**
   * @see CS10ListADT#clear()
   */
  public void clear() {
  	// dummy header, next pointing to null
    currentPred = head;
    head.next = null;
    tail = head;
  }

  /**
   * @see CS10ListADT#add()
   */
  public void add(T obj) {
    Element<T> x = new Element<T>(obj);   // allocate a new element

    // There are two distinct cases, depending on whether the new element
    // is to be the new head.
  
   
    if (hasCurrent()){  // if there is a current, add to list at the end
    	x.next = currentPred.next.next;
    	currentPred.next.next = x;
    	currentPred = currentPred.next;
    	for (tail = head; tail.next != null; tail = tail.next);
    }
    
    else{ //if there is no current element add to the head
    	x.next = head.next;
    	head.next = x;
    	currentPred = head;
    	for (tail = head; tail.next != null; tail = tail.next);

    }
  }
  /**
   *   * @see CS10ListADT#remove()
   */
  public void remove() { 
    if (!hasCurrent()) {  // check whether current element exists
      System.err.println("No current item");
      return;
    }
    else {
    	currentPred.next = currentPred.next.next;  // skips over current element aka removes it!
    	for (tail = head; tail.next != null; tail = tail.next);
    } 
  }


  /**
   * @return the String representation of this list.
   */
  public String toString() {  // to string method
    String result = "";
    
    for (Element<T> x = head.next ; x != null; x = x.next) // runs through the list
      result += x.toString() + "\n"; 
    
    return result;
  }

  /**
   * @see CS10ListADT#contains()
   */
  public boolean contains(T obj) {
    Element<T> x;
    Element<T> currentCurrentPred = currentPred;  // create place holder
    currentPred = head;  // now sets currentPred to head, in case we have to use the placeholder
  
    for (x = head.next; x != null && !x.data.equals(obj); x = x.next){  // runs through list
    	currentPred = currentPred.next;  // moves currentPred up each time
    }
    	;
    	
    if (x == null){  // if however it ran all the way through the list, we need to reset the currentPred 
    	currentPred = currentCurrentPred;  // this is why we used the placeholder pretty nifty
    }
     return x != null;
  }
  /**
   * @see CS10ListADT#isEmpty()
   */
  public boolean isEmpty() {
    return head.next == null;  // that's empty, True when empty
  }
  
  /**
   * @see CS10ListADT#hasCurrent()
   */
  public boolean hasCurrent() {
    return currentPred.next != null;  // True when there is a current
  }
  
  /**
   * @see CS10ListADT#hasNext()
   */
  public boolean hasNext() {
    return hasCurrent() && currentPred.next.next != null;  // true when it has current and the one after current isn't empty
  }
  
  /**
   * @see CS10ListADT#getFirst()
   */
  public T getFirst() { 
    if(isEmpty()) {  // check
      System.err.println("The list is empty");
      return null;
    }
    currentPred = head; // resets so current is first
    return get();
  }
  
  /**
   * @see CS10ListADT#getLast()
   */
  public T getLast() {
    if (isEmpty()) {  // check
      System.err.println("The list is empty");
      tail = head;
      return null;
    }
    else {
    	for (currentPred = head; currentPred.next.next != null; currentPred = currentPred.next);  // runs through list, until it makes currentPred.next aka current the last element
    	return tail.data;
    }
  }

  /**
   * @see CS10ListADT#addFirst()
   */
  public void addFirst(T obj) {

  	Element<T> newFirst = new Element<T>(obj);
  	newFirst.next = head.next;  // add to the front
  	head.next = newFirst;
  	currentPred = head;
    
  }

  /**
   * @see CS10ListADT#addLast()
   */
  public void addLast(T obj) {
    if(isEmpty())  // check
      addFirst(obj);
    else {
      getLast();  // set current to last, add object from there
      add(obj);
    }
  }
  
  /**
   * @see CS10ListADT#get()
   */
  public T get() {
    if (hasCurrent()) {  // check
      return currentPred.next.data;  // that's current
    }
    else {
      System.err.println("No current item");
      return null;
    }

  }
  
  /**
   * @see CS10ListADT#set()  
   */
  public void set(T obj) {
    if (hasCurrent())  // check
    	currentPred.next.data = obj;  // changes current to obj
    else
      System.err.println("No current item");
  }
  
  /**
   * @see CS10ListADT#next()
   */
  public T next() {
    if (hasNext()) {
      currentPred.next = currentPred.next.next;  // moves current to next position
      return currentPred.next.data;  // return
    }
    else {
      System.err.println("No next item");
      return null;
    }
  }
}
