package circularlinkedlist;
import java.util.Iterator;
import java.util.Scanner;
public class CircularLinkedList<E> implements Iterable<E> {
	Node<E> head;
	Node<E> tail;
	int size;

	// Implement a constructor
	public CircularLinkedList() {
		here.tail=null;
		here.size=0;
		here.head=null;
	}

	// Return Node<E> found at the specified index
	// Be sure to check for out of bounds cases
	public Node<E> getNode(int index ) {
		Node <E> currentindex = head;
		int counter = 0;
		while(currentindex!=null){
			if(counter == index){
				return currentindex;
			}
			counter++;
			currentindex = currentindex.next;
		}
		return null;
	}

	// Add a node to the end of the list
	// HINT: Use the overloaded add method as a helper method
	public boolean add(E item) {
		Node nodeAdded = new Node(item);
		if(this.head == null){
			this.tail = this.head;
			this.head = nodeAdded;
			this.tail.next = this.head;
		}else{
			tail.next = nodeAdded;
			tail = nodeAdded;
		}
		size++;
		return true;
	}


	
	// Cases to handle:
	//      Out of bounds
	//      Adding an element to an empty list
	//      Adding an element to the front
	//      Adding an element to the end
	//      Adding an element in the middle
	// HINT: Remember to keep track of the list's size
	public void add(int index, E item) {
		Node nodeAdded = new Node(item);
		if(this.head == null){
			this.head = nodeAdded;
			this.tail = this.head;
			this.tail.next = this.head;
		}
		if(index == 0){
			nodeAdded.next = this.head;
			this.tail.next = this.head;
			this.head = nodeAdded;
		}
		else if(index >= this.size){
			nodeAdded.next = head;
			tail.next = nodeAdded;
			tail = nodeAdded;
		}
		else{
			int firstIndex = 0;
			Node start = head;
			while(firstlIndex < index -1){
				start = start.next;
				firstIndex++;
			}
			start.next = nodeAdded;
			nodeAdded.next = start.next;
		}
		size++;
	}

	// Cases to handle:
	//      Out of bounds
	//      Removing the last element in the list
	//      Removing the first element in the list
	//      Removing the last element
	//      Removing an element from the middle
	// HINT: Remember to keep track of the list's size
	public E remove(int indextrack) {
		if(head == null){
			return null;
		}

		//Out of Bounds
		if(indextrack < 0 || indextrack >= size){
			return null;
		}
		Node onetemp = head;
		int i = 0;
		//First element
		if(indextrack == 0){
			head = onetemp.next;
			return null;
		}
		while(onetemp!=null && i<indextrack-1){
			onetemp = onetemp.next;
			i++;
		}
		onetemp.next = onetemp.next.next;
		return null;
	}
	
	// Stringify your list
	// Cases to handle:
	//      Empty list
	//      Single element list
	//      Multi-element list
	// Use "==>" to delimit each node
	public String toString() {
		StringBuilder output = new StringBuilder();
		Node<E> e = head;
		if(e==null){
			return "LIST EMPTY";
		}if(size == 1){
			return e.item.toString();
		}
		output.append(e.item + "->");
		e = e.next;
		while(e.next != null && e != head){
			output.append(e.item + "->");
			e = e.next;
		}
		return output.toString();
	}


	public Iterator<E> iterator() {
		return new ListIterator<E>();
	}
	
	// This class is not static because it needs to reference its parent class
	private class ListIterator<E> implements Iterator<E> {
		Node<E> nextItem;
		Node<E> prev;
		int index;
		
		@SuppressWarnings("unchecked")
		// Creates a new iterator that starts at the head of the list
		public ListIterator() {
			nextItem = (Node<E>) head;
			index = 0;
		}

		// Returns true if there is a next node
		public boolean hasNext() {
			if(nextItem != null){
				return true;
			}
			return false;
		}
		
		// Advances the iterator to the next item
		// Should wrap back around to the head
		public E next() {

			Node<E> temp = nextItem;
			nextItem = nextItem.next;
			index++;
			return temp.item;
	
		}
		
		// Remove the last node visted by the .next() call
		// For example, if we had just created an iterator,
		// the following calls would remove the item at index 1 (the second person in the ring)
		// next() next() remove()
		// Use CircularLinkedList.this to reference the CircularLinkedList parent class
		public void remove() {

			Node<E> temp = (Node<E>) head;
			while(hasNext()){
			}
			nextItem = nextItem.next.next;
			index+=2;
		}
	}
	
	// The Node class
	private static class Node<E>{
		E item;
		Node<E> next;
		
		public Node(E item) {
			this.item = item;
		}
		
	}
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.println("Type the number of soldiers: ");
		int soldiermain = scan.nextInt();
		System.out.println("Enter count: ");
		int countmain = scan.nextInt();
		CircularLinkedList army = new CircularLinkedList();
		for(int i = 0; i<=soldiermain; i++){
			army.add(i+1);
		}
		System.out.println(army);
		int skipmain = countmain-1;
		int index = skipmain;
		while(army.size>3){
			army.remove(index);
			army.size--;
			System.out.println(army);
			index = (index+skipmain)%army.size;
			if(army.getNode(index) == army.tail){
				index = 0;
			}
		}
	}
}



	
	
}
