package chapter4.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class ProductCategory implements Comparable<ProductCategory> {

	private String name;

	private Set<Product> products = new LinkedHashSet<Product>(0);

	private ProductCategory parentCategory;

	public ProductCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(ProductCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	private Set<ProductCategory> subcategories = new LinkedHashSet<ProductCategory>(0);

	public String getName() {
		return name;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public Set<ProductCategory> getSubcategories() {
		return subcategories;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public void setSubcategories(Set<ProductCategory> subcategories) {
		this.subcategories = subcategories;
	}

	public int compareTo(ProductCategory o) {
		return this.name.compareTo(o.name);
	}
}
