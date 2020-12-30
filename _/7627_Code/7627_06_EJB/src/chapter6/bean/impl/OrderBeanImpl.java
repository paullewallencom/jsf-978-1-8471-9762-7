package chapter6.bean.impl;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Conversational;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;

import chapter6.bean.OrderBean;
import chapter6.model.Customer;
import chapter6.model.LineItem;
import chapter6.model.Order;
import chapter6.model.Queries;

@Stateful
@Conversational
@Name("orderBean")
@Scope(ScopeType.CONVERSATION)
public class OrderBeanImpl implements OrderBean {

	private LineItem lineItem;

	@In
	private EntityManager em;

	@In
	private FacesMessages facesMessages;

	@Logger
	private Log logger;

	private Order order;

	private List<Order> orders;

	public LineItem getLineItem() {
		if (lineItem == null) {
			lineItem = new LineItem();
			lineItem.setQuantity(1);
			Order order = getOrder();
			lineItem.setOrder(order);
			order.getLineItems().add(lineItem);
		}
		return lineItem;
	}

	public Order getOrder() {
		if (order == null) {
			order = new Order();
			order.setCustomer(new Customer());
		}
		return order;
	}

	private Integer getUniqueOrderNumber() {
		Integer value = null;
		Query query = em.createNamedQuery(Queries.UNIQUE_ORDER_NUMBER);
		value = (Integer) query.getSingleResult();
		return value;
	}

	@SuppressWarnings("unchecked")
	public List<Order> getOrders() {
		if (orders == null) {
			orders = em.createNamedQuery(Queries.ALL_ORDERS).getResultList();
		}
		return orders;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@End
	public String submitOrder() {
		String outcome = null;
		try {
			if (lineItem.getProduct().getQuantityInStock() > 0) {
				order.setOrderNumber(getUniqueOrderNumber());
				em.persist(order);
				facesMessages.add("Thank you, {0}. Your order has been received.", order.getCustomer().getFirstName());
				outcome = "success";
			} else {
				outcome = "out-of-stock";
			}
		} catch (Exception e) {
			logger.error("Failed to submit order:", e);
			facesMessages.add(Severity.ERROR, "Sorry, we were unable to process your order.");
		}
		return outcome;
	}

	@Remove
	@Destroy
	public void remove() {
	}

}
