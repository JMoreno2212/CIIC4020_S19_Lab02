package linkedLists;

import java.util.Arrays;

public class DLDHDTList<E> implements LinkedList<E> {
	private DNode<E> header, trailer; 
	private int length; 
	
	public DLDHDTList() { 
		header = (DNode<E>) createNewNode();
		trailer = (DNode<E>) createNewNode();
		header.setNext(trailer);
		trailer.setPrev(header);
		length = 0;
	}
	
	public void addFirstNode(Node<E> nuevo) {
		addNodeAfter(header, nuevo); 
	}

	public void addNodeAfter(Node<E> target, Node<E> nuevo) {
		DNode<E> dnuevo = (DNode<E>) nuevo; 
		DNode<E> nBefore = (DNode<E>) target; 
		DNode<E> nAfter = nBefore.getNext(); 
		nBefore.setNext(dnuevo); 
		nAfter.setPrev(dnuevo); 
		dnuevo.setPrev(nBefore); 
		dnuevo.setNext(nAfter); 
		length++; 
	}

	public void addNodeBefore(Node<E> target, Node<E> nuevo) {
		DNode<E> dTarget = (DNode<E>) target;
		DNode<E> dNuevo = (DNode<E>) nuevo;
		DNode<E> dPrevious = dTarget.getPrev();
		dNuevo.setNext(dTarget);
		dTarget.setPrev(dNuevo);
		addNodeAfter(dPrevious, dNuevo);
		length++;
	}

	public Node<E> createNewNode() {
		return new DNode<E>();
	}

	public Node<E> getFirstNode() throws NodeOutOfBoundsException {
		if (length == 0) 
			throw new NodeOutOfBoundsException("List is empty."); 
		return header.getNext();
	}

	public Node<E> getLastNode() throws NodeOutOfBoundsException {
		if (length == 0) 
			throw new NodeOutOfBoundsException("List is empty."); 
		return trailer.getPrev();
	}

	public Node<E> getNodeAfter(Node<E> target) throws NodeOutOfBoundsException {
		DNode<E> dTarget = (DNode<E>) target;
		if(dTarget.getNext() == trailer) 
			throw new NodeOutOfBoundsException("getNodeAfter: target is the last node");
		return dTarget.getNext(); 
	}

	public Node<E> getNodeBefore(Node<E> target) throws NodeOutOfBoundsException {
		DNode<E> dTarget = (DNode<E>) target;
		if(dTarget.getPrev() == header) 
			throw new NodeOutOfBoundsException("getNodePrev: target is the first node");
		return dTarget.getPrev(); 
	}

	public int length() {
		return length;
	}

	public void removeNode(Node<E> target) {
		DNode<E> dTarget = (DNode<E>) target;
		dTarget.getPrev().setNext(dTarget.getNext());
		dTarget.getNext().setPrev(dTarget.getPrev());
		dTarget.cleanLinks();
		dTarget.setElement(null);
		length--;
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
		while (header != null) { 
			DNode<E> nnode = header.getNext(); 
			header.setElement(null); 
			header.cleanLinks(); 
			header = nnode; 
		}
	}
	
	/**
	 * The execution of this method removes all the data nodes from
	 * the current instance of the list, leaving it as a valid empty
	 * doubly linked list with dummy header and dummy trailer nodes. 
	 */
	public void makeEmpty() { 
		// TODO
	}
		
	protected void finalize() throws Throwable {
	    try {
			this.destroy(); 
	    } finally {
	        super.finalize();
	    }
	}
	
	public Object[] toArray() {
		Object[] result = new Object[length];
		for(int i = 0; i < length; i++) {
			result[i] = header;
			header = header.getNext();
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
		DNode<E> clonedNode = header;
		clonedList.addFirstNode(clonedNode);
		for(int i = 1; i < length; i++) {
			addNodeAfter(clonedNode, clonedNode.getNext());
			clonedNode = clonedNode.getNext();
		}
		return clonedList;
	}
	/**
	 * Class to represent a node of the type used in doubly linked lists. 
	 * @author pedroirivera-vega
	 *
	 * @param <E>
	 */
	private static class DNode<E> implements Node<E> {
		private E element; 
		private DNode<E> prev, next; 

		// Constructors
		public DNode() {}
		
		@SuppressWarnings("unused")
		public DNode(E e) { 
			element = e; 
		}
		
		@SuppressWarnings("unused")
		public DNode(E e, DNode<E> p, DNode<E> n) { 
			prev = p; 
			next = n; 
		}
		
		// Methods
		public DNode<E> getPrev() {
			return prev;
		}
		public void setPrev(DNode<E> prev) {
			this.prev = prev;
		}
		public DNode<E> getNext() {
			return next;
		}
		public void setNext(DNode<E> next) {
			this.next = next;
		}
		public E getElement() {
			return element; 
		}

		public void setElement(E data) {
			element = data; 
		} 
		
		/**
		 * Just set references prev and next to null. Disconnect the node
		 * from the linked list.... 
		 */
		public void cleanLinks() { 
			prev = next = null; 
		}
		
	}

}
