package com.misgastos.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;

public class LogoutAction extends ActionSupport implements SessionAware {

    private Map<String, Object> session;

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public String execute() throws Exception {
        if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
            ((org.apache.struts2.dispatcher.SessionMap<String, Object>) session).invalidate();
        }
        return SUCCESS;
    }
}