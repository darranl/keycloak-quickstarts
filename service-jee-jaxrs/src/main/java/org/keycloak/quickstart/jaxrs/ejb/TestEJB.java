package org.keycloak.quickstart.jaxrs.ejb;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless
public class TestEJB {

    @Resource
    private SessionContext sessionContext;

    public String getMessage() {
        new RuntimeException("Just a test").printStackTrace();
        return "This is an EJB - " + getPrincipalName();
    }

    private String getPrincipalName() {
        Principal p = sessionContext.getCallerPrincipal();
        if (p != null) {
            String name = p.getName();
            if (name.length() > 5) {
                name = name.substring(0,5) + "...";
            }
            return p.getClass().getSimpleName() + "(" + name + ")";
        } else {
            return "null";
        }
    }
}
