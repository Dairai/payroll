package com.payroll.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.payroll.model.PayrollFileModel;
import com.payroll.utils.ConfigManager;
import org.bson.*;

/**
 * Created by Dairai on 6/11/2017.
 */
public class FileMongoRepo {

    private String mongoServerName = "";
    private String mongoDbName = "";
    private int mongoDbPort;
    private String mongoDbUser = "";
    private String mongoDbPassword = "";

    public FileMongoRepo(){setDBParams();}

    private void setDBParams()
    {
        mongoServerName = ConfigManager.getInstance().getSetting(ConfigManager.MONGO_SERVER_KEY);
        mongoDbUser = ConfigManager.getInstance().getSetting(ConfigManager.MONGO_DB_USER);
        mongoDbPassword = ConfigManager.getInstance().getSetting(ConfigManager.MONGO_DB_PASSWORD);
        mongoDbPort = ConfigManager.getInstance().getSettingAsInt(ConfigManager.MONGO_DB_PORT_KEY);
        mongoDbName = ConfigManager.getInstance().getSetting(ConfigManager.MONGO_DB_NAME_KEY);
    }

    private MongoDatabase getMongoDatabase() {
        MongoClient mongo;
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

        return mongo.getDatabase(mongoDbName);
    }

    public void storeFile(PayrollFileModel file){
        MongoDatabase db = this.getMongoDatabase();

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

        return !(res.first()==null);

    }
}
