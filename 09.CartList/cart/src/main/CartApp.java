package main;

import java.util.HashSet;

public class CartApp {

	public static void main(String[] args) {
		// 상품 목록 생성
		HashSet<Product> productSet = new HashSet<>();
		
		// 상품 클래스 생성 및 상품목록에 넣기
		Product milk = new Product(1, "milk", 2500);
		productSet.add(milk);
		
		Product apple = new Product(2, "apple", 4500);
		productSet.add(apple);
		
		Product strawberry = new Product(3, "strawberry", 9000);
		productSet.add(strawberry);
		
		Product banana = new Product(4, "banana", 6000);
		productSet.add(banana);
		
		// 상품 목록 확인
		System.out.println("고유한 상품 목록");
		System.out.println("=================");
		for(Product product : productSet) {
			System.out.println(product.getName() + " : " + product.getPrice());
		}
		System.out.println();
		
		// 장바구니 생성
		Cart myCart = Cart.getInstance();
		
		// 상품을 장바구니에 추가
		myCart.addProduct(milk, 2);
		myCart.addProduct(apple, 1);
		myCart.addProduct(strawberry, 1);
		myCart.addProduct(milk, 2);
		
		// 상품을 장바구니에서 제거
		myCart.removeProduct(apple);
		
		// 장바구니에 담긴 상품들을 출력 (상품이름, 각 상품의 갯수)
		System.out.println("장바구니 목록");
		System.out.println("=================");
		myCart.showItems();
	}

}
