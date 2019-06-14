package org.dmp.mongo.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.dmp.mongo.audit.Audited;
import org.dmp.mongo.db.MongoWithMongojackMapping;
import org.dmp.mongo.db.PartDAO;
import org.dmp.mongo.model.part.Part;
import org.dmp.mongo.model.part.PartValidation;

@RequestScoped
@Audited
@Path("/parts")
public class PartsRestService {
    
    @Inject
    private transient Logger log;
    
    @Inject @MongoWithMongojackMapping
    private PartDAO partDao;
    
    @POST
    @Path("/part/{number}")
    @PartValidation
    public void createPart(@PathParam("number") String number) {
      Part part = new Part(number);
      part.setTitle("Widget");
      part.setDescription("Some random widget");
      
      log.debug("Adding part " + number + " to Mongo");
      partDao.add(part);
    }
    
    @DELETE
    @Path("/part/{number}")
    public void deletePart(@PathParam("number") String number) {
      log.debug("Deleting part " + number + " from Mongo");
      partDao.deleteByPartNumber(number);
    }
    
    @GET
    @Path("/part/{number}")
    public Part getPart(@PathParam("number") String number) {
      log.debug("Retrieving part " + number + " from Mongo");
      return partDao.retrieveByPartNumber(number);
    }
    
    @PUT
    @Path("/part")
    public void updatePart(@PathParam("part") Part part) {
      log.debug("Updating part " + part.getNumber() + " in Mongo");
      partDao.update(part);
    }
    

}
