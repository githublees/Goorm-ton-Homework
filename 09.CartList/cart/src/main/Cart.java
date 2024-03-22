package main;

import java.util.HashMap;

public class Cart {
	
	private HashMap<Product, Integer> cart = new HashMap<>();
	
	Cart() {}
	
	private static class CartInstanceHolder {
		private static final Cart INSTANCE = new Cart();
	}
	
	public static Cart getInstance() {
		return CartInstanceHolder.INSTANCE;
	}	
	
	public void addProduct(Product p, int quantity) {
		cart.put(p, cart.getOrDefault(p, 0) + quantity);
	}
	
	public void showItems() {
		for(Product p : cart.keySet()) {
			System.out.println("상품이름: " + p.getName() + ", 상품수량: " + cart.get(p));
		}
	}
	
	public void removeProduct(Product p) {
		cart.remove(p);
	}

}
