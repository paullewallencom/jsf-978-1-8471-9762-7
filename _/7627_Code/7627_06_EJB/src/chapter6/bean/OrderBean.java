package chapter6.bean;

import java.util.List;

import javax.ejb.Local;

import chapter6.model.LineItem;
import chapter6.model.Order;

@Local
public interface OrderBean {

	public LineItem getLineItem();

	public Order getOrder();

	public List<Order> getOrders();

	public void remove();

	public void setOrder(Order order);

	public String submitOrder();

}
