package db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import utils.EnvReader;

public class TestConnection {

    public static void main(String[] args) {

        // pegar URI do .env
        String connectionString = EnvReader.get("MONGO_URI");

        if (connectionString == null || connectionString.isEmpty()) {
            System.out.println("❌ ERRO: MONGO_URI não encontrada no .env");
            return;
        }

        try {
            // Versão da API
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            // Config cliente
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();

            // Cliente
            MongoClient mongoClient = MongoClients.create(settings);

            // Banco admin
            MongoDatabase database = mongoClient.getDatabase("admin");

            // Comando ping
            database.runCommand(new Document("ping", 1));

            System.out.println("✅ Conexão bem-sucedida com o MongoDB Atlas!");

        } catch (MongoException e) {
            System.out.println("❌ Erro ao conectar ao MongoDB:");
            e.printStackTrace();
        }
    }
}
