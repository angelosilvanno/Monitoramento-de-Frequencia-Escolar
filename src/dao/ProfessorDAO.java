package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Professor;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;

public class ProfessorDAO {
    
    private MongoCollection<Document> collection;

    public ProfessorDAO() {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        this.collection = db.getCollection("professores");
    }

    // ============== INSERT ==================
    public void inserir(Professor professor) {
        Document doc = new Document()
                .append("idUsuario", professor.getId())
                .append("nome", professor.getNome())
                .append("cpf", professor.getCpf())
                .append("email", professor.getEmail())
                .append("senha", professor.getSenha())
                .append("numeroCNDB", professor.getNumeroCNDB())
                .append("cordenador", professor.getCoordenador());

        collection.insertOne(doc);
        System.out.println("Professor inserido no MongoDB!");
    }

    // =============== BUSCAR POR ID ===============
    public Professor buscarPorId(int id) {
        Document doc = collection.find(Filters.eq("idUsuario", id)).first();

        if (doc == null) return null;

        return new Professor(
                doc.getInteger("idUsuario"),
                doc.getString("nome"),
                doc.getString("cpf"),
                doc.getString("email"),
                doc.getString("senha"),
                doc.getString("numeroCNDB"),
                doc.getBoolean("cordenador")
        );
    }

    // =============== LISTAR TODOS ===============
    public void listarTodos() {
        FindIterable<Document> docs = collection.find();

        for (Document d : docs) {
            System.out.println(d.toJson());
        }
    }

    // =============== REMOVER ===============
    public void remover(int id) {
        collection.deleteOne(Filters.eq("idUsuario", id));
        System.out.println("Professor removido!");
    }
}
