package chapter4.model;

import java.util.ArrayList;
import java.util.List;

public class NavigationItem {

	private List<NavigationItem> children = new ArrayList<NavigationItem>();

	private String label;

	private String outcome;

	private String viewId;

	public NavigationItem() {
	}
	
	public NavigationItem(String label, String viewId) {
		this(label, viewId, null);
	}

	public NavigationItem(String label, String viewId, String outcome) {
		this.label = label;
		this.viewId = viewId;
		this.outcome = outcome;
	}

	public String action() {
		return outcome;
	}

	public List<NavigationItem> getChildren() {
		return children;
	}

	public String getLabel() {
		return label;
	}

	public String getOutcome() {
		return outcome;
	}

	public String getViewId() {
		return viewId;
	}

	public void setChildren(List<NavigationItem> children) {
		this.children = children;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

}
