package chapter2.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import chapter2.model.Country;
import chapter2.model.Customer;

public class CustomerBean {

	private List<SelectItem> countrySelectItems;

	private Customer customer = new Customer();

	private List<Customer> customerList;

	private String informationSource;

	private String[] informationSources;

	private List<String> interestList = new ArrayList<String>();

	private String[] interests;

	private List<Country> getCountries() {
		List<Country> list = null;
		try {
			list = new ArrayList<Country>();
			Scanner scanner = new Scanner(getClass().getResourceAsStream("/countries.txt"));
			while (scanner.hasNextLine()) {
				list.add(new Country(scanner.nextLine()));
			}
			scanner.close();
			Collections.sort(list);
		} catch (Exception e) {
			e.printStackTrace();
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

	public int getCustomerCount() {
		return getCustomerList().size();
	}

	public List<Customer> getCustomerList() {
		if (customerList == null) {
			customerList = loadCustomers();
		}
		return customerList;
	}

	public List<Customer> getFemaleCustomers() {
		List<Customer> list = new ArrayList<Customer>();
		List<Customer> customers = getCustomerList();
		if (customers != null && !customers.isEmpty()) {
			for (Customer customer : customers) {
				if (customer.getMale() == null || !customer.getMale()) {
					list.add(customer);
				}
			}
		}
		return list;
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

	public List<Customer> getMaleCustomers() {
		List<Customer> list = new ArrayList<Customer>();
		List<Customer> customers = getCustomerList();
		if (customers != null && !customers.isEmpty()) {
			for (Customer customer : customers) {
				if (customer.getMale() != null && customer.getMale()) {
					list.add(customer);
				}
			}
		}
		return list;
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
			int year = calendar.get(Calendar.YEAR) - 50;
			Random rand = new Random();
			for (int i = 0; i < 100; i++) {
				String firstName = names.get(rand.nextInt(names.size()));
				String lastName = names.get(rand.nextInt(names.size()));
				calendar.set(Calendar.YEAR, rand.nextInt(25) + year);
				calendar.set(Calendar.DAY_OF_YEAR, rand.nextInt(364) + 1);
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

	public void submitSurveyResponse(ActionEvent event) {
	}

}
