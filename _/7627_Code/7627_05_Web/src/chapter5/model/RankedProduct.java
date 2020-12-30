package chapter5.model;

import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;

/**
 * Product subclass that adds support for ordering and effects in the UI.
 */
public class RankedProduct extends Product {

	private static final long serialVersionUID = 1L;

	private Effect effect = new Highlight("#fda505");

	private int rank;

	public RankedProduct() {
		effect.setFired(true);
	}

	public Effect getEffect() {
		return effect;
	}

	public String getNameWithRank() {
		return rank + ". " + this.getName();
	}

	public int getRank() {
		return rank;
	}

	public void setEffect(Effect effect) {
		this.effect = effect;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}