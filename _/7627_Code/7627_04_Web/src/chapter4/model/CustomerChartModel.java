package chapter4.model;

import java.util.List;

import org.apache.myfaces.trinidad.model.ChartModel;

public abstract class CustomerChartModel extends ChartModel {

	protected List<Customer> customers;

	public CustomerChartModel(List<Customer> customers) {
		this.customers = customers;
	}

}
