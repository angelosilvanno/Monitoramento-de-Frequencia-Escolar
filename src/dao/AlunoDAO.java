package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Aluno;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;

public class AlunoDAO {

    private static MongoCollection<Document> collection;

    static {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        collection = db.getCollection("alunos");
    }

    // ============================================================
    // VERIFICAÇÕES DE DUPLICIDADE
    // ============================================================

    public static boolean existeAlunoComMatricula(int matricula) {
        return collection.find(Filters.eq("matricula", matricula)).first() != null;
    }

    public static boolean existeAlunoComCpf(String cpf) {
        return collection.find(Filters.eq("cpf", cpf)).first() != null;
    }

    public static boolean existeAlunoComIdUsuario(int idUsuario) {
        return collection.find(Filters.eq("idUsuario", idUsuario)).first() != null;
    }

    // ============================================================
    // CRIAR ALUNO (AGORA COM VALIDAÇÕES)
    // ============================================================

    public static void criarAluno(Aluno aluno) {

        // 🔒 VALIDAÇÃO 1 — matrícula duplicada
        if (existeAlunoComMatricula(aluno.getMatricula())) {
            System.out.println("❌ ERRO: Já existe um aluno com esta MATRÍCULA!");
            return;
        }

        // 🔒 VALIDAÇÃO 2 — CPF duplicado
        if (existeAlunoComCpf(aluno.getCpf())) {
            System.out.println("❌ ERRO: Já existe um aluno com este CPF!");
            return;
        }

        // 🔒 VALIDAÇÃO 3 — idUsuario duplicado
        if (existeAlunoComIdUsuario(aluno.getId())) {
            System.out.println("❌ ERRO: Já existe um usuário com este ID!");
            return;
        }

        // Se passou pelas validações, salva normalmente
        Document doc = new Document()
                .append("idUsuario", aluno.getId())
                .append("nome", aluno.getNome())
                .append("cpf", aluno.getCpf())
                .append("email", aluno.getEmail())
                .append("senha", aluno.getSenha())
                .append("matricula", aluno.getMatricula())
                .append("nomeResp", aluno.getNomeResp());

        collection.insertOne(doc);
        System.out.println("✔️ Aluno inserido no MongoDB!");
    }

    // ============================================================
    // BUSCAR POR MATRÍCULA
    // ============================================================

    public static Aluno buscarAluno(int matriculaAluno) {

        Document doc = collection.find(Filters.eq("matricula", matriculaAluno)).first();

        if (doc == null) return null;

        return documentToAluno(doc);
    }

    // ============================================================
    // EDITAR ALUNO
    // ============================================================

    public static void editarAluno(Aluno alunoAtualizado) {

        Document update = new Document("$set", new Document()
                .append("nome", alunoAtualizado.getNome())
                .append("cpf", alunoAtualizado.getCpf())
                .append("email", alunoAtualizado.getEmail())
                .append("senha", alunoAtualizado.getSenha())
                .append("nomeResp", alunoAtualizado.getNomeResp())
                .append("idUsuario", alunoAtualizado.getId())
        );

        collection.updateOne(
                Filters.eq("matricula", alunoAtualizado.getMatricula()),
                update
        );

        System.out.println("✔️ Aluno atualizado com sucesso!");
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
    // EXCLUIR ALUNO
    // ============================================================

    public static void excluirAluno(int matriculaAluno) {

        collection.deleteOne(Filters.eq("matricula", matriculaAluno));
        System.out.println("✔️ Aluno removido do MongoDB!");
    }

    // ============================================================
    // VISUALIZAR ALUNO
    // ============================================================

    public static void visualizarAluno(int matricula) {

        Aluno aluno = buscarAluno(matricula);

        if (aluno == null) {
            System.out.println("❌ Aluno não encontrado!");
            return;
        }

        System.out.println("\n===== Dados do Aluno =====");
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("CPF: " + aluno.getCpf());
        System.out.println("Email: " + aluno.getEmail());
        System.out.println("Senha: " + aluno.getSenha());
        System.out.println("Matrícula: " + aluno.getMatricula());
        System.out.println("ID Usuário: " + aluno.getId());
        System.out.println("Responsável: " + aluno.getNomeResp());
        System.out.println("==========================\n");
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
