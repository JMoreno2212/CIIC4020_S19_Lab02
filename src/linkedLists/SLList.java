/**
 * 
 */
package linkedLists;

import java.util.Arrays;

/**
 * @author pirvos
 *
 */
public class SLList<E> implements LinkedList<E> {
	private SNode<E> head; 
	private int length; 

	public SLList() { 
		head = null; 
		length = 0; 
	}
	
	public void addFirstNode(Node<E> nuevo) {
		// Pre: nuevo is not a node in the list
		((SNode<E>) nuevo).setNext(head); 
		head = (SNode<E>) nuevo; 
		length++; 
	}

	public void addNodeAfter(Node<E> target, Node<E> nuevo) {
		// Pre: target is a node in the list
		// Pre: nuevo is not a node in the list
		((SNode<E>) nuevo).setNext(((SNode<E>) target).getNext()); 
		((SNode<E>) target).setNext((SNode<E>) nuevo); 
		length++; 
	}

	public void addNodeBefore(Node<E> target, Node<E> nuevo) {
		// Pre: target is a node in the list
		// Pre: nuevo is not a node in the list

		if (target == head)
			this.addFirstNode(nuevo); 
		else { 
			Node<E> prevNode = findNodePrevTo(target);  
			this.addNodeAfter(prevNode, nuevo); 
		}
	}

	private Node<E> findNodePrevTo(Node<E> target) {
		// Pre: target is a node in the list
		if (target == head) 
			return null; 
		else { 
			SNode<E> prev = head; 
			while (prev != null && prev.getNext() != target) 
				prev = prev.getNext();  
			return prev; 
		}
	}

	public Node<E> getLastNode() 
	throws NodeOutOfBoundsException 
	{
		if (head == null)
			throw new NodeOutOfBoundsException("getLastNode(): Empty list."); 
		else { 
			SNode<E> curr = head; 
			while (((SNode<E>) curr).getNext() != null)
				curr = curr.getNext(); 
			return curr; 
		}
	}

	public Node<E> getNodeAfter(Node<E> target) 
	throws NodeOutOfBoundsException 
	{
		// Pre: target is a node in the list
		SNode<E> aNode = ((SNode<E>) target).getNext(); 
		if (aNode == null)  
			throw new NodeOutOfBoundsException("getNextNode(...) : target is the last node."); 
		else 
			return aNode;
	}


	public Node<E> getNodeBefore(Node<E> target) 
	throws NodeOutOfBoundsException 
	{
		// Pre: target is a node in the list
		if (target == head)  
			throw new NodeOutOfBoundsException("getPrevNode(...) : target is the first node."); 
		else 
			return findNodePrevTo(target);
	}

	public int length() {
		return this.length;
	}

	public void removeNode(Node<E> target) {
		// Pre: target is a node in the list
		
		if (target == head) {
			// the list is not empty....
			SNode<E> ntr = head; 
			head = head.getNext(); 
			ntr.setNext(null);      // notice that the node keeps its data..
		}
		else { 
			SNode<E> prevNode = (SNode<E>) this.getNodeBefore(target); 
			prevNode.setNext(((SNode<E>) target).getNext()); 
		}
		length--; 
	}

	
	public Node<E> getFirstNode() 
	throws NodeOutOfBoundsException 
	{
		if (head == null)
			throw new NodeOutOfBoundsException("getFirstNode() : linked list is empty..."); 
		
		// the linked list is not empty....
		return head;
	}
	
	/**
	 * Prepares every node so that the garbage collector can free 
	 * its memory space, at least from the point of view of the
	 * list. This method is supposed to be used whenever the 
	 * list object is not going to be used anymore. Removes all
	 * physical nodes (data nodes and control nodes, if any)
	 * from the linked list
	 */
	private void destroy() {
		while (head != null) { 
			SNode<E> nnode = head.getNext(); 
			head.setElement(null); 
			head.setNext(null); 
			head = nnode; 
		}
	}
	
	/**
	 * The execution of this method removes all the data nodes
	 * from the current instance of the list. 
	 */
	public void makeEmpty() { 
		// TODO
	}
		
	protected void finalize() throws Throwable {
		//System.out.println("GC is WORKING!");
		//System.out.println("Number of nodes to remove is: "+ this.length); 
		try {
			this.destroy(); 
		} finally {
			super.finalize();
		}
	}
	
	public Node<E> createNewNode() {
		return new SNode<E>();
	}

	public Object[] toArray() {
		Object[] result = new Object[length];
		for(int i = 0; i < length; i++) {
			result[i] = head;
			head = head.getNext();
		}
		return result;		
	}
	
	@SuppressWarnings("hiding")
	public <E> E[] toArray(E[] array) {
		array = Arrays.copyOf(array, length);
		return array;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object clone() {
		SLFLList clonedList = new SLFLList();
		SNode<E> clonedNode = head;
		clonedList.addFirstNode(clonedNode);
		for(int i = 1; i < length; i++) {
			addNodeAfter(clonedNode, clonedNode.getNext());
			clonedNode = clonedNode.getNext();
		}
		return clonedList;
	}
	
	// private and static inner class that defines the 
	// type of node that this list implementation uses
	private static class SNode<T> implements Node<T> {
		private T element; 
		private SNode<T> next; 
		public SNode() { 
			element = null; 
			next = null; 
		}
		@SuppressWarnings("unused")
		public SNode(T data, SNode<T> next) { 
			this.element = data; 
			this.next = next; 
		}
		@SuppressWarnings("unused")
		public SNode(T data)  { 
			this.element = data; 
			next = null; 
		}
		public T getElement() {
			return element;
		}
		public void setElement(T data) {
			this.element = data;
		}
		public SNode<T> getNext() {
			return next;
		}
		public void setNext(SNode<T> next) {
			this.next = next;
		}
	}

}
