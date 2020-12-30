package chapter6.bean;

import java.util.List;

import javax.ejb.Local;
import javax.faces.event.ActionEvent;

import org.richfaces.event.DataFilterSliderEvent;

import chapter6.model.Order;
import chapter6.model.Product;
import chapter6.model.ProductCategory;

@Local
public interface ProductBean {

	public void filterByPrice(DataFilterSliderEvent event);

	public Order getOrder();

	public Product getProductById(String value);

	public List<ProductCategory> getProductCategories();

	public List<Product> getProducts();

	public List<Product> getRandomProducts();

	public Product getSelectedProduct();

	public List<Product> getSelectedProducts();

	public void remove();

	public void selectFavoriteProducts(ActionEvent event);

	public void selectPopularProducts(ActionEvent event);

	public void selectPurchasedProducts(ActionEvent event);

	public void selectRecommendedProducts(ActionEvent event);
	
	public void setSelectedProduct(Product selectedProduct);

}