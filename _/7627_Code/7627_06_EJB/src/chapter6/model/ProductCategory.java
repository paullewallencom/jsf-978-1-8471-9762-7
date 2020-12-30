package chapter6.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(catalog = "jsfbook")
public class ProductCategory extends AbstractEntity implements Comparable<ProductCategory> {

	private static final long serialVersionUID = 1L;

	private String name;

	private ProductCategory parentCategory;

	private Set<Product> products = new LinkedHashSet<Product>(0);

	private Set<ProductCategory> subCategories = new LinkedHashSet<ProductCategory>(0);

	public int compareTo(ProductCategory o) {
		return this.name.compareTo(o.name);
	}

	public String getName() {
		return name;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "parent_category_id", insertable = true, updatable = true, nullable = true)
	public ProductCategory getParentCategory() {
		return parentCategory;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Product> getProducts() {
		return products;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "parentCategory")
	public Set<ProductCategory> getSubCategories() {
		return subCategories;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentCategory(ProductCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public void setSubCategories(Set<ProductCategory> subcategories) {
		this.subCategories = subcategories;
	}
}
