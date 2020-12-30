package chapter4.bean;

import org.apache.myfaces.trinidad.model.ChartModel;

import chapter4.model.CustomerAgeBarChartModel;
import chapter4.model.CustomerCountryPieChartModel;
import chapter4.model.CustomerGenderPieChartModel;

public class ChartBean {

	private ChartModel customerAgeBarChartModel;

	private CustomerBean customerBean;

	private ChartModel customerCountryPieChartModel;

	private ChartModel customerGenderPieChartModel;

	public ChartModel getCustomerAgeBarChartModel() {
		if (customerAgeBarChartModel == null) {
			customerAgeBarChartModel = new CustomerAgeBarChartModel(customerBean.getCustomerList());
		}
		return customerAgeBarChartModel;
	}

	public ChartModel getCustomerCountryPieChartModel() {
		if (customerCountryPieChartModel == null) {
			customerCountryPieChartModel = new CustomerCountryPieChartModel(customerBean.getCustomerList());
		}
		return customerCountryPieChartModel;
	}

	public ChartModel getCustomerGenderPieChartModel() {
		if (customerGenderPieChartModel == null) {
			customerGenderPieChartModel = new CustomerGenderPieChartModel(customerBean.getCustomerList());
		}
		return customerGenderPieChartModel;
	}

	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}

}
