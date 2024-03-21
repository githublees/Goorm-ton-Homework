package main;

import java.util.*;

// 제너릭으로 구현하고자 <E>를 주었다.
public class MyLinkedList<E> {
	
	private static class Node<E> {
		// 현재 노드가 가지고 있는 E(item) 객체
		E data;
		
		// 노드의 다음과 이전값을 참조하기 위한 객체
		Node<E> next;
		Node<E> prev;
		
		// 노드 생성자
		Node(Node<E> prev, E data, Node<E> next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
	
	// 현재 MyLinkedList의 size
	int size = 0;

	// 첫번째 노드 객체 참조
	Node<E> first;
	
	// 마지막 노드 객체 참조
	Node<E> last;
	
	// 빈 리스트 생성자
	public MyLinkedList() {
	}
	
	public void add(E e) {
		linkLast(e);
	}
	
	// 마지막 노드에 data를 추가하기 위한 메소드
	void linkLast(E e) {
		// 현재 마지막 노드의 객체(last)를 새로운 l 노드 객체에
		final Node<E> l = last;
		// prev가 l을 가리키고 data를 가지고 있는 새로운 노드 생성
		final Node<E> newNode = new Node<E>(l, e, null);
				
		// newNode는 add()시 마지막에 들어가므로 
		// MyLinkedList의 마지막 노드 객체가 되어야 한다.
		last = newNode;
				
		// l이 null이라는 말은 마지막 노드가 존재하지 않는,
		// MyLinkedList에 값이 존재하지 않는 상태이므로 first에 newNode가 들어간다.
		if(l == null) first = newNode;
		// 이전에 last 객체였던 l의 다음 객체로 newNode가 들어왔기 때문에
		// l의 next 객체를 newNode로 수정해준다.
		else l.next = newNode;
				
		// 노드가 늘어났기 때문에 size+1
		size++;
	}
	
	public E get(int index) {
		return node(index).data;
		
	}
	
	public E delete(int index) {
		Node<E> delNode = node(index);
		
		// 삭제할 노드의 정보를 새로 저장
		final E data = delNode.data;
		final Node<E> next = delNode.next;
		final Node<E> prev = delNode.prev;
		
		// 삭제할 노드의 이전 노드가 null이라는 것은 삭제할 노드가 첫번째 노드라는 뜻
		// 그러므로 삭제될 노드가 삭제되면 다음 노드가 first 되야한다.
		if(prev == null) {
			first = next;
		
		// 아닌 경우는 삭제될 노드의 이전 노드가 가리키는 다음 노드는 삭제될 노드가 아니라 
		// 삭제될 노드의 다음 노드가 되어야한다.
		} else {
			prev.next = next;
			delNode.prev = null;
		}
		
		// 반대로 삭제할 노드의 다음 노드가 null이라는 것은 삭제할 노드가 마지막 노드,
		// 그러므로 마지막 노드의 값은 삭제될 노드의 이전 노드가 되어야 한다.
		if(next == null) {
			last = prev;
		
		// 아닐 경우는 삭제될 노드의 다음 노드가 가리키는 이전 노드는 
		// 삭제될 노드의 이전 노드가 되어야 한다.
		} else {
			next.prev = prev;
			delNode.next = null;
		}
		
		delNode.data = null;
		size--;
		return data;
	}
	
	// Node를 찾는 메소드
	private Node<E> node(int index) {
		// size를 오른쪽으로 1 쉬프트한 다는 의미는 size의 크기를 2로 나눈 값을 의미한다.
		if(index < (size >> 1)) {
			// index가 나눈 값보다 작으므로 앞에서 부터 찾는게 빠르다
			Node<E> x = first;
			for(int i=0; i<index; i++) {
				x = x.next;
			}
			return x;
			
		} else {
			Node<E> x = last;
			for(int i=size -1; i>index; i--) {
				x = x.prev;
			}
			return x;
		}
	}

	// iterator 구현
	public Iterator<E> iterator() {
		return new MyLinkedListIterator();
	}
	
	private class MyLinkedListIterator implements Iterator<E> {
		
		private Node<E> lastReturned;
		private Node<E> next;
		
		MyLinkedListIterator() {
			this.next = first;
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();

			lastReturned = next;
			next = next.next;
			
			return lastReturned.data;
		}
	}
	
	
	// Stack을 위한 구현
	public void push(E e) {
		linkLast(e);
	}
	
	public E pop() {
		if(last == null) return null;
		
		return delete(size);
	}
	
	// Queue를 위한 구현
	public void offer(E e) {
		linkLast(e);
	}
	
	public E poll() {
		if(first == null) return null;
		
		return delete(0);
	}
}
