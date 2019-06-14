package org.dmp.spring.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Path("rest/greet")
@Controller
public class GreeterRestService {
    
    private static final Logger logger = Logger.getLogger(GreeterRestService.class);
    
    @Autowired
    private Greeting logGreeter;

    @GET
    @Path("{name}")
    public String greetRestCall(@PathParam("name") final String name) {
        logger.info("Rest call for greeting " + name);
     
        // Here's an example of a Spring autowired bean being called
        logGreeter.greet();
        return "Greetings " + name;
    }
}
