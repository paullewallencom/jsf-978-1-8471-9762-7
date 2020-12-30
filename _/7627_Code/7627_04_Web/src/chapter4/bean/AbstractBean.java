package chapter4.bean;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractBean {

	protected String getBaseURL() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) external.getRequest();
		String scheme = request.getScheme();
		String host = request.getServerName();
		int port = request.getServerPort();
		String path = request.getContextPath();
		StringBuilder sb = new StringBuilder();
		sb.append(scheme);
		sb.append("://");
		sb.append(host);
		if (port != 80) {
			sb.append(":");
			sb.append(port);
		}
		sb.append(path);
		return sb.toString();
	}

}
