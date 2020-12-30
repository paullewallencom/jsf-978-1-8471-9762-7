package chapter4.model;

import java.awt.Color;
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

	private Color favoriteColor;

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

	public String getFavoriteColorHexValue() {
		String value = null;
		if (favoriteColor != null) {
			int rgb = favoriteColor.getRGB();
			value = Integer.toHexString((rgb & 0xffffff) | 0x1000000).substring(1);
		}
		return value;
	}

	public int getAge() {
		Date today = new Date();
		double millis = today.getTime() - birthDate.getTime();
		double seconds = millis / 1000d;
		double minutes = seconds / 60d;
		double hours = minutes / 60d;
		double days = hours / 24d;
		double years = days / 365d;
		int age = new Double(Math.floor(years)).intValue();
		return age;
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

	public Color getFavoriteColor() {
		return favoriteColor;
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

	public void setFavoriteColor(Color favoriteColor) {
		this.favoriteColor = favoriteColor;
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
