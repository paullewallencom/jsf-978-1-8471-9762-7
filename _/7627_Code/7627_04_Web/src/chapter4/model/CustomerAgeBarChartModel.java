package chapter4.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerAgeBarChartModel extends CustomerChartModel {

	private enum AgeGroup {
		UNDER_TWENTY {
			@Override
			public int[] getRange() {
				return new int[] { 0, 20 };
			}

			@Override
			public String getLabel() {
				return "Under Twenty";
			}
		},
		TWENTY_TO_THIRTY {
			@Override
			public int[] getRange() {
				return new int[] { 20, 30 };
			}

			@Override
			public String getLabel() {
				return "Twenty to Thirty";
			}
		},

		THIRTY_TO_FORTY {
			@Override
			public int[] getRange() {
				return new int[] { 30, 40 };
			}

			@Override
			public String getLabel() {
				return "Thirty to Forty";
			}
		},
		FORTY_TO_FIFTY {
			@Override
			public int[] getRange() {
				return new int[] { 40, 50 };
			}

			@Override
			public String getLabel() {
				return "Forty to Fifty";
			}

		},
		FIFTY_TO_SIXTY {
			@Override
			public int[] getRange() {
				return new int[] { 50, 60 };
			}

			@Override
			public String getLabel() {
				return "Fifty to Sixty";
			}
		},
		OVER_SIXTY {
			@Override
			public int[] getRange() {
				return new int[] { 60, 120 };
			}

			@Override
			public String getLabel() {
				return "Over Sixty";
			}
		};

		abstract int[] getRange();

		abstract String getLabel();
	}

	private Map<AgeGroup, Double> customerData = new HashMap<AgeGroup, Double>();

	public CustomerAgeBarChartModel(List<Customer> customers) {
		super(customers);
		loadCustomerData();
	}

	private void loadCustomerData() {
		for (AgeGroup ageGroup : AgeGroup.values()) {
			for (Customer customer : customers) {
				int age = customer.getAge();
				int[] range = ageGroup.getRange();
				int min = range[0];
				int max = range[1];
				if (age < max && age >= min) {
					Double count = customerData.get(ageGroup);
					if (count == null) {
						count = 1d;
					} else {
						count++;
					}
					customerData.put(ageGroup, count);
				}
			}
		}
	}

	private List<String> groupLabels;

	private List<String> seriesLabels;

	private List<List<Double>> yvalues;

	@Override
	public List<String> getGroupLabels() {
		if (groupLabels == null) {
			groupLabels = new ArrayList<String>();
			for (AgeGroup ageGroup : AgeGroup.values()) {
				groupLabels.add(ageGroup.getLabel());
			}
		}
		return groupLabels;
	}

	@Override
	public List<String> getSeriesLabels() {
		if (seriesLabels == null) {
			seriesLabels = new ArrayList<String>();
			seriesLabels.add("Customer Age Groups");
		}
		return seriesLabels;
	}

	@Override
	public List<List<Double>> getYValues() {
		if (yvalues == null) {
			yvalues = new ArrayList<List<Double>>();
			for (AgeGroup ageGroup : customerData.keySet()) {
				Double count = customerData.get(ageGroup);
				List<Double> countryCount = new ArrayList<Double>();
				countryCount.add(count);
				yvalues.add(countryCount);
			}
		}
		return yvalues;
	}

}
