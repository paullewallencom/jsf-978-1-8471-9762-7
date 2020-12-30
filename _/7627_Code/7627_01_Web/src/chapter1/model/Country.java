package chapter1.model;

import java.io.Serializable;

/**
 * This class represents a country in our application's domain model.
 * 
 * @author Ian
 * 
 */
public class Country implements Comparable<Country>, Serializable {

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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Country)) {
			return false;
		}
		Country other = (Country) obj;
		return this.name.equals(other.name);
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		if (name != null) {
			return name.hashCode() * 37;
		} else {
			return super.hashCode();
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
