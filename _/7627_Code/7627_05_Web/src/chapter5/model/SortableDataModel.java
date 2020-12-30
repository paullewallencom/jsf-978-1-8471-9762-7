package chapter5.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.icesoft.faces.component.ext.RowSelectorEvent;

public class SortableDataModel<T extends Selectable<?>> {

	private boolean ascending = true;

	private String columnName = "";

	private boolean enhancedMultiple;

	private boolean lastAscending = true;

	private String lastColumnName = "";

	private List<T> list;

	private String mode = "single";

	private boolean multiple;

	private List<T> selectedValues = new ArrayList<T>();

	public SortableDataModel(List<T> list, String initialSortColumn) {
		this.list = list;
		this.columnName = initialSortColumn;
		checkState();
	}

	public void checkState() {
		if (!lastColumnName.equals(columnName)) {
			System.out.println("Sorting list by column: " + columnName);
			Collections.sort(list, new PropertyComparator(columnName));
			lastColumnName = columnName;
		}
		if (lastAscending != ascending) {
			System.out.println("Ordering list (ascending=" + ascending + ")");
			Collections.reverse(list);
			lastAscending = ascending;
		}
	}

	public String getColumnName() {
		return columnName;
	}

	public List<T> getList() {
		return list;
	}

	public String getMode() {
		return mode;
	}

	public List<T> getSelectedValues() {
		return selectedValues;
	}

	public boolean isAscending() {
		return ascending;
	}

	public boolean isEnhancedMultiple() {
		return enhancedMultiple;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void rowSelectionListener(RowSelectorEvent event) {
		selectedValues.clear();
		for (T selectable : list) {
			if (selectable.isSelected()) {
				selectedValues.add(selectable);
			}
		}
	}

	public void setAscending(boolean ascending) {
		this.lastAscending = this.ascending;
		this.ascending = ascending;
	}

	public void setColumnName(String columnName) {
		this.lastColumnName = this.columnName;
		this.columnName = columnName;
	}

	public void setEnhancedMultiple(boolean multipleEnhanced) {
		this.enhancedMultiple = multipleEnhanced;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void setMode(String mode) {
		this.mode = mode;
		if ("single".equals(mode)) {
			enhancedMultiple = false;
			multiple = false;
		} else if ("multiple".equals(mode)) {
			enhancedMultiple = false;
			multiple = true;
		} else if ("enhanced".equals(mode)) {
			enhancedMultiple = true;
			multiple = false;
		}
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
}
