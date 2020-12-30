package chapter6.model;

public class Queries {

	public static final String ALL_COUNTRIES = "Country.findAll";

	public static final String ALL_CUSTOMERS = "Customer.findAll";

	public static final String ALL_ORDERS = "Order.findAll";

	public static final String ALL_PRODUCT_CATEGORIES = "ProductCategory.findAll";

	public static final String ALL_PRODUCTS = "Product.findAll";

	public static final String COUNTRIES_LIKE = "Country.findByNameLike";

	public static final String PRODUCT_CATEGORY_BY_NAME = "ProductCategory.findByName";

	public static final String PRODUCT_SUBCATEGORY_BY_NAME = "ProductCategory.findSubCategoryByName";

	public static final String PRODUCTS_BY_PRICE = "Product.findByPrice";

	public static final String UNIQUE_ORDER_NUMBER = "Order.findUniqueOrderNumber";

}
