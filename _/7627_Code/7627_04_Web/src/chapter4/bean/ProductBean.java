package chapter4.bean;

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

import org.apache.myfaces.custom.navmenu.NavigationMenuItem;
import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.MenuModel;
import org.apache.myfaces.trinidad.model.ProcessMenuModel;

import chapter4.model.Customer;
import chapter4.model.NavigationItem;
import chapter4.model.Order;
import chapter4.model.Product;
import chapter4.model.ProductCategory;

public class ProductBean extends AbstractBean implements Converter {

	private CustomerBean customerBean;

	private Order order = new Order();

	private Map<String, ProductCategory> productCategoriesByName;

	private ProcessMenuModel productCategoryModel;

	private List<Product> products;

	private Map<String, Product> productsById;

	private List<SelectItem> productSelectItems;

	private UIXTable productsTable;

	private Product selectedProduct;

	private Product[] selectedProducts;

	private List<Product> sortedProducts = new ArrayList<Product>();

	private String[] stringArray;

	public void cancelDialog(ActionEvent event) {
		RequestContext.getCurrentInstance().returnFromDialog(null, null);
	}

	public void closeProductDialog(ActionEvent event) {
		Iterator<Object> iterator = productsTable.getSelectedRowKeys().iterator();
		Object rowKey = iterator.next();
		Object oldRowKey = productsTable.getRowKey();
		productsTable.setRowKey(rowKey);
		Product product = null;
		UIComponent component = event.getComponent();
		UIParameter param = (UIParameter) component.findComponent("selectedProduct");
		if (param != null) {
			product = (Product) param.getValue();
		}
		if (param != null) {
			setSelectedProduct(product);
			RequestContext.getCurrentInstance().returnFromDialog(product.getName(), null);
		}
		productsTable.setRowKey(oldRowKey);
	}

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

	public MenuModel getProductCategoryModel() {
		try {
			if (productCategoryModel == null) {
				String view = FacesContext.getCurrentInstance().getViewRoot().getViewId();
				NavigationItem rootItem = new NavigationItem("Products", view);
				List<ProductCategory> categories = getProductCategories();
				Collections.sort(categories);
				for (ProductCategory category : categories) {
					// include top-level categories only
					if (category.getParentCategory() != null) {
						continue;
					}				
					String categoryViewId = view + "?category=" + URLEncoder.encode(category.getName(), "UTF-8");
					NavigationItem categoryItem = new NavigationItem(category.getName(), categoryViewId);
					rootItem.getChildren().add(categoryItem);
					for (ProductCategory subcategory : category.getSubcategories()) {
						String subcategoryViewId = categoryViewId + "&subcategory=" + URLEncoder.encode(subcategory.getName(), "UTF-8");
						NavigationItem subcategoryItem = new NavigationItem(subcategory.getName(), subcategoryViewId);
						categoryItem.getChildren().add(subcategoryItem);
						List<Product> products = new ArrayList<Product>(subcategory.getProducts());
						Collections.sort(products);
						for (Product product : products) {
							String productViewId = view + "?product=" + product.getId();
							NavigationItem productItem = new NavigationItem(product.getName(), productViewId);
							subcategoryItem.getChildren().add(productItem);
						}
					}
				}
				ChildPropertyTreeModel treeModel = new ChildPropertyTreeModel(rootItem, "children");
				productCategoryModel = new ProcessMenuModel(treeModel, "viewId");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productCategoryModel;
	}

	public List<Product> getProducts() {
		if (products == null) {
			initProducts();
		}
		return products;
	}

	public List<SelectItem> getProductSelectItems() {
		if (productSelectItems == null) {
			productSelectItems = new ArrayList<SelectItem>();
			List<Product> products = getProducts();
			for (Product product : products) {
				SelectItem item = new SelectItem();
				item.setLabel(product.getName());
				item.setValue(product);
				productSelectItems.add(item);
			}
		}
		return productSelectItems;
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

	public UIXTable getProductsTable() {
		return productsTable;
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

	public List<Product> getSortedProducts() {
		return sortedProducts;
	}

	public String[] getStringArray() {
		return stringArray;
	}

	private void initProducts() {
		products = new ArrayList<Product>();
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
			Product product = new Product();
			ProductCategory productCategory = getProductCategory(category);
			ProductCategory productSubcategory = getProductCategory(subcategory);
			product.setName(name);
			product.setPrice(Double.valueOf(random.nextInt(10000)));
			product.setQuantityInStock(random.nextInt(100));
			product.setProductCategory(productSubcategory);
			productCategory.getProducts().add(product);
			productCategory.getSubcategories().add(productSubcategory);
			productSubcategory.getProducts().add(product);
			productSubcategory.setParentCategory(productCategory);
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

	public void setProductsTable(UIXTable productsTable) {
		this.productsTable = productsTable;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public void setSelectedProducts(Product[] sortedProducts) {
		this.selectedProducts = sortedProducts;
	}

	public void setSortedProducts(List<Product> sortedProducts) {
		this.sortedProducts = sortedProducts;
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
