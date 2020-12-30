package chapter1.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a customer in our application's domain model.
 * 
 * @author Ian
 * 
 */
public class Customer implements Comparable<Customer>, Serializable {

	private static final long serialVersionUID = 1L;

	private Date birthDate;

	private Country countryOfOrigin;

	private String emailAddress;

	private String firstName;

	private Set<String> interests = new HashSet<String>(0);

	private String lastName;

	private Boolean male;

	private String phoneNumber;

	private SatisfactionLevel satisfactionLevel;

	public int compareTo(Customer o) {
		String lastFirst1 = getFullName();
		String lastFirst2 = o.getFullName();
		return lastFirst1.compareTo(lastFirst2);
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public Country getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFullName() {
		return this.lastName + ", " + this.firstName;
	}

	public Set<String> getInterests() {
		return interests;
	}

	public String getLastName() {
		return lastName;
	}

	public Boolean getMale() {
		return male;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public SatisfactionLevel getSatisfactionLevel() {
		return satisfactionLevel;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setCountryOfOrigin(Country countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setInterests(Set<String> interests) {
		this.interests = interests;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMale(Boolean male) {
		this.male = male;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setSatisfactionLevel(SatisfactionLevel satisfactionLevel) {
		this.satisfactionLevel = satisfactionLevel;
	}

}
