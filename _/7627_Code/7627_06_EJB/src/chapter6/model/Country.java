package chapter6.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class represents a country in our application's domain model.
 * 
 * @author Ian
 * 
 */
@Entity
@Table(catalog = "jsfbook")
public class Country extends AbstractEntity implements Comparable<Country>, Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public Country() {
	}

	public Country(String name) {
		this.name = name;
	}

	public int compareTo(Country o) {
		return this.name.compareTo(o.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
