package chapter5.bean;

import com.icesoft.faces.component.ContextActionEvent;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;
import com.icesoft.faces.context.effects.Pulsate;
import com.icesoft.faces.context.effects.Shake;

import chapter5.model.FacesUtils;

import javax.faces.event.ActionEvent;
import java.io.Serializable;


public class MenuPopupBean implements Serializable {
    /**
     * Shake event type
     */
    public static final String SHAKE_EFFECT = "shake";
    /**
     * Pulsate event type
     */
    public static final String PULSATE_EFFECT = "pulsate";
    /**
     * Highlight effect type
     */
    public static final String HIGHLIGHT_EFFECT = "highlight";

    /**
     * Executes a menu effect specified by the request param "effectType".
     *
     * @param event jsf action event.
     */
    public void executeMenuEffect(ActionEvent event) {
        // get effect type from the request param effectType
        String effectType = FacesUtils.getRequestParameter("effectType");

        if (event instanceof ContextActionEvent) {
            // ContextActionEvent getTarget allows us to find out which
            // component initiated the menupopup action.  
            ContextActionEvent se = (ContextActionEvent) event;
            HtmlPanelGroup targetGroup = (HtmlPanelGroup) se.getTarget();
            targetGroup.setEffect(effectFactory(effectType));
        }

    }

    /**
     * Utility method for getting one of three types of effects based on the key
     * words,  SHAKE_EFFECT, PULSATE_EFFECT, HIGHLIGHT_EFFECT.
     *
     * @param effects name of effect to create. Should be one of the following:
     *                SHAKE_EFFECT, PULSATE_EFFECT, HIGHLIGHT_EFFECT
     * @return valid effect as described by the paramater, if no match is found
     *         the Highlight Effect is returned.
     */
    private static Effect effectFactory(String effects) {
        if (SHAKE_EFFECT.equals(effects)) {
            return new Shake();
        } else if (PULSATE_EFFECT.equals(effects)) {
            Pulsate pulsate = new Pulsate();
            pulsate.setDuration(0.75f);
            return pulsate;
        } else {
            return new Highlight("#fda505");
        }
    }
}
