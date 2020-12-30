package chapter6.bean;

import java.util.List;

import javax.ejb.Local;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.schedule.model.ScheduleModel;

import chapter6.model.Country;
import chapter6.model.Customer;

@Local
public interface CustomerBean {

	public List<Country> countryAutocomplete(Object suggestion);

	public List<Country> getCountries();
	
	public Customer getCustomer();

	public List<Customer> getCustomers();

	public ScheduleModel getDailyAppointmentsScheduleModel();

	public String getInformationSource();

	public String[] getInformationSources();

	public List<String> getInterestList();

	public String[] getInterests();

	public ScheduleModel getMonthlyAppointmentsScheduleModel();

	public ScheduleModel getWeeklyAppointmentsScheduleModel();

	public ScheduleModel getWorkweekAppointmentsScheduleModel();

	public void remove();

	public String saveCustomer();

	public void saveInterests(ActionEvent event);

	public void setInformationSource(String informationSource);

	public void setInformationSources(String[] informationSources);

	public void setInterestList(List<String> interestList);

	public void setInterests(String[] interests);

	public void submitSurveyResponse(ActionEvent event);

}