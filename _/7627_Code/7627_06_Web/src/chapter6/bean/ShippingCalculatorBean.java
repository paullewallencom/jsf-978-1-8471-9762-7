package chapter6.bean;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import chapter6.model.Country;
import chapter6.model.Product;

@Name("shippingCalculatorBeanSeam")
@Scope(ScopeType.CONVERSATION)
public class ShippingCalculatorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Country country;

	private Product product;

	public Country getCountry() {
		if (country == null) {
			country = new Country();
		}
		return country;
	}

	public Product getProduct() {
		return product;
	}

	public Double getTotal() {
		Double total = 0d;
		if (country != null && product != null) {
			total = product.getPrice();
			if (country.getName().equals("USA")) {
				total = +5d;
			} else {
				total = +10d;
			}
		}
		return total;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
