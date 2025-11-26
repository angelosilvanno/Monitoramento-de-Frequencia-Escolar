package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Aluno;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;

public class AlunoDAO {

    private MongoCollection<Document> collection;

    public AlunoDAO() {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        this.collection = db.getCollection("alunos");
    }

    // =============== INSERT ===============
    public void inserir(Aluno aluno) {
        Document doc = new Document()
                .append("idUsuario", aluno.getId())
                .append("nome", aluno.getNome())
                .append("cpf", aluno.getCpf())
                .append("email", aluno.getEmail())
                .append("senha", aluno.getSenha())
                .append("matricula", aluno.getMatricula())
                .append("nomeResp", aluno.getNomeResp());

        collection.insertOne(doc);
        System.out.println("Aluno inserido no MongoDB!");
    }

    // =============== BUSCAR POR ID ===============
    public Aluno buscarPorId(int id) {
        Document doc = collection.find(Filters.eq("idUsuario", id)).first();

        if (doc == null) return null;

        return new Aluno(
                doc.getInteger("idUsuario"),
                doc.getString("nome"),
                doc.getString("cpf"),
                doc.getString("email"),
                doc.getString("senha"),
                doc.getInteger("matricula"),
                doc.getString("nomeResp")
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
        System.out.println("Aluno removido!");
    }
}
