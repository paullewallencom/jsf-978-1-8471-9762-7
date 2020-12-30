package chapter6.bean.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.myfaces.custom.schedule.model.DefaultScheduleEntry;
import org.apache.myfaces.custom.schedule.model.ScheduleModel;
import org.apache.myfaces.custom.schedule.model.SimpleScheduleModel;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import chapter6.bean.CustomerBean;
import chapter6.model.Country;
import chapter6.model.Customer;
import chapter6.model.Queries;

@Stateful
@Name("customerBean")
@Scope(ScopeType.CONVERSATION)
public class CustomerBeanImpl implements CustomerBean {

	private Customer customer;

	private List<Customer> customerList;

	@In
	private EntityManager em;

	@In
	private FacesMessages facesMessages;

	private String informationSource;

	private String[] informationSources;

	private List<String> interestList = new ArrayList<String>();

	private String[] interests;

	@Logger
	protected Log logger;

	private ScheduleModel monthlyScheduleModel;

	@SuppressWarnings("unchecked")
	public List<Country> countryAutocomplete(Object suggestion) {
		List<Country> list = null;
		String text = (String) suggestion;
		if (text != null && text.length() > 0) {
			Query query = em.createNamedQuery(Queries.COUNTRIES_LIKE);
			query.setParameter(1, text + "%");
			list = query.getResultList();
		}		
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Country> getCountries() {
		List<Country> list = em.createNamedQuery(Queries.ALL_COUNTRIES).getResultList();
		return list;
	}

	public Customer getCustomer() {
		if (customer == null) {
			customer = new Customer();
		}
		return customer;
	}

	@SuppressWarnings("unchecked")
	public List<Customer> getCustomers() {
		if (customerList == null) {
			customerList = em.createNamedQuery(Queries.ALL_CUSTOMERS).getResultList();
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
		if (interestList == null || interestList.isEmpty()) {
			interestList.add("Java");
			interestList.add("Web Design");
			interestList.add("Architecture");
			interestList.add("Database");
			interestList.add("GUI");
		}
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
		List<Customer> customers = getCustomers();
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

	@Remove
	@Destroy
	public void remove() {
	}

	public String saveCustomer() {
		em.persist(customer);
		facesMessages.add("Thank you. Your registration was successful.");
		return "success";
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
