package org.dmp.mongo.db;

import java.net.UnknownHostException;

import javax.inject.Inject;

import org.dmp.mongo.model.part.Part;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.DBQuery.Query;
import org.mongojack.JacksonDBCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 *   This is a Mongo DAO that uses Mongojack to provide basic Object Mapping (java objects to database objects via JSON objects)
 *   
 * @author david.peters
 *
 */
@MongoWithMongojackMapping
public class MongoPartMongojackDAO implements PartDAO {
    
    private String DB_PART_COLLECTION_NAME = "parts";
    
    private JacksonDBCollection<Part, String> collection;
    
    public @Inject MongoPartMongojackDAO(MongoSettings settings) {
        try {
            // is this injectable?
            MongoClient mongo = new MongoClient(settings.getMongoHostname(), settings.getMongoPort());
            DB db = mongo.getDB(settings.getMongoDatabaseName());
            DBCollection partCollection = db.getCollection(DB_PART_COLLECTION_NAME);
            
            collection = JacksonDBCollection.wrap(partCollection, Part.class, String.class);
        } catch (UnknownHostException e) {
            // fail fast - bad hostname
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid connection settings : " + settings);
        }
    }

    @Override
    public void add(Part part) {
        collection.insert(part);
    }

    @Override
    public Part retrieveByPartNumber(String partNum) {
        Part part = null;
        DBCursor<Part> cursor = collection.find().is("number", partNum);
        if (cursor.hasNext()) {
            part = cursor.next();
        }
        return part;
    }

    @Override
    public void update(Part part) {
        // Unfortunately, number is hard-coded so mapping isn't great
        Query query = DBQuery.is("number", part.getNumber());
        collection.update(query, part);
    }

    @Override
    public void deleteByPartNumber(String partNum) {
        Query query = DBQuery.is("number", partNum);
        collection.remove(query);
    }

}
