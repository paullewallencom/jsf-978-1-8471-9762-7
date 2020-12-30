package chapter5.model;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Comparator;

public class PropertyComparator implements Comparator<Object> {

	private String propertyName;

	public PropertyComparator(String propertyName) {
		this.propertyName = propertyName;
	}

	@SuppressWarnings("unchecked")
	public int compare(Object o1, Object o2) {
		int result = 0;
		try {
			Object value1 = getPropertyValue(o1, propertyName);
			Object value2 = getPropertyValue(o2, propertyName);
			// send nulls to the bottom of the list
			if (value1 == null && value2 == null) {
				result = 0;
			} else if (value1 != null && value2 == null) {
				result = -1;
			} else if (value1 == null && value2 != null) {
				result = 1;
			} else {
				Comparable c1 = (value1 instanceof Comparable ? (Comparable) value1 : value1.toString());
				Comparable c2 = (value1 instanceof Comparable ? (Comparable) value2 : value2.toString());
				result = c1.compareTo(c2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method uses reflection to get the value of the property specified in
	 * the sort column.
	 * 
	 * @param object
	 *            The object to introspect.
	 * @param propertyName
	 *            The name of the property to evaluate.
	 * @return The value of the property.
	 * @throws Exception
	 */
	private Object getPropertyValue(Object object, String propertyName) throws Exception {
		Object value = null;
		BeanInfo info = Introspector.getBeanInfo(object.getClass());
		PropertyDescriptor[] properties = info.getPropertyDescriptors();
		boolean found = false;
		for (PropertyDescriptor property : properties) {
			if (property.getName().equals(propertyName)) {
				Method getter = property.getReadMethod();
				if (getter == null) {
					throw new IllegalArgumentException("Property '" + propertyName + "' is not readable in class " + object.getClass().getName());
				}
				found = true;
				value = getter.invoke(object);
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException("Property '" + propertyName + "' not found in class " + object.getClass().getName());
		}
		return value;
	}

}
