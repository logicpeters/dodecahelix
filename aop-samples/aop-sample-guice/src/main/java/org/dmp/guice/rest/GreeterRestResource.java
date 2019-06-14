package org.dmp.guice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.dmp.guice.aop.Audited;

@Path("greet")
public class GreeterRestResource {
    
    private static final Logger logger = Logger.getLogger(GreeterRestResource.class);

    @GET
    @Path("{name}")
    @Audited
    public String greet(@PathParam("name") final String name) {
        logger.info("Rest call for greeting " + name);
        return "Greetings " + name;
    }
    
}
