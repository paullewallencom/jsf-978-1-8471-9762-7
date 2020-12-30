package chapter6.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;

@Entity
@Table(catalog = "jsfbook", name = "purchase_order")
public class Order extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Customer customer = new Customer();

	private Date dateReceived;

	private Set<LineItem> lineItems = new LinkedHashSet<LineItem>(0);

	private Integer orderNumber;

	private Country shippingDestination;

	@NotNull
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "customer_id", insertable = true, updatable = true, nullable = false)
	public Customer getCustomer() {
		return customer;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
	public Set<LineItem> getLineItems() {
		return lineItems;
	}

	@Column(unique = true, insertable = true, updatable = true, nullable = false)
	public Integer getOrderNumber() {
		return orderNumber;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "shipping_destination_id", insertable = true, updatable = true, nullable = true)
	public Country getShippingDestination() {
		return shippingDestination;
	}

	@Transient
	public Double getTotal() {
		Double total = 0d;
		for (LineItem item : lineItems) {
			total += item.getQuantity() * item.getProduct().getPrice();
		}
		return total;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public void setLineItems(Set<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setShippingDestination(Country shippingDestination) {
		this.shippingDestination = shippingDestination;
	}

}
