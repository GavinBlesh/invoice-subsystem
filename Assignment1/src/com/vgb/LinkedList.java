package com.vgb;

import java.util.Comparator;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 *  
 * Class to represent a Linked List data structure
 */
public class LinkedList<T> {

	private Node<T> head;
	private int size;
	private Comparator<T> comparator;

	/*
	 * Constructor for the Linked List that takes in a comparator
	 */
	public LinkedList(Comparator<T> comparator) {
		this.head = null;
		this.size = 0;
		this.comparator = comparator;
	}

	/*
	 * Inserts a node in the Linked List
	 */
	public void insert(T x) {

		Node<T> newNode = new Node<>(x);

		if (head == null || comparator.compare(x, this.head.getData()) < 0) {
			newNode.setNext(this.head);
			this.head = newNode;
		} else {
			Node<T> previous = this.head;
			Node<T> current = previous.getNext();

			/*
			 * Iterates through the LinkedList to find where to insert the new node
			 */
			while (current != null && comparator.compare(x, current.getData()) > 0) {
				previous = current;
				current = current.getNext();
			}

			previous.setNext(newNode);
			newNode.setNext(current);
		}

		size++;
	}

	/*
	 * Finds the size of the linked list
	 */
	public int size() {
		return this.size;
	}

	/*
	 * Finds the node at a given index Private function to prevent leaky
	 * abstractions
	 */
	private Node<T> getNodeAtIndex(int index) {

		Node<T> currentNode = this.head;
		for (int i = 0; i < index; i++) {
			currentNode = currentNode.getNext();
		}
		return currentNode;
	}

	/*
	 * Gets the data inside a node at a certain index
	 */
	public T get(int index) {
		Node<T> node = this.getNodeAtIndex(index);
		return node.getData();
	}

	/*
	 * Deletes a node from the linked list given an index. Connects the nodes that
	 * were connected to the deleted node as well.
	 */
	public T delete(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException(
					"The index " + index + "Is either less than 0 or >= the size. Can't delete it");
		}

		T result;
		if (index == 0) {
			result = this.head.getData();
			this.head = this.head.getNext();
		} else {
			Node<T> previous = this.getNodeAtIndex(index - 1);
			Node<T> current = previous.getNext();
			result = current.getData();
			previous.setNext(current.getNext());
		}
		size--;
		return result;
	}
}
