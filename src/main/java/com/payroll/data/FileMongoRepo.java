package com.payroll.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.payroll.model.PayrollFileModel;
import org.bson.*;

/**
 * Created by Dairai on 6/11/2017.
 */
public class FileMongoRepo {

    private String mongoServerName = "Gina";
    private String mongoDbName = "waveapps";
    private int mongoDbPort = 27017;
    private String mongoDbUser = "";
    private String mongoDbPassword = "password";

    public FileMongoRepo(){};

    private MongoDatabase getMongoDatabase() {
        MongoClient mongo = null;
        if (mongoDbUser.trim().equals("")) {
            mongo = new MongoClient(mongoServerName, mongoDbPort);
        } else {
            MongoClientURI uri = new MongoClientURI(
                    String.format("mongodb://%s:%s@%s:%s/%s",
                            mongoDbUser,
                            mongoDbPassword,
                            mongoServerName,
                            mongoDbPort,
                            mongoDbName
                    ));
            mongo = new MongoClient(uri);
        }

        MongoDatabase db =  mongo.getDatabase(mongoDbName);
        return db;
    }

    public void storeFile(PayrollFileModel file){
        MongoDatabase db = this.getMongoDatabase();

        //Note: using BsonDocument given the nature of the contents.
        MongoCollection<BsonDocument> blogCollection = db.getCollection("reports", BsonDocument.class);

        BsonDocument bDoc = new BsonDocument();
        bDoc.put("id", new BsonInt64(file.getId()));
        bDoc.put("name", new BsonString(file.getName()));
        bDoc.put("data", new BsonBinary(file.getData()));
        bDoc.put("upload_date", new BsonDateTime(file.getUploadDate().getTime()));

        blogCollection.insertOne(bDoc);
    }

    public boolean fileExists(String fileName){
        MongoDatabase db = this.getMongoDatabase();
        BsonDocument doc = new BsonDocument();
        doc.put("name", new BsonString(fileName));
        FindIterable<BsonDocument> res = db.getCollection("reports",BsonDocument.class).find(doc);

        if(res.first() == null)
            return false;

        return true;
    }
}
