package com.ums.service;

import org.springframework.stereotype.Service;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

@Service
public class RoutingService {

    public void addMessage(String msg){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public void routeTo(String page){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(FacesContext.getCurrentInstance(), null, page+"?faces-redirect=true");
    }
}
