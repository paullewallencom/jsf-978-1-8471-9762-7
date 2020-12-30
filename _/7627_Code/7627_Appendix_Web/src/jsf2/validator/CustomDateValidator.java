package jsf2.validator;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class CustomDateValidator implements Validator {

	public void validate(FacesContext context, UIComponent component,
			Object object) throws ValidatorException {
		if (object instanceof Date) {
			Date date = (Date) object;
			Calendar calendar = Calendar.getInstance();
			calendar.roll(Calendar.YEAR, -120);
			if (date.before(calendar.getTime())) {
				FacesMessage msg = new FacesMessage();
				msg.setSummary("Invalid birthdate: " + date);
				msg.setDetail("The date entered is more than 120 years ago.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}
	}
}
