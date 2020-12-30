package chapter5.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.faces.event.ActionEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import chapter5.model.AgeGroup;
import chapter5.model.Country;
import chapter5.model.Customer;
import chapter5.model.SortableDataModel;

public class CustomerBean extends AbstractBean {

	private DataModel columnDataModel;

	private List<Country> countries;

	private List<SelectItem> countrySelectItems;

	private Customer customer = new Customer();

	private double[][] customerAgeBarChartData;

	private List<Double> customerCountryPieChartData;

	private Map<String, Object> customerData = new HashMap<String, Object>();

	private List<Double> customerGenderData;

	private List<Customer> customerList;

	private String informationSource;

	private String[] informationSources;

	private List<String> interestList = new ArrayList<String>();

	private String[] interests;

	private DataModel rowDataModel;

	private SortableDataModel<Customer> sortableCustomerModel;

	public List<String> getAgeGroupLabels() {
		List<String> list = new ArrayList<String>();
		for (AgeGroup group : AgeGroup.values()) {
			list.add(group.getNumericLabel());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Object getCellValue() {
		Object value = null;
		DataModel rowDataModel = getRowDataModel();
		if (rowDataModel.isRowAvailable()) {
			List<Object> rowData = (List<Object>) rowDataModel.getRowData();
			DataModel columnDataModel = getColumnDataModel();
			if (columnDataModel.isRowAvailable()) {
				Object columnData = columnDataModel.getRowData();
				int column = Integer.parseInt(columnData.toString());
				value = rowData.get(column - 1);
			}
		}
		return value;
	}

	public DataModel getColumnDataModel() {
		if (columnDataModel == null) {
			int numberOfColumns = 4;
			String[] array = new String[numberOfColumns];
			for (int i = 0; i < numberOfColumns; i++) {
				array[i] = String.valueOf(i + 1);
			}
			columnDataModel = new ArrayDataModel(array);
		}
		return columnDataModel;
	}

	private List<Country> getCountries() {
		if (countries == null) {
			try {
				countries = new ArrayList<Country>();
				Scanner scanner = new Scanner(getClass().getResourceAsStream("/countries.txt"));
				while (scanner.hasNextLine()) {
					countries.add(new Country(scanner.nextLine()));
				}
				scanner.close();
				Collections.sort(countries);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return countries;
	}

	public List<String> getCountryNames() {
		List<String> list = new ArrayList<String>();
		List<Country> countries = getCountries();
		for (Country country : countries) {
			list.add(country.getName());
		}
		return list;
	}

	public List<SelectItem> getCountrySelectItems() {
		if (countrySelectItems == null) {
			countrySelectItems = new ArrayList<SelectItem>();
			List<Country> countries = getCountries();
			for (Country country : countries) {
				SelectItem item = new SelectItem();
				item.setLabel(country.getName());
				item.setValue(country);
				countrySelectItems.add(item);
			}
		}
		return countrySelectItems;
	}

	public Customer getCustomer() {
		return customer;
	}

	public double[][] getCustomerAgeBarChartData() {
		if (customerAgeBarChartData == null) {
			customerAgeBarChartData = new double[1][6];
			List<Customer> customers = getCustomerList();
			int customerCount = customers.size();
			AgeGroup[] ageGroups = AgeGroup.values();
			for (int i = 0; i < ageGroups.length; i++) {
				customerAgeBarChartData[0][i] = 0;
				AgeGroup ageGroup = ageGroups[i];
				for (int j = 0; j < customerCount; j++) {
					Customer customer = customers.get(j);
					int age = customer.getAge();
					int[] range = ageGroup.getRange();
					int min = range[0];
					int max = range[1];
					if (age < max && age >= min) {
						customerAgeBarChartData[0][i]++;
					}
				}

			}
		}
		return customerAgeBarChartData;
	}

	private Map<Country, Double> getCustomerCountryDataMap() {
		List<Customer> customers = getCustomerList();
		Map<Country, Double> map = new HashMap<Country, Double>();
		for (Customer customer : customers) {
			Country country = customer.getCountryOfOrigin();
			Double count = map.get(country);
			if (count == null) {
				count = 0d;
			}
			count++;
			map.put(country, count);
		}
		return map;
	}

	public List<Double> getCustomerCountryPieChartData() {
		if (customerCountryPieChartData == null) {
			customerCountryPieChartData = new ArrayList<Double>();
			Map<Country, Double> map = getCustomerCountryDataMap();
			List<Country> list = new ArrayList<Country>(map.keySet());
			Collections.sort(list);
			for (Country country : list) {
				Double value = map.get(country);
				if (value == null) {
					value = 0d;
				}
				map.put(country, value);
			}
			for (Country country : getCountries()) {
				Double value = map.get(country);
				if (value == null) {
					value = 0d;
				}
				customerCountryPieChartData.add(value);
			}
		}
		return customerCountryPieChartData;
	}

	public List<Double> getCustomerGenderData() {
		if (customerGenderData == null) {
			customerGenderData = new ArrayList<Double>();
			List<Customer> customers = getCustomerList();
			double male = 0;
			double female = 0;
			for (Customer customer : customers) {
				if (customer.getMale() != null && customer.getMale()) {
					male++;
				} else {
					female++;
				}
			}
			customerGenderData.add(male);
			customerGenderData.add(female);
		}
		return customerGenderData;
	}

	public List<Customer> getCustomerList() {
		if (customerList == null) {
			customerList = loadCustomers();
		}
		return customerList;
	}

	public Object getDailyAppointmentsScheduleModel() {
		return null;
	}

	public String getInformationSource() {
		return informationSource;
	}

	public String[] getInformationSources() {
		return informationSources;
	}

	public List<String> getInterestList() {
		return interestList;
	}

	public String[] getInterests() {
		return interests;
	}

	public DataModel getRowDataModel() {
		if (rowDataModel == null) {
			List<List<Object>> rowData = new ArrayList<List<Object>>();
			List<Customer> customers = getCustomerList();
			List<Object> customerData = null;
			for (Customer customer : customers) {
				customerData = new ArrayList<Object>();
				customerData.add(customer.getFullName());
				customerData.add(customer.getBirthDate());
				customerData.add(customer.getPhoneNumber());
				customerData.add(customer.getCountryOfOrigin());
				rowData.add(customerData);
			}
			rowDataModel = new ListDataModel(rowData);
		}
		return rowDataModel;
	}

	public SortableDataModel<Customer> getSortableCustomerModel() {
		if (sortableCustomerModel == null) {
			List<Customer> customers = getCustomerList();
			sortableCustomerModel = new SortableDataModel<Customer>(customers, "fullName");
			sortableCustomerModel.setColumnName("fullName");
		}
		return sortableCustomerModel;
	}

	private void initCustomerData(Customer customer) {
		String id = customer.getId();
		customerData.put(id + ":Name", customer.getFullName());
		customerData.put(id + ":Age", customer.getAge());
		customerData.put(id + ":Phone Number", customer.getPhoneNumber());
		customerData.put(id + ":Birth Date", customer.getBirthDate());
		customerData.put(id + ":Country of Origin", customer.getCountryOfOrigin());
	}

	private List<Customer> loadCustomers() {
		List<Customer> list = null;
		try {
			list = new ArrayList<Customer>();
			List<String> names = new ArrayList<String>();
			Scanner scanner = new Scanner(getClass().getResourceAsStream("/names.txt"));
			while (scanner.hasNextLine()) {
				names.add(scanner.nextLine());
			}
			scanner.close();
			Collections.shuffle(names);
			List<Country> countries = getCountries();
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR) - 60;
			Random rand = new Random();
			for (int i = 0; i < 100; i++) {
				String firstName = names.get(rand.nextInt(names.size()));
				String lastName = names.get(rand.nextInt(names.size()));
				calendar.set(Calendar.YEAR, rand.nextInt(40) + year);
				calendar.set(Calendar.DAY_OF_YEAR, rand.nextInt(364) + 1);
				calendar.set(Calendar.HOUR_OF_DAY, rand.nextInt(23));
				calendar.set(Calendar.MINUTE, rand.nextInt(59));
				calendar.set(Calendar.SECOND, rand.nextInt(59));
				Date birthDate = calendar.getTime();
				Country country = countries.get(rand.nextInt(countries.size()));
				Customer customer = new Customer();
				customer.setFirstName(firstName);
				customer.setLastName(lastName);
				customer.setBirthDate(birthDate);
				int phone = rand.nextInt(9999);
				if (phone < 1000) {
					phone += 1000;
				}
				customer.setPhoneNumber("555-" + phone);
				customer.setMale(rand.nextBoolean());
				customer.setCountryOfOrigin(country);
				list.add(customer);
				initCustomerData(customer);
			}
			Collections.sort(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void saveCustomer(ActionEvent event) {
		customer = new Customer();
		interests = null;
	}

	public void saveInterests(ActionEvent event) {
		if (interests == null) {
			return;
		}
		interestList.clear();
		int len = interests.length;
		for (int i = 0; i < len; i++) {
			String interest = interests[i];
			if (!interestList.contains(interest)) {
				interestList.add(interest);
			}
		}
	}

	public void setInformationSource(String informationSource) {
		this.informationSource = informationSource;
	}

	public void setInformationSources(String[] informationSources) {
		this.informationSources = informationSources;
	}

	public void setInterestList(List<String> interestList) {
		this.interestList = interestList;
	}

	public void setInterests(String[] interests) {
		this.interests = interests;
	}

	public void sortCustomersByBirthDate(ActionEvent event) {
		sortableCustomerModel.setColumnName("birthDate");
		sortableCustomerModel.checkState();
	}

	public void sortCustomersByCountry(ActionEvent event) {
		sortableCustomerModel.setColumnName("countryOfOrigin");
		sortableCustomerModel.checkState();
	}

	public void sortCustomersByName(ActionEvent event) {
		sortableCustomerModel.setColumnName("fullName");
		sortableCustomerModel.checkState();
	}

	public void sortCustomersByPhoneNumber(ActionEvent event) {
		sortableCustomerModel.setColumnName("phoneNumber");
		sortableCustomerModel.checkState();
	}

	public void submitSurveyResponse(ActionEvent event) {
	}

}
