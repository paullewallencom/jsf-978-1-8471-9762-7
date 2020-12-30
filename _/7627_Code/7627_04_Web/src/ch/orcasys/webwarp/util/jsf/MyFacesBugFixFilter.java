package ch.orcasys.webwarp.util.jsf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MyFaces Bug Fix: InputDate popup crashes when using extension mapping. <a
 * href="https://issues.apache.org/jira/browse/TRINIDAD-119?page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#action_12516534"
 * >Jira Bug Trinidad 119</a>
 * 
 * <p>
 * This filter fixes the Problem: when a url containing '__ADFv__' arrives
 * whithout a '.xhtml' it fixes the url and sends a redirect to the browser.
 * <p>
 * <p>
 * Map this filter with url-pattern: /*
 * </p>
 * <p>
 * 07/29/2009 - Added support for detecting actual JSF extension suffix used by
 * the application. Also, we are now forwarding instead of redirecting to ensure
 * the original HTTP request headers are preserved, otherwise Trinidad does not
 * think an Ajax request is in progress. (Ian)
 * </p>
 * 
 * 
 * @version $Id: $
 * @author mos
 * @author Ian
 * 
 */
public class MyFacesBugFixFilter implements Filter {

	private String extension;

	/**
	 * Dummy
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String requestURI = httpRequest.getRequestURI();
			if (requestURI != null && requestURI.contains("__ADFv__") && !requestURI.contains(extension)) {
				String context = httpRequest.getContextPath();
				String replacedURI = requestURI.replace("__ADFv__", "__ADFv__" + extension + "?" + httpRequest.getQueryString());
				replacedURI = replacedURI.replaceFirst("^" + context, "");
				RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(replacedURI);
				dispatcher.forward(request, response);
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	/**
	 * Dummy
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		extension = arg0.getServletContext().getInitParameter("javax.faces.DEFAULT_SUFFIX");

	}

}
