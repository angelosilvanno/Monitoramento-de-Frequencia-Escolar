package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Professor;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;

public class ProfessorDAO {
    
    private static MongoCollection<Document> collection;

    static {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        collection = db.getCollection("professores");
    }

    public static void criarProfessor(Professor professor) {
        Document doc = new Document()
                .append("idUsuario", professor.getId())
                .append("nome", professor.getNome())
                .append("cpf", professor.getCpf())
                .append("email", professor.getEmail())
                .append("senha", professor.getSenha())
                .append("numeroCNDB", professor.getNumeroCNDB())
                .append("coordenador", professor.getCoordenador());

        collection.insertOne(doc);
        System.out.println("Professor inserido no MongoDB!");
    }

    public static Professor buscarProfessor(String numeroCNDB) {

        Document doc = collection.find(Filters.eq("numeroCNDB", numeroCNDB)).first();

        if (doc == null) return null;

        return documentToProfessor(doc);
    }

    public static void editarProfessor(Professor professorAtualizado) {

        Document update = new Document("$set", new Document()
                .append("nome", professorAtualizado.getNome())
                .append("cpf", professorAtualizado.getCpf())
                .append("email", professorAtualizado.getEmail())
                .append("senha", professorAtualizado.getSenha())
                .append("numeroCNDB", professorAtualizado.getNumeroCNDB())
                .append("coordenador", professorAtualizado.getCoordenador())
                .append("idUsuario", professorAtualizado.getId())
        );
        collection.updateOne(
                Filters.eq("numeroCNDB", professorAtualizado.getNumeroCNDB()),
                update
        );

        System.out.println("Professor atualizado com sucesso!");
    }

    public static void listarProfessor() {

        FindIterable<Document> docs = collection.find();

        for (Document d : docs) {
            System.out.println(d.toJson());
        }
    }

    public static void excluirProfessor(String numeroCNDB) {

        collection.deleteOne(Filters.eq("numeroCNDB", numeroCNDB));
        System.out.println("Professor removido do MongoDB!");
    }

    public void visualizarProfessor(String numeroCNDB) {

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
        System.out.println("Coordenador: " + professor.getCoordenador());
        System.out.println("==========================\n");
    }
    
    private static Professor documentToProfessor(Document doc) {

        return new Professor(
                doc.getInteger("idUsuario"),
                doc.getString("nome"),
                doc.getString("cpf"),
                doc.getString("email"),
                doc.getString("senha"),
                doc.getString("numeroCNDB"),
                doc.getBoolean("coordenador", false)
        );
    }

    public boolean professorExiste(String numeroCNDB) {
        return collection.find(Filters.eq("numeroCNDB", numeroCNDB)).first() != null;
    }
}
