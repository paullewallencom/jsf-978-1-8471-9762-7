package chapter3.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.navmenu.NavigationMenuItem;

import chapter3.model.Customer;
import chapter3.model.Order;
import chapter3.model.Product;

public class ProductBean extends AbstractBean implements Converter {

	private CustomerBean customerBean;

	private Order order = new Order();

	private List<Product> products;

	private Map<String, Product> productsById;

	private Product selectedProduct;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Product product = null;
		if (value != null && !value.equals("")) {
			product = productsById.get(value);
		}
		return product;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String string = null;
		if (value instanceof String) {
			string = (String) value;
		} else if (value instanceof Product) {
			string = ((Product) value).getId();
		}
		return string;
	}

	public Order getOrder() {
		return order;
	}

	public List<Product> getProducts() {
		if (products == null) {
			products = new ArrayList<Product>();
			productsById = new HashMap<String, Product>();
			Scanner scanner = new Scanner(getClass().getResourceAsStream("/products.txt"));
			Random random = new Random();
			while (scanner.hasNextLine()) {
				Product product = new Product();
				product.setName(scanner.nextLine());
				product.setPrice(Double.valueOf(random.nextInt(10000)));
				product.setQuantityInStock(random.nextInt(5));
				products.add(product);
				productsById.put(product.getId(), product);
			}
			scanner.close();
		}
		return products;
	}

	public NavigationMenuItem getProductsNavigationMenuItemPull() {
		String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		String view = context + "/products.jsf";
		NavigationMenuItem menu = new NavigationMenuItem();
		menu.setLabel("Products");
		menu.setActiveOnViewIds("/products.jsf");
		menu.setExternalLink(view);
		List<Product> products = getProducts();
		for (Product product : products) {
			NavigationMenuItem item = new NavigationMenuItem();
			item.setAction("products");
			item.setLabel(product.getName());
			item.setExternalLink(view + "?product=" + product.getId());
			item.setValue(product);
			menu.add(item);
		}
		return menu;
	}

	public NavigationMenuItem getProductsNavigationMenuItemPush() {
		String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		String view = context + "/products.jsf";
		NavigationMenuItem menu = new NavigationMenuItem();
		menu.setLabel("Products");
		menu.setActiveOnViewIds("/products.jsf");
		menu.setExternalLink(view);
		List<Product> products = getProducts();
		for (Product product : products) {
			NavigationMenuItem item = new NavigationMenuItem();
			item.setAction("products");
			item.setLabel(product.getName());
			item.setActionListener("#{productBean.selectProduct}");
			item.setValue(product);
			menu.add(item);
		}
		return menu;
	}

	public NavigationMenuItem getProductsJsCookMenuItem() {
		String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		String view = context + "/products.jsf";
		NavigationMenuItem menu = new NavigationMenuItem();
		menu.setLabel("Products");
		menu.setActiveOnViewIds("/products.jsf");
		menu.setExternalLink(view);
		List<Product> products = getProducts();
		for (Product product : products) {
			NavigationMenuItem item = new NavigationMenuItem();
			item.setAction("products");
			item.setLabel(product.getName());
			item.setActionListener("#{productBean.selectProduct}");
			item.setValue(product.getId());
			menu.add(item);
		}
		return menu;
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

	public String placeOrder() {
		String outcome = null;
		if (selectedProduct != null) {
			if (selectedProduct.getQuantityInStock() > 0) {
				Customer customer = customerBean.getCustomer();
				order = new Order();
				order.setCustomer(customer);
				order.setProduct(selectedProduct);
				outcome = "order";
			} else {
				outcome = "outofstock";
			}
		}
		return outcome;
	}

	public void selectProduct(ActionEvent event) {
		UIComponent comp = event.getComponent();
		if (comp instanceof UICommand) {
			UICommand command = (UICommand) comp;
			Object value = command.getValue();
			if (value instanceof Product) {
				setSelectedProduct((Product) value);
			} else if (value instanceof String) {
				String id = (String) value;
				Product product = productsById.get(id);
				setSelectedProduct(product);
			}
		}
	}

	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public String submitOrder() {
		// TODO Submit order for processing
		order = new Order();
		return "success";
	}

	public String displayProducts() {
		getProducts();
		return "products";
	}

}
