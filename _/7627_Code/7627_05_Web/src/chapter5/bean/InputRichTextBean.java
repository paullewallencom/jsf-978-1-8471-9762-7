package chapter5.bean;

import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;

public class InputRichTextBean extends AbstractBean {

	private String toolbarMode = getToolbarModeDefault();

	private String value = "";

	protected Effect valueChangeEffect;

	public InputRichTextBean() {
		valueChangeEffect = new Highlight("#fda505");
		valueChangeEffect.setFired(true);
	}

	public String getToolbarMode() {
		return toolbarMode;
	}

	public String getToolbarModeBasic() {
		return "Basic";
	}

	public String getToolbarModeDefault() {
		return "Default";
	}

	public String getValue() {
		return value;
	}

	public Effect getValueChangeEffect() {
		return valueChangeEffect;
	}

	public void setToolbarMode(String toolbarMode) {
		this.toolbarMode = toolbarMode;
	}

	public void setValue(String value) {
		// highlight backing bean display.
		valueChangeEffect.setFired(false);
		this.value = value;
	}
}
