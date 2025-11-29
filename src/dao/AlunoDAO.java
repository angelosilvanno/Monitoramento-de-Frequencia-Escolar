package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import models.Aluno;
import org.bson.Document;

public class AlunoDAO {

    private static MongoCollection<Document> collection;

    static {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        collection = db.getCollection("alunos");
    }

    // ============================================================
    // VERIFICAÇÕES DE DUPLICIDADE
    // ============================================================

    private static boolean existeAlunoComMatricula(int matricula) {
        return collection.find(Filters.eq("matricula", matricula)).first() != null;
    }

    private static boolean existeAlunoComCpf(String cpf) {
        return collection.find(Filters.eq("cpf", cpf)).first() != null;
    }

    private static boolean existeAlunoComIdUsuario(int idUsuario) {
        return collection.find(Filters.eq("idUsuario", idUsuario)).first() != null;
    }

    // ============================================================
    // CRIAR ALUNO
    // ============================================================

    public static Aluno criarAluno(String nome, String cpf, String email, String senha,
                                   int idUsuario, int matriculaAluno, String nomeResp) {

        // 🔒 VALIDAÇÕES
        if (existeAlunoComMatricula(matriculaAluno)) {
            System.out.println("❌ ERRO: Já existe um aluno com esta MATRÍCULA!");
            return null;
        }

        if (existeAlunoComCpf(cpf)) {
            System.out.println("❌ ERRO: Já existe um aluno com este CPF!");
            return null;
        }

        if (existeAlunoComIdUsuario(idUsuario)) {
            System.out.println("❌ ERRO: Já existe um usuário com este ID!");
            return null;
        }

        Document doc = new Document()
                .append("idUsuario", idUsuario)
                .append("nome", nome)
                .append("cpf", cpf)
                .append("email", email)
                .append("senha", senha)
                .append("matricula", matriculaAluno)
                .append("nomeResp", nomeResp);

        collection.insertOne(doc);

        System.out.println("✔️ Aluno inserido no MongoDB!");
        return documentToAluno(doc);
    }

    // ============================================================
    // EDITAR ALUNO
    // ============================================================

    public static void editarAluno(int matriculaAluno, int idUsuario, String nome, String cpf, 
                                   String email, String senha, String nomeResp) {

        Document update = new Document("$set", new Document()
                .append("nome", nome)
                .append("cpf", cpf)
                .append("email", email)
                .append("senha", senha)
                .append("idUsuario", idUsuario)
                .append("nomeResp", nomeResp)
        );

        collection.updateOne(Filters.eq("matricula", matriculaAluno), update);
        System.out.println("✔️ Aluno atualizado com sucesso!");
    }

    // ============================================================
    // EXCLUIR ALUNO
    // ============================================================

    public static void excluirAluno(int matriculaAluno) {
        collection.deleteOne(Filters.eq("matricula", matriculaAluno));
        System.out.println("✔️ Aluno removido do MongoDB!");
    }

    // ============================================================
    // BUSCAR ALUNO
    // ============================================================

    public static Aluno buscarAluno(int matriculaAluno) {
        Document doc = collection.find(Filters.eq("matricula", matriculaAluno)).first();
        if (doc == null) return null;
        return documentToAluno(doc);
    }

    // ============================================================
    // LISTAR ALUNOS
    // ============================================================

    public static void listarAlunos() {
        FindIterable<Document> docs = collection.find();
        for (Document d : docs) {
            System.out.println(d.toJson());
        }
    }

    // ============================================================
    // VISUALIZAR ALUNOS FORMATADOS
    // ============================================================

    public static void visualizarAluno() {
        FindIterable<Document> docs = collection.find();

        System.out.println("\n===== Lista de Alunos =====");
        for (Document doc : docs) {
            Aluno aluno = documentToAluno(doc);
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("CPF: " + aluno.getCpf());
            System.out.println("Email: " + aluno.getEmail());
            System.out.println("Senha: " + aluno.getSenha());
            System.out.println("Matrícula: " + aluno.getMatricula());
            System.out.println("ID Usuário: " + aluno.getId());
            System.out.println("Responsável: " + aluno.getNomeResp());
            System.out.println("---------------------------");
        }
        System.out.println("===========================\n");
    }

    // ============================================================
    // DOCUMENT → ALUNO
    // ============================================================

    private static Aluno documentToAluno(Document doc) {
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
}
