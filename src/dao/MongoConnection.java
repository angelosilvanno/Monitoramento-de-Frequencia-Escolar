package dao;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import utils.EnvReader;

public class MongoConnection {    
    private static final String CONNECTION_STRING = EnvReader.get("MONGO_URI");
    private static MongoClient client;

    public static MongoDatabase getDatabase(String dbName) {
        if (client == null) {
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                    .serverApi(serverApi)
                    .build();

            client = MongoClients.create(settings);
        }

        return client.getDatabase(dbName);
    }
}
