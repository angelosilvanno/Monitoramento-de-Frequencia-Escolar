package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import models.Professor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    private static MongoCollection<Document> collection;

    static {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        collection = db.getCollection("professores");
    }

    // -------------------- CRIAR PROFESSOR --------------------=
    public static Professor criarProfessor(String nome, String cpf, String email, String senha,
                                           int idUsuario, String numeroCNDB, boolean coordenador) {

        if (buscarProfessor(numeroCNDB) != null) {
            System.out.println("Já existe um professor com este CNDB!");
            return null;
        }

        Document doc = new Document()
                .append("idUsuario", idUsuario)
                .append("nome", nome)
                .append("cpf", cpf)
                .append("email", email)
                .append("senha", senha)
                .append("numeroCNDB", numeroCNDB)
                .append("coordenador", coordenador);

        collection.insertOne(doc);

        return new Professor(idUsuario, nome, cpf, email, senha, numeroCNDB, coordenador);
    }

    // -------------------- BUSCAR POR CNDB --------------------
    public static Professor buscarProfessor(String numeroCNDB) {
        Document doc = collection.find(Filters.eq("numeroCNDB", numeroCNDB)).first();
        if (doc == null) return null;
        return documentToProfessor(doc);
    }

    // -------------------- EDITAR PROFESSOR --------------------
    public static void editarProfessor(Professor professorAtual, int idUsuario, String nome, String cpf,
                                       String email, String senha, boolean coordenador, String numeroCNDB) {

        Document update = new Document("$set", new Document()
                .append("nome", nome)
                .append("cpf", cpf)
                .append("email", email)
                .append("senha", senha)
                .append("idUsuario", idUsuario)
                .append("numeroCNDB", numeroCNDB)
                .append("coordenador", coordenador)
        );

        collection.updateOne(Filters.eq("numeroCNDB", professorAtual.getNumeroCNDB()), update);
        System.out.println("Professor atualizado com sucesso!");
    }

    // -------------------- EXCLUIR PROFESSOR --------------------
    public static void excluirProfessor(String numeroCNDB) {
        collection.deleteOne(Filters.eq("numeroCNDB", numeroCNDB));
        System.out.println("Professor removido do MongoDB!");
    }

    // -------------------- LISTAR PROFESSORES --------------------=
    public static List<Professor> listarProfessor() {
        List<Professor> lista = new ArrayList<>();
        FindIterable<Document> docs = collection.find();

        for (Document d : docs) {
            lista.add(documentToProfessor(d));
        }
        return lista;
    }

    // -------------------- VISUALIZAR PROFESSORES --------------------
    public void visualizarProfessor() {
        List<Professor> lista = listarProfessor();

        if (lista.isEmpty()) {
            System.out.println("Nenhum professor cadastrado!");
            return;
        }

        System.out.println("\n===== Lista de Professores =====");
        for (Professor p : lista) {
            System.out.println("Nome: " + p.getNome());
            System.out.println("CPF: " + p.getCpf());
            System.out.println("Email: " + p.getEmail());
            System.out.println("ID Usuário: " + p.getId());
            System.out.println("CNDB: " + p.getNumeroCNDB());
            System.out.println("Coordenador: " + p.getCoordenador());
            System.out.println("---------------------------");
        }
        System.out.println("==============================\n");
    }

    // -------------------- VERIFICAR SE É COORDENADOR --------------------
    public boolean ehCoordenador(String numeroCNDB) {
        Professor p = buscarProfessor(numeroCNDB);
        if (p == null) return false;
        return p.getCoordenador();
    }

    // -------------------- DOCUMENT PARA PROFESSOR --------------------
    private static Professor documentToProfessor(Document doc) {
        return new Professor(
                doc.getInteger("idUsuario"),
                doc.getString("nome"),
                doc.getString("cpf"),
                doc.getString("email"),
                doc.getString("senha"),
                doc.getString("numeroCNDB"),
                doc.getBoolean("coordenador")
        );
    }
}