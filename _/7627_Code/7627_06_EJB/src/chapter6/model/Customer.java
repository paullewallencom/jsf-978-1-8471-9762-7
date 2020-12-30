package chapter6.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Past;

/**
 * This class represents a customer in our application's domain model.
 * 
 * @author Ian
 * 
 */
@Entity
@Table(catalog = "jsfbook")
public class Customer extends AbstractEntity implements Comparable<Customer>, Serializable {

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

	@Transient
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

	@Past
	@NotNull
	@Column(nullable = false)
	public Date getBirthDate() {
		return birthDate;
	}

	@NotNull
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "country_id", insertable = true, updatable = true, nullable = true)
	public Country getCountryOfOrigin() {
		return countryOfOrigin;
	}

	@NotNull
	@Email
	@Column(nullable = false)
	public String getEmailAddress() {
		return emailAddress;
	}

	public Color getFavoriteColor() {
		return favoriteColor;
	}

	@NotNull
	@NotEmpty
	@Column(nullable = false)
	public String getFirstName() {
		return firstName;
	}

	@Transient
	public String getFullName() {
		return this.lastName + ", " + this.firstName;
	}

	@Transient
	public Set<String> getInterests() {
		return interests;
	}

	@NotNull
	@NotEmpty
	@Column(nullable = false)
	public String getLastName() {
		return lastName;
	}

	@NotNull
	@Column(nullable = false)
	public Boolean getMale() {
		return male;
	}

	@NotNull
	@NotEmpty
	@Column(nullable = false)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Transient
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
