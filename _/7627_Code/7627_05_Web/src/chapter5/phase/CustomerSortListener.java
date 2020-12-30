package chapter5.phase;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import chapter5.model.SortableDataModel;

public class CustomerSortListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	public void afterPhase(PhaseEvent arg0) {
	}

	public void beforePhase(PhaseEvent arg0) {
		FacesContext context = arg0.getFacesContext();
		ELContext el = context.getELContext();
		ExpressionFactory factory = context.getApplication().getExpressionFactory();
		ValueExpression expr = factory.createValueExpression(el, "#{customerBean.sortableCustomerModel}", SortableDataModel.class);
		SortableDataModel<?> model = (SortableDataModel<?>) expr.getValue(el);
		model.checkState();
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
