package chapter5.model;

import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable, Comparable<Product> {

	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString();

	private String name;

	private Double price;

	private ProductCategory productCategory;

	private Integer quantityInStock = 0;

	public String getIcon() {
		String name = productCategory.getName();
		name = name.toLowerCase();
		name = name.replace(" ", "-");
		name += ".gif";
		return name;
	}

	public int compareTo(Product o) {
		return name.compareTo(o.name);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public Integer getQuantityInStock() {
		return quantityInStock;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public String toString() {
		return getClass().getName() + "[" + getId() + "]";
	}
}
