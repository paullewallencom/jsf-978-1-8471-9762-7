package chapter5.model;

public interface Selectable<T> extends Comparable<T> {

	public boolean isSelected();

	public void setSelected(boolean selected);

}
