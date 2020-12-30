package chapter5.bean;


import javax.faces.event.ActionEvent;
import java.io.Serializable;

/**
 * <p>The PopupBean class is the backing bean that manages the Popup Panel
 * state.</p>
 * <p>This includes the modal and draggable user configurable message, as well
 * as the rendered and visibility state.</p>
 */
public class PopupBean implements Serializable {
    // user entered messages for both dialogs
    private String draggableMessage = "A Draggable Message";
    private String modalMessage = "A Modal Message" ;
    // render flags for both dialogs
    private boolean draggableRendered = false;
    private boolean modalRendered = false;
    // if we should use the auto centre attribute on the draggable dialog
    private boolean autoCentre = false;

    public String getDraggableMessage() {
        return draggableMessage;
    }

    public void setDraggableMessage(String draggableMessage) {
        this.draggableMessage = draggableMessage;
    }

    public String getModalMessage() {
        return modalMessage;
    }

    public void setModalMessage(String modalMessage) {
        this.modalMessage = modalMessage;
    }

    public boolean isDraggableRendered() {
        return draggableRendered;
    }

    public void setDraggableRendered(boolean draggableRendered) {
        this.draggableRendered = draggableRendered;
    }

    public boolean getModalRendered() {
        return modalRendered;
    }

    public void setModalRendered(boolean modalRendered) {
        this.modalRendered = modalRendered;
    }
    
    public boolean getAutoCentre() {
        return autoCentre;
    }

    public void setAutoCentre(boolean autoCentre) {
        this.autoCentre = autoCentre;
    }

    public String getDetermineDraggableButtonText() {
        return "Show";
    }

    public String getDetermineModalButtonText() {
        return "Show";
    }

    public void toggleDraggable(ActionEvent event) {
        draggableRendered = !draggableRendered;
    }

    public void toggleModal(ActionEvent event) {
        modalRendered = !modalRendered;
    }
}
