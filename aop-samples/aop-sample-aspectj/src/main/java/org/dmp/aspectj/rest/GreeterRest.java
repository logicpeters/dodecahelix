package org.dmp.aspectj.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

@Path("greet")
public class GreeterRest {
    
    private static final Logger logger = Logger.getLogger(GreeterRest.class);

    @GET
    @Path("{name}")
    public String greet(@PathParam("name") final String name) {
        logger.info("Rest call for greeting " + name);
        return "Greetings " + name;
    }
}
