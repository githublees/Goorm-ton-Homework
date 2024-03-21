package main;

import java.util.Iterator;

public class test {

	public static void main(String[] args) {
		MyLinkedList<String> list = new MyLinkedList<>();
		
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");

		// get 구현
		System.out.println(list.get(3));
		
		// delete 구현
		list.delete(2);
		
		// Iterator interface를 구현하여 for-each로 순회 가능하도록 구현
		Iterator<String> iter = list.iterator();
		while(iter.hasNext()) {
			System.out.print(iter.next() + " ");
		}
		System.out.println();
		
		// Stack 구현
		list.push("push 1");
		list.push("push 2");
		list.push("push 3");
		
		System.out.println(list.pop()); // push 3
		System.out.println(list.pop()); // push 2

		// Queue 구현
		list.offer("offer 1");
		list.offer("offer 2");
		list.offer("offer 3");
		
		System.out.println(list.poll()); // 0
		System.out.println(list.poll()); // 1
		System.out.println(list.poll()); // 3
		System.out.println(list.poll()); // push 1
		System.out.println(list.poll()); // offer 1
		System.out.println(list.poll()); // offer 2
		System.out.println(list.poll()); // offer 3
		System.out.println(list.poll()); // null
	}

}
