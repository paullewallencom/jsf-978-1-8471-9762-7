package chapter1.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

public class BackingBean {

	private Date date;

	private String name;
	
	private Integer number;

	private String password;

	private Double percentage;

	private Float price;
	
	private Boolean rememberMe;

	private String word;

	private List<String> words = new ArrayList<String>();

	public void addWord(ActionEvent event) {
		if (word != null && word.length() > 0) {
			words.add(word);
		}
	}

	public Date getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public Integer getNumber() {
		return number;
	}

	public String getPassword() {
		return password;
	}

	public Double getPercentage() {
		return percentage;
	}

	public Float getPrice() {
		return price;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public Date getToday() {
		return new Date();
	}

	public String getWord() {
		return word;
	}

	public List<String> getWords() {
		return words;
	}

	public void removeWord(ActionEvent event) {
		words.remove(word);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

	public void validateBirthDate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
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
