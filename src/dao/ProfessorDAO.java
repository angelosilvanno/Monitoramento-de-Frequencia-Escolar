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

    public Professor buscarProfessor(int numeroCNDB) {

        Document doc = collection.find(Filters.eq("numeroCNDB", numeroCNDB)).first();

        if (doc == null) return null;

        return documentToProfessor(doc);
    }

    public Professor buscarPorId(int idUsuario) {
        Document doc = collection.find(Filters.eq("idUsuario", idUsuario)).first();

        if (doc == null) return null;

        return documentToProfessor(doc);
    }

    public void editarProfessor(Professor professorAtualizado) {

        Document update = new Document("$set", new Document()
                .append("nome", professorAtualizado.getNome())
                .append("cpf", professorAtualizado.getCpf())
                .append("email", professorAtualizado.getEmail())
                .append("senha", professorAtualizado.getSenha())
                .append("numeroCNDB", professorAtualizado.getNumeroCNDB())
                .append("cordenador", professorAtualizado.getCoordenador())
                .append("idUsuario", professorAtualizado.getId())
        );
        collection.updateOne(
                Filters.eq("matricula", professorAtualizado.getNumeroCNDB()),
                update
        );

        System.out.println("Professor atualizado com sucesso!");
    }

    public void listarTodos() {

        FindIterable<Document> docs = collection.find();

        for (Document d : docs) {
            System.out.println(d.toJson());
        }
    }

    public void excluirProfessor(int numeroCNDB) {

        collection.deleteOne(Filters.eq("numeroCNDB", numeroCNDB));
        System.out.println("Professor removido do MongoDB!");
    }

    public void visualizarProfessor(int numeroCNDB) {

        Professor professor = buscarProfessor(numeroCNDB);

        if (professor == null) {
            System.out.println("Professor não encontrado!");
            return;
        }

        System.out.println("\n===== Dados do Professor =====");
        System.out.println("Nome: " + professor.getNome());
        System.out.println("CPF: " + professor.getCpf());
        System.out.println("Email: " + professor.getEmail());
        System.out.println("Senha: " + professor.getSenha());
        System.out.println("NumeroCNDB: " + professor.getNumeroCNDB());
        System.out.println("ID Usuário: " + professor.getId());
        System.out.println("Cordenador: " + professor.getCoordenador());
        System.out.println("==========================\n");
    }
    
    private Professor documentToProfessor(Document doc) {

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
}
