package chapter2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import chapter2.model.Country;

public class CountryConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Country country = null;
		if (value != null && !value.equals("")) {
			country = new Country(value);
		}
		return country;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String string = null;
		if (value instanceof String) {
			string = (String) value;
		} else if (value instanceof Country) {
			string = ((Country) value).getName();
		}
		return string;
	}

}
