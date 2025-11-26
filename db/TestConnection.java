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

public class TestConnection {

    public static void main(String[] args) {

        // 🔴 Coloque sua senha aqui:
        String connectionString = 
        try {
            // Versão da API do MongoDB Atlas
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            // Configurações do cliente
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();

            // Criar cliente
            MongoClient mongoClient = MongoClients.create(settings);

            // Conectar no banco (admin para o ping)
            MongoDatabase database = mongoClient.getDatabase("admin");

            // Comando ping
            database.runCommand(new Document("ping", 1));

            System.out.println("✅ Conexão bem sucedida com o MongoDB Atlas!");

        } catch (MongoException e) {
            System.out.println("❌ Erro ao conectar ao MongoDB:");
            e.printStackTrace();
        }
    }
}
