/*
 * JBoss, Home of Professional Open Source
 * Copyright 2016, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.quickstart.jaxrs;

import java.security.Principal;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.keycloak.quickstart.jaxrs.ejb.TestEJB;

@Path("/")
public class Resource {

    @Context
    private HttpServletRequest request;

    @EJB
    private TestEJB ejb;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("public")
    public Message getPublic(@Context HttpHeaders header, @Context HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return new Message("public - " + getPrincipalName() + " - " + ejb.getMessage());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("secured")
    public Message getSecured() {
        return new Message("secured - " + getPrincipalName() + " - " + ejb.getMessage());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    public Message getAdmin() {
        return new Message("admin - " + getPrincipalName() + " - " + ejb.getMessage());
    }

    private String getPrincipalName() {
        Principal p = request.getUserPrincipal();
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
