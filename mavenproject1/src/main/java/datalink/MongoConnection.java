package datalink;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnection {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static MongoDatabase getdatabase() {
        if (database == null) {
            String url = "mongodb+srv://marcelosampaio0987_db_user:root@clustercrud.1yn7afi.mongodb.net/?retryWrites=true&w=majority&appName=ClusterCRUD";
            mongoClient = MongoClients.create(url);
            database = mongoClient.getDatabase("db_sistema");
        }
        return database;
    }

}
