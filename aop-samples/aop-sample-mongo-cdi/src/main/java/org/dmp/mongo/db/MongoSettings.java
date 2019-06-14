package org.dmp.mongo.db;

import javax.ejb.Stateless;

@Stateless
public class MongoSettings {
    
    private String mongoDatabaseName = "assembly";
    private String mongoHostname = "localhost";
    private int mongoPort = 27017;
    
    public String getMongoHostname() {
        return mongoHostname;
    }
    public void setMongoHostname(String mongoHostname) {
        this.mongoHostname = mongoHostname;
    }
    public int getMongoPort() {
        return mongoPort;
    }
    public void setMongoPort(int mongoPort) {
        this.mongoPort = mongoPort;
    }
    public String getMongoDatabaseName() {
        return mongoDatabaseName;
    }
    public void setMongoDatabaseName(String mongoDatabaseName) {
        this.mongoDatabaseName = mongoDatabaseName;
    }
    
}
