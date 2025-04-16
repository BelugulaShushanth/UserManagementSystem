package com.ums.bean;

import com.ums.config.FacesViewScope;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@Component("csrfToken")
@Scope(FacesViewScope.NAME)
public class CSRFToken {

    private String token;

    public void setToken(String token){
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        this.token = csrfToken != null ? csrfToken.getToken() : "";
    }
    public String getToken() {
        return this.token;
    }

}
