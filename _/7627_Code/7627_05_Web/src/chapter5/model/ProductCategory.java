package chapter5.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class ProductCategory implements Comparable<ProductCategory> {

	private String id = UUID.randomUUID().toString();

	private String name;

	private ProductCategory parent;

	private Set<Product> products = new LinkedHashSet<Product>(0);

	private Set<ProductCategory> subcategories = new LinkedHashSet<ProductCategory>(0);

	public int compareTo(ProductCategory o) {
		return this.name.compareTo(o.name);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ProductCategory getParent() {
		return parent;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public Set<ProductCategory> getSubcategories() {
		return subcategories;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public void setSubcategories(Set<ProductCategory> subcategories) {
		this.subcategories = subcategories;
	}
}
