package chapter4.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerCountryPieChartModel extends CustomerChartModel {

	private Map<Country, Double> countryData = new HashMap<Country, Double>();

	public CustomerCountryPieChartModel(List<Customer> customers) {
		super(customers);
		loadCustomerData();
	}

	private void loadCustomerData() {
		for (Customer customer : customers) {
			Country country = customer.getCountryOfOrigin();
			Double count = countryData.get(country);
			if (count == null) {
				count = 1d;
			} else {
				count++;
			}
			countryData.put(country, count);
		}
	}

	private List<String> groupLabels;

	private List<String> seriesLabels;

	private List<List<Double>> yvalues;

	@Override
	public List<String> getGroupLabels() {
		if (groupLabels == null) {
			groupLabels = new ArrayList<String>();
			groupLabels.add("Customer Countries");
		}
		return groupLabels;
	}

	@Override
	public List<String> getSeriesLabels() {
		if (seriesLabels == null) {
			seriesLabels = new ArrayList<String>();
			for (Customer customer : customers) {
				Country country = customer.getCountryOfOrigin();
				String name = country.getName();
				if (!seriesLabels.contains(name)) {
					seriesLabels.add(name);
				}
			}
			Collections.sort(seriesLabels);
		}
		return seriesLabels;
	}

	@Override
	public List<List<Double>> getYValues() {
		if (yvalues == null) {
			yvalues = new ArrayList<List<Double>>();
			List<Double> countryCount = new ArrayList<Double>();
			yvalues.add(countryCount);
			for (Country country : countryData.keySet()) {
				Double count = countryData.get(country);
				countryCount.add(count);				
			}
		}
		return yvalues;
	}

}
