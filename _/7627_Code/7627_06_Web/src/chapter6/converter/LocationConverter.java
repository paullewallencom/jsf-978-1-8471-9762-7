package chapter6.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.jboss.seam.Component;

import chapter6.bean.MapBean;
import chapter6.model.Location;

/**
 * This converter class handles Location object conversion. Because the Location
 * class is not a JPA entity, we are using a custom converter instead of Seam's
 * built-in JPA entity converter.
 * 
 * @author Ian
 * 
 */
public class LocationConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Object result = null;
		if (value != null) {
			MapBean bean = (MapBean) Component.getInstance("mapBean");
			result = bean.findLocation(value);
		}
		return result;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String result = null;
		if (value instanceof Location) {
			result = ((Location) value).getId();
		} else {
			result = String.valueOf(value);
		}
		return result;
	}

}
