package chapter4.model;

import java.io.Serializable;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private Customer customer = new Customer();

	private Product product;

	private Integer quantity;

	public Customer getCustomer() {
		return customer;
	}

	public Product getProduct() {
		return product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
