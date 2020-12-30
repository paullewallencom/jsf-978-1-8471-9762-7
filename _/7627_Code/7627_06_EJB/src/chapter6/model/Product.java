package chapter6.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Min;
import org.hibernate.validator.NotNull;

@Entity
@Table(catalog = "jsfbook")
public class Product extends AbstractEntity implements Serializable, Comparable<Product> {

	private static final long serialVersionUID = 1L;

	private ProductCategory category;

	private String name;

	private Double price;

	private Integer quantityInStock = 0;

	public int compareTo(Product o) {
		return name.compareTo(o.name);
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_category_id", insertable = true, updatable = true, nullable = false)
	public ProductCategory getCategory() {
		return category;
	}

	@Transient
	public String getIcon() {
		String name = category.getName();
		name = name.toLowerCase();
		name = name.replace(" ", "-");
		name += ".gif";
		return name;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@Min(value = 0)
	public Double getPrice() {
		return price;
	}

	@Min(value = 0)
	public Integer getQuantityInStock() {
		return quantityInStock;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public String toString() {
		return getClass().getName() + "[" + getId() + "]";
	}
}
