package chapter4.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerGenderPieChartModel extends CustomerChartModel {

	private enum Gender {
		FEMALE {

			@Override
			public String getLabel() {
				return "Female";
			}
		},

		MALE {

			@Override
			public String getLabel() {
				return "Male";
			}
		};

		abstract String getLabel();
	}

	private Map<Gender, Double> genderData = new HashMap<Gender, Double>();

	public CustomerGenderPieChartModel(List<Customer> customers) {
		super(customers);
		loadCustomerData();
	}

	private void loadCustomerData() {
		for (Customer customer : customers) {
			Gender gender = null;
			if (customer.getMale()) {
				gender = Gender.MALE;
			} else {
				gender = Gender.FEMALE;
			}
			Double count = genderData.get(gender);
			if (count == null) {
				count = 1d;
			} else {
				count++;
			}
			genderData.put(gender, count);
		}
	}

	private List<String> groupLabels;

	private List<String> seriesLabels;

	private List<List<Double>> yvalues;

	@Override
	public List<String> getGroupLabels() {
		if (groupLabels == null) {
			groupLabels = new ArrayList<String>();
			groupLabels.add("Customer Gender");
		}
		return groupLabels;
	}

	@Override
	public List<String> getSeriesLabels() {
		if (seriesLabels == null) {
			seriesLabels = new ArrayList<String>();
			seriesLabels.add("Male");
			seriesLabels.add("Female");
		}
		return seriesLabels;
	}

	@Override
	public List<List<Double>> getYValues() {
		if (yvalues == null) {
			yvalues = new ArrayList<List<Double>>();
			List<Double> genderCount = new ArrayList<Double>();
			yvalues.add(genderCount);
			Double maleCount = genderData.get(Gender.MALE);
			Double femaleCount = genderData.get(Gender.FEMALE);
			genderCount.add(maleCount);
			genderCount.add(femaleCount);
		}
		return yvalues;
	}

}
