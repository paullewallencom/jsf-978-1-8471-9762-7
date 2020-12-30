package chapter5.bean;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import chapter5.model.Customer;
import chapter5.model.Order;
import chapter5.model.Product;
import chapter5.model.ProductCategory;
import chapter5.model.RankedProduct;

import com.icesoft.faces.component.menubar.MenuItem;
import com.icesoft.faces.component.panelpositioned.PanelPositionedEvent;

public class ProductBean extends AbstractBean implements Converter {

	private CustomerBean customerBean;

	private Order order = new Order();

	private Map<String, ProductCategory> productCategoriesByName;

	private List<RankedProduct> products;

	private Map<String, Product> productsById;

	private List<SelectItem> productSelectItems;

	private List<RankedProduct> rankedProducts;

	private Product selectedProduct;

	private Product[] selectedProducts;

	private String[] stringArray;

	public String displayProducts() {
		getProducts();
		return "products";
	}

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

	private List<ProductCategory> getProductCategories() {
		if (products == null) {
			initProducts();
		}
		List<ProductCategory> categories = new ArrayList<ProductCategory>(productCategoriesByName.values());
		Iterator<ProductCategory> it = categories.iterator();
		while (it.hasNext()) {
			ProductCategory category = it.next();
			if (category.getParent() != null) {
				it.remove();
			}
		}
		return categories;
	}

	private ProductCategory getProductCategory(String category) {
		ProductCategory productCategory = null;
		productCategory = productCategoriesByName.get(category);
		if (productCategory == null) {
			productCategory = new ProductCategory();
			productCategory.setName(category);
			productCategoriesByName.put(category, productCategory);
		}
		return productCategory;
	}

	public List<MenuItem> getProductMenuItems() {
		List<MenuItem> model = null;
		try {
			model = new ArrayList<MenuItem>();
			List<ProductCategory> categories = getProductCategories();
			String view = FacesContext.getCurrentInstance().getViewRoot().getViewId();
			String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			if (!context.equals("/")) {
				view = context + view;
			}
			Collections.sort(categories);
			for (ProductCategory category : categories) {
				MenuItem categoryItem = new MenuItem();
				String categoryViewId = view + "?category=" + URLEncoder.encode(category.getName(), "UTF-8");
				categoryItem.setLink(categoryViewId);
				categoryItem.setValue(category.getName());
				categoryItem.setId("item" + category.getId());
				model.add(categoryItem);
				for (ProductCategory subcategory : category.getSubcategories()) {
					String subcategoryViewId = categoryViewId + "&subcategory=" + URLEncoder.encode(subcategory.getName(), "UTF-8");
					MenuItem subcategoryItem = new MenuItem();
					subcategoryItem.setLink(subcategoryViewId);
					subcategoryItem.setValue(subcategory.getName());
					subcategoryItem.setId("item" + subcategory.getId());
					categoryItem.getChildren().add(subcategoryItem);
					List<Product> products = new ArrayList<Product>(subcategory.getProducts());
					Collections.sort(products);
					for (Product product : products) {
						String productViewId = view + "?product=" + product.getId();
						MenuItem productItem = new MenuItem();
						productItem.setLink(productViewId);
						productItem.setValue(product.getName());
						productItem.setId("item" + product.getId());
						subcategoryItem.getChildren().add(productItem);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	public List<RankedProduct> getProducts() {
		if (products == null) {
			initProducts();
		}
		return products;
	}

	public List<SelectItem> getProductSelectItems() {
		if (productSelectItems == null) {
			productSelectItems = new ArrayList<SelectItem>();
			List<? extends Product> products = getProducts();
			for (Product product : products) {
				SelectItem item = new SelectItem();
				item.setLabel(product.getName());
				item.setValue(product);
				productSelectItems.add(item);
			}
		}
		return productSelectItems;
	}

	public List<Product> getRandomProducts() {
		List<Product> products = new ArrayList<Product>(getProducts());
		Collections.shuffle(products);
		return products;
	}

	public List<RankedProduct> getRankedProducts() {
		if (rankedProducts == null) {
			rankedProducts = new ArrayList<RankedProduct>();
			List<RankedProduct> list = getProducts();
			for (int i = 0; i < 10; i++) {
				RankedProduct product = list.get(i);
				product.setRank(i + 1);
				rankedProducts.add(product);
			}
		}
		return rankedProducts;
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

	public Product[] getSelectedProducts() {
		return selectedProducts;
	}

	public String[] getStringArray() {
		return stringArray;
	}

	private void initProducts() {
		products = new ArrayList<RankedProduct>();
		productsById = new HashMap<String, Product>();
		productCategoriesByName = new HashMap<String, ProductCategory>();
		Random random = new Random();
		Scanner scanner = new Scanner(getClass().getResourceAsStream("/products.csv"));
		if (scanner.hasNextLine()) {
			scanner.nextLine(); // remove header
		}
		while (scanner.hasNextLine()) {
			String[] data = scanner.nextLine().split(",");
			String name = data[0];
			String category = data[1];
			String subcategory = data[2];
			RankedProduct product = new RankedProduct();
			ProductCategory productCategory = getProductCategory(category);
			ProductCategory productSubcategory = getProductCategory(subcategory);
			product.setName(name);
			product.setPrice(Double.valueOf(random.nextInt(10000)));
			product.setQuantityInStock(random.nextInt(100));
			product.setProductCategory(productSubcategory);
			productCategory.getProducts().add(product);
			productCategory.getSubcategories().add(productSubcategory);
			productSubcategory.getProducts().add(product);
			productSubcategory.setParent(productCategory);
			products.add(product);
			productsById.put(product.getId(), product);
		}
		scanner.close();
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

	public void rankChanged(PanelPositionedEvent evt) {
		resetRank();
		List<RankedProduct> list = getRankedProducts();
		if (evt.getOldIndex() >= 0) {
			((RankedProduct) list.get(evt.getIndex())).getEffect().setFired(false);
		}
	}

	private void resetRank() {
		List<RankedProduct> list = getRankedProducts();
		for (int i = 0; i < 10; i++) {
			((RankedProduct) list.get(i)).setRank(i + 1);
		}
	}

	public void saveSelectedProducts(ActionEvent event) {
		if (selectedProducts != null) {
			System.out.println("User selected products: ");
			for (Product product : selectedProducts) {
				System.out.println(product.getName());
			}
		}
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
		} else {
			for (UIComponent child : event.getComponent().getChildren()) {
				if (child instanceof UIParameter) {
					UIParameter param = (UIParameter) child;
					Object value = param.getValue();
					if (value instanceof Product) {
						setSelectedProduct((Product) value);
					}
				}
			}
		}
	}

	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public void setSelectedProducts(Product[] sortedProducts) {
		this.selectedProducts = sortedProducts;
	}

	public void setStringArray(String[] val) {
		stringArray = val;
	}

	public String submitOrder() {
		// TODO Submit order for processing
		order = new Order();
		return "success";
	}
}
