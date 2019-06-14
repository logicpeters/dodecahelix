package org.dmp.mongo.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.dmp.mongo.api.Greeting;
import org.dmp.mongo.audit.Audited;

@RequestScoped
@Audited
@Path("/greet")
public class GreeterRestService {
    
  @Inject
  Greeting greeting;
 
  @Inject 
  private transient Logger log;
  
  @GET
  @Path("{name}")
  public String greetSomeone(@PathParam("name") String name) {
    log.debug("Greeter rest call made.");
    return greeting.greetSomeone(name);
  }
  
}
