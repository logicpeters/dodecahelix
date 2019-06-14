package org.dmp.aspectj.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

@Path("greet")
public class GreeterRestService {
    
    private static final Logger logger = Logger.getLogger(GreeterRestService.class);

    @GET
    @Path("{name}")
    public String greetRestCall(@PathParam("name") final String name) {
        logger.info("Rest call for greeting " + name + " (" + this.getClass().getName() + ")");
        return "Greetings " + name;
    }
}
