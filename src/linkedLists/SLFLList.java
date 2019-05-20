package linkedLists;
/**
 * Singly linked list with references to its first and its
 * last node. 
 * 
 * @author pirvos
 *
 */

import java.util.Arrays;

import linkedLists.LinkedList;

public class SLFLList<E> 
implements LinkedList<E>
{

	private SNode<E> first, last; 
	int length = 0; 

	public SLFLList() { 
		first = last = null; 
	}


	public void addFirstNode(Node<E> nuevo) {
		SNode<E> sNuevo = (SNode<E>) nuevo;
		sNuevo.setNext(first);
		first = sNuevo;
		length++;	
	}

	public void addNodeAfter(Node<E> target, Node<E> nuevo) {
		SNode<E> sTarget = (SNode<E>) target;
		SNode<E> sNuevo = (SNode<E>) nuevo;
		sNuevo.setNext(sTarget.getNext());
		sTarget.setNext(sNuevo);		
		length++;
	}

	public void addNodeBefore(Node<E> target, Node<E> nuevo) {
		SNode<E> sIterate = first;
		SNode<E> sTarget = (SNode<E>) target;
		SNode<E> sNuevo = (SNode<E>) nuevo;
		sNuevo.setNext(sTarget);
		while(sIterate.getNext() != sTarget) {sIterate = sIterate.getNext();}
		sIterate.setNext(sTarget);
		length++;
	}

	public Node<E> getFirstNode() throws NodeOutOfBoundsException {
		if(length() == 0) {throw new NodeOutOfBoundsException("getFirstNode: list is empty");}
		else return first;
	}

	public Node<E> getLastNode() throws NodeOutOfBoundsException {
		if(length() == 0 ) {throw new NodeOutOfBoundsException("getLastNode: list is empty");}
		else return last;
	}

	public Node<E> getNodeAfter(Node<E> target) throws NodeOutOfBoundsException {
		SNode<E> sTarget = (SNode<E>) target;
		if(sTarget == last) {throw new NodeOutOfBoundsException("getNodeAfter: target is the last node");}
		else return sTarget.getNext();
	}

	public Node<E> getNodeBefore(Node<E> target) throws NodeOutOfBoundsException {
		SNode<E> sIterate = first;
		SNode<E> sTarget = (SNode<E>) target;
		if(length == 0 || sTarget == first) {throw new NodeOutOfBoundsException("getNodeBefore: target is the first node");}
		while(sIterate.getNext() != sTarget) {sIterate = sIterate.getNext();}
		return sIterate;
	}

	public int length() {
		return this.length;
	}

	public void removeNode(Node<E> target) {
		SNode<E> sTarget = (SNode<E>) target;
		if(sTarget == first) {first = sTarget.getNext();}
		else if(sTarget == last) {
			SNode<E> sPrevious = (SNode<E>) getNodeBefore(target);
			last = (SNode<E>) this.getNodeBefore(target);
			sPrevious.setNext(null);
		}
		else {
			SNode<E> sPrevious = (SNode<E>) getNodeBefore(target);
			sPrevious.setNext(sTarget.getNext());
		}
		
		sTarget.setElement(null);
		sTarget.setNext(null);
		length--;	
	}

	public Node<E> createNewNode() {
		return new SNode<E>();
	}
	
	public Object[] toArray() {
		Object[] result = new Object[length];
		for(int i = 0; i < length; i++) {
			result[i] = first;
			first = first.getNext();
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
		SNode<E> clonedNode = first;
		clonedList.addFirstNode(clonedNode);
		for(int i = 1; i < length; i++) {
			addNodeAfter(clonedNode, clonedNode.getNext());
			clonedNode = clonedNode.getNext();
		}
		return clonedList;
	}
	///////////////////////////////////////////////////
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
