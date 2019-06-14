package org.dmp.mongo.db;

import java.net.UnknownHostException;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.dmp.mongo.model.part.Part;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 *   A DAO which manually handles the mapping between the Part and MongoDB objects
 *   
 * @author david.peters
 *
 */
@MongoWithManualMapping
public class MongoPartDAO implements PartDAO {
    
    @Inject 
    private transient Logger log;

    private DBCollection partCollection;
    
    public String DB_PART_COLLECTION_NAME = "parts";
    
    private static final String PART_NUMBER_MONGO_IDX = "part_number";
    private static final String PART_TITLE_MONGO_IDX = "part_title";
    private static final String PART_DESCRIPTION_MONGO_IDX = "part_description";
    
    public @Inject MongoPartDAO(MongoSettings settings) {
        try {
            MongoClient mongo = new MongoClient(settings.getMongoHostname(), settings.getMongoPort());
            DB db = mongo.getDB(settings.getMongoDatabaseName());
            partCollection = db.getCollection(DB_PART_COLLECTION_NAME);
        } catch (UnknownHostException e) {
            // fail fast - bad hostname
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid connection settings : " + settings);
        }
    }

    @Override
    public void add(Part part) {
        DBObject partDbo = mapPartToDBO(part);
        partCollection.insert(partDbo);
    }

    
    @Override
    public Part retrieveByPartNumber(String partNum) {
        Part part = null;
        DBObject query = new BasicDBObject(PART_NUMBER_MONGO_IDX, partNum);
        DBObject partDbo = this.getSingleDboByQuery(query);
        if (partDbo!=null) {
            part = this.mapDBOToPart(partDbo);
        }
        return part;
    }

    @Override
    public void update(Part part) {
        DBObject query = new BasicDBObject(PART_NUMBER_MONGO_IDX, part.getNumber());
        DBObject originalPart = this.getSingleDboByQuery(query);
        if (originalPart==null) {
            throw new IllegalArgumentException("Call to update part failed.  There is no part in the database with number " + part.getNumber());
        }
        DBObject replacementPart = this.mapPartToDBO(part);
        partCollection.update(originalPart, replacementPart);
    }

    @Override
    public void deleteByPartNumber(String partNum) {
        DBObject query = new BasicDBObject(PART_NUMBER_MONGO_IDX, partNum);
        DBObject deletePart = this.getSingleDboByQuery(query);
        if (deletePart!=null) {
            partCollection.remove(deletePart);
        } else {
            log.warn("Call made to delete a part that does not exist : " + partNum);
        }
    }
    
    private DBObject getSingleDboByQuery(DBObject query) {
        DBObject partDbo = null;
       
        DBCursor cursor = partCollection.find(query);
        try {
           if (cursor.count()>1) {
               throw new IllegalArgumentException("Invalid query for single part.  Multiple parts returned.");
           }
           while(cursor.hasNext()) {
               partDbo = cursor.next();
           }
        } finally {
           cursor.close();
        }
        
        return partDbo;
    }
    
    private DBObject mapPartToDBO(Part part) {
        DBObject partDbo = new BasicDBObject();
        partDbo.put(PART_NUMBER_MONGO_IDX, part.getNumber());
        partDbo.put(PART_TITLE_MONGO_IDX, part.getTitle());
        partDbo.put(PART_DESCRIPTION_MONGO_IDX, part.getDescription());
        
        return partDbo;
    }
    
    private Part mapDBOToPart(DBObject partDbo) {
        Part part = new Part((String) partDbo.get(PART_NUMBER_MONGO_IDX));
        part.setDescription((String) partDbo.get(PART_DESCRIPTION_MONGO_IDX));
        part.setTitle((String) partDbo.get(PART_TITLE_MONGO_IDX));
        
        return part;
    }

}
