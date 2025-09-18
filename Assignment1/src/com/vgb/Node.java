package com.vgb;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Class to represent an individual node in the LinkedList data structure
 */
public class Node<T> {

	private T data;
	private Node<T> next;

	/*
	 * Constructor for the node class
	 */
	public Node(T data) {
		this.data = data;
		this.next = null;
	}

	/*
	 * Gets the data stored inside the node. Used for comparisons and traversing the
	 * Linked List
	 */
	public T getData() {
		return data;
	}

	/*
	 * Gets the next node in the linked list
	 */
	public Node<T> getNext() {
		return next;
	}

	/*
	 * Setter function for next variable
	 */
	public void setNext(Node<T> next) {
		this.next = next;
	}
}
