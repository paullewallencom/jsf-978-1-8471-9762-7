package chapter5.model;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FacesUtils {

     public static ServletContext getServletContext() {
        return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
     }
 
     public static ExternalContext getExternalContext() {
         FacesContext fc = FacesContext.getCurrentInstance();
         return fc.getExternalContext();
     }
 
     public static HttpSession getHttpSession(boolean create) {
         return (HttpSession) FacesContext.getCurrentInstance().
                 getExternalContext().getSession(create);
     }
 

     public static Object getManagedBean(String beanName) {
 
         return getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());
     }


     public static void resetManagedBean(String beanName) {
         getValueBinding(getJsfEl(beanName)).setValue(FacesContext.getCurrentInstance(), null);
     }
 

     public static void setManagedBeanInSession(String beanName, Object managedBean) {
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(beanName, managedBean);
     }
 

     public static String getRequestParameter(String name) {
         return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
     }

    
    public static void addInfoMessage(String msg) {
         addInfoMessage(null, msg);
     }
 
    

     public static void addInfoMessage(String clientId, String msg) {
         FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
     }
 

     public static void addErrorMessage(String msg) {
         addErrorMessage(null, msg);
     }
 

     public static void addErrorMessage(String clientId, String msg) {
         FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }

    private static Application getApplication() {
         ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        return appFactory.getApplication();
     }
 
     private static ValueBinding getValueBinding(String el) {
         return getApplication().createValueBinding(el);
     }

    private static HttpServletRequest getServletRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
     }
 
    private static Object getElValue(String el) {
         return getValueBinding(el).getValue(FacesContext.getCurrentInstance());
     }

     private static String getJsfEl(String value) {
        return "#{" + value + "}";
     }
 }