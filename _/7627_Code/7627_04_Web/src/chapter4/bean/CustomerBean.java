package chapter4.bean;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.myfaces.custom.schedule.model.DefaultScheduleEntry;
import org.apache.myfaces.custom.schedule.model.ScheduleModel;
import org.apache.myfaces.custom.schedule.model.SimpleScheduleModel;
import org.apache.myfaces.trinidad.event.ReturnEvent;

import chapter4.model.Country;
import chapter4.model.Customer;

public class CustomerBean extends AbstractBean {

	private List<SelectItem> countrySelectItems;

	private Customer customer = new Customer();

	private List<Customer> customerList;

	private String informationSource;

	private String[] informationSources;

	private ScheduleModel monthlyScheduleModel;

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

	public List<Customer> getCustomerList() {
		if (customerList == null) {
			customerList = loadCustomers();
		}
		return customerList;
	}

	public ScheduleModel getDailyAppointmentsScheduleModel() {
		return getScheduleModel(ScheduleModel.DAY, 3);
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

	public ScheduleModel getMonthlyAppointmentsScheduleModel() {
		if (monthlyScheduleModel == null) {
			monthlyScheduleModel = getScheduleModel(ScheduleModel.MONTH, 20);
		}
		return monthlyScheduleModel;
	}

	private ScheduleModel getScheduleModel(int mode, int appointments) {
		ScheduleModel model = new SimpleScheduleModel();
		model.setMode(mode);
		List<Customer> customers = getCustomerList();
		Collections.shuffle(customers);
		Calendar calendar = Calendar.getInstance();
		model.setSelectedDate(calendar.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Random random = new Random();
		int maxPerDay = 3;
		int dayIncrement = 1;
		switch (mode) {
		case ScheduleModel.DAY:
			dayIncrement = 0;
			break;
		case ScheduleModel.WEEK:
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			break;
		case ScheduleModel.WORKWEEK:
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			break;
		case ScheduleModel.MONTH:
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			dayIncrement = 3;
			maxPerDay = 1;
			break;
		}
		int count = 0;
		int max = maxPerDay;
		for (int i = 0; i < appointments; i++) {
			if (count == max) {
				max = random.nextInt(maxPerDay);
				if (max == 0) {
					max = 1;
				}
				int days = random.nextInt(dayIncrement);
				if (days == 0) {
					days = 1;
				}
				int hour = random.nextInt(15);
				if (hour < 8) {
					hour = 8;
				}
				calendar.roll(Calendar.DATE, days);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				count = 0;
			}
			Customer customer = customers.get(i);
			int rand = random.nextInt(4);
			if (rand == 0) {
				rand = 1;
			}
			calendar.roll(Calendar.HOUR_OF_DAY, rand);
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			while (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
				calendar.roll(Calendar.DATE, true);
				day = calendar.get(Calendar.DAY_OF_WEEK);
			}
			Date start = calendar.getTime();
			rand = random.nextInt(4);
			if (rand == 0) {
				rand = 1;
			}
			calendar.roll(Calendar.HOUR_OF_DAY, rand);
			Date end = calendar.getTime();
			DefaultScheduleEntry entry = new DefaultScheduleEntry();
			entry.setId(UUID.randomUUID().toString());
			entry.setStartTime(start);
			entry.setEndTime(end);
			entry.setTitle("Meeting with " + customer.getFirstName() + " " + customer.getLastName());
			entry.setSubtitle("Follow-up appointment");
			entry.setDescription("Meeting to discuss project opportunity.");
			model.addEntry(entry);
			count++;
		}
		return model;
	}

	public ScheduleModel getWeeklyAppointmentsScheduleModel() {
		return getScheduleModel(ScheduleModel.WEEK, 25);
	}

	public ScheduleModel getWorkweekAppointmentsScheduleModel() {
		return getScheduleModel(ScheduleModel.WORKWEEK, 25);
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

	public void handleColorSelection(ReturnEvent event) {
		Object result = event.getReturnValue();
		System.out.println("Customer selected: " + result);
		if (result instanceof Color) {
			Color color = (Color) result;
			customer.setFavoriteColor(color);
		}
	}

}
