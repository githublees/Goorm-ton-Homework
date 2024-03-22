package main;

import java.util.Objects;

public class Product {
	private long id;
	private String name;
	private int price;
	
	Product(long id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
	};
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		
		hash = 31 * hash + (int) this.id;	
		hash = 31 * hash + (this.name == null ? 0 : this.name.hashCode());
		hash = 31 * hash + this.price;	
		
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true; // 만일 현 객체 this와 매개변수 객체가 같을 경우 true
		if(!(obj instanceof Product)) return false; // 만일 매개변수 객체가 Product 타입과 호환되지 않으면 false
		Product product = (Product) obj; // 만일 매개변수 객체가 Product 타입과 호환된다면 다운캐스팅(down casting) 진행
		// this 객체 속성과 매개변수 객체 속성 등이 다 같을 경우
		return this.id == product.id && Objects.equals(this.name, product.name) && this.price == product.price;
	}

	
	
}
