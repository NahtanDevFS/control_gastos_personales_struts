package com.misgastos.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.Map;

public class AuthenticationInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> session = invocation.getInvocationContext().getSession();

        if (session.get("usuarioLogueado") == null) {
            return Action.LOGIN;
        } else {
            return invocation.invoke();
        }
    }
}