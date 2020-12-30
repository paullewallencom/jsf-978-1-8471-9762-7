package chapter4.bean;

import java.awt.Color;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.context.RequestContext;

public class DialogBean {

	private Color selectedColor;

	public void cancel(ActionEvent event) {
		RequestContext.getCurrentInstance().returnFromDialog(null, null);
	}

	public void closeColorDialog(ActionEvent event) {
		RequestContext.getCurrentInstance().returnFromDialog(selectedColor, null);
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

}
