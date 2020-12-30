package chapter6.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Min;
import org.hibernate.validator.NotNull;

@Entity
@Table(catalog = "jsfbook")
public class LineItem extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Order order;

	private Product product;

	private Integer quantity;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", insertable = true, updatable = true, nullable = false)
	public Order getOrder() {
		return order;
	}

	@NotNull
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", insertable = true, updatable = true, nullable = false)
	public Product getProduct() {
		return product;
	}

	@NotNull
	@Min(1)
	public Integer getQuantity() {
		return quantity;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
