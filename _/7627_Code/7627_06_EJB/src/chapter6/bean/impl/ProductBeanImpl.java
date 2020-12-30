package chapter6.bean.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.richfaces.event.DataFilterSliderEvent;

import chapter6.bean.ProductBean;
import chapter6.model.Order;
import chapter6.model.Product;
import chapter6.model.ProductCategory;
import chapter6.model.Queries;

@Stateful
@Name("productBean")
@Scope(ScopeType.CONVERSATION)
public class ProductBeanImpl implements ProductBean {

	private Order order = new Order();

	@In
	private EntityManager em;

	@Logger
	protected Log logger;
	private List<ProductCategory> productCategories;

	private List<Product> products;

	private Map<String, Product> productsById;

	private Product selectedProduct;

	private List<Product> selectedProducts;

	@SuppressWarnings("unchecked")
	public void filterByPrice(DataFilterSliderEvent event) {
		int sliderVal = event.getNewSliderVal();
		Query query = em.createNamedQuery(Queries.PRODUCTS_BY_PRICE);
		query.setParameter(1, sliderVal);
		products = query.getResultList();
	}

	public Order getOrder() {
		return order;
	}

	public Product getProductById(String value) {
		Product p = em.find(Product.class, value);
		return p;
	}

	@SuppressWarnings("unchecked")
	public List<ProductCategory> getProductCategories() {
		if (productCategories == null) {
			productCategories = em.createNamedQuery(Queries.ALL_PRODUCT_CATEGORIES).getResultList();
		}
		return productCategories;
	}

	@SuppressWarnings("unchecked")
	public List<Product> getProducts() {
		if (products == null) {
			products = em.createNamedQuery(Queries.ALL_PRODUCTS).getResultList();
		}
		return products;
	}

	public List<Product> getRandomProducts() {
		List<Product> products = new ArrayList<Product>(getProducts());
		Collections.shuffle(products);
		return products;
	}

	public Product getSelectedProduct() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String param = external.getRequestParameterMap().get("product");
		if (param != null && !param.equals("")) {
			selectedProduct = productsById.get(param);
		}
		return selectedProduct;
	}

	public List<Product> getSelectedProducts() {
		return selectedProducts;
	}

	public void selectFavoriteProducts(ActionEvent event) {
		selectedProducts = getRandomProducts();
	}

	public void selectPopularProducts(ActionEvent event) {
		selectedProducts = getRandomProducts();
	}

	public void selectPurchasedProducts(ActionEvent event) {
		selectedProducts = getRandomProducts();
	}

	public void selectRecommendedProducts(ActionEvent event) {
		selectedProducts = getRandomProducts();
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	@Remove
	@Destroy
	public void remove() {

	}

}
