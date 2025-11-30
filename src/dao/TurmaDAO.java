package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import models.Turma;
import models.Aluno;
import models.Professor;

import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;

import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    // ===============================
    //       VARIÁVEIS ESTÁTICAS
    // ===============================
    private static MongoCollection<Document> collection;

    // lista local
    private static List<Turma> turmas = new ArrayList<>();


    // ===============================
    //   INICIALIZAÇÃO DO MONGO (FIX)
    // ===============================
    static {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        collection = db.getCollection("turmas");
    }

    // construtor pode ficar vazio
    public TurmaDAO() { }


    // ============================================================
    //                      CRIAR TURMA 
    // ============================================================
    public static Turma criarTurma(int idTurma, String nomeTurma) {

        if (turmaExiste(idTurma)) {
            System.out.println("ERRO: Já existe uma turma com esse ID!");
            return null;
        }

        Document doc = new Document()
                .append("idTurma", idTurma)
                .append("nomeTurma", nomeTurma)
                .append("alunos", new ArrayList<>())
                .append("professor", null);

        collection.insertOne(doc);
        System.out.println("Turma criada no MongoDB!");

        Turma turma = new Turma(idTurma, nomeTurma);
        turmas.add(turma);

        return turma;
    }


    // ============================================================
    //                      BUSCAR POR ID
    // ============================================================
    public static Turma buscarTurma(int idTurma) {
        Document doc = collection.find(Filters.eq("idTurma", idTurma)).first();
        if (doc == null) return null;

        return documentToTurma(doc);
    }


    // ============================================================
    //                      EXCLUIR TURMA
    // ============================================================
    public static void excluirTurma(int idTurma) {
        collection.deleteOne(Filters.eq("idTurma", idTurma));
        turmas.removeIf(t -> t.getIdTurma() == idTurma);
        System.out.println("Turma removida do MongoDB!");
    }


    // ============================================================
    //                      LISTAR TURMAS
    // ============================================================
    public static List<Turma> listarTurma() {

        List<Turma> lista = new ArrayList<>();
        FindIterable<Document> docs = collection.find();

        for (Document d : docs) lista.add(documentToTurma(d));

        return lista;
    }


    // ============================================================
    //                      EDITAR TURMA
    // ============================================================
    public static void editarTurma(Turma turma, int novoId, String novoNome) {

        Document update = new Document("$set", new Document()
                .append("idTurma", novoId)
                .append("nomeTurma", novoNome)
        );

        collection.updateOne(Filters.eq("idTurma", turma.getIdTurma()), update);

        turmas.removeIf(t -> t.getIdTurma() == turma.getIdTurma());
        turmas.add(new Turma(novoId, novoNome));

        System.out.println("Turma atualizada com sucesso!");
    }


    // ============================================================
    //                  VISUALIZAR TURMA
    // ============================================================
    public void visualizarTurma(int idTurma) {

        Document turmaDoc = collection.find(Filters.eq("idTurma", idTurma)).first();

        if (turmaDoc == null) {
            System.out.println("Turma não encontrada!");
            return;
        }

        System.out.println("====================================");
        System.out.println("ID: " + turmaDoc.getInteger("idTurma"));
        System.out.println("Nome: " + turmaDoc.getString("nomeTurma"));
        System.out.println("====================================");

        // PROFESSOR
        String numeroCNDB = turmaDoc.getString("professor");
        if (numeroCNDB != null) {
            Professor professor = ProfessorDAO.buscarProfessor(numeroCNDB);
            System.out.println("Professor: " + professor.getNome());
        } else {
            System.out.println("Nenhum professor atribuído.");
        }

        // ALUNOS
        List<Integer> matriculas = turmaDoc.getList("alunos", Integer.class);

        if (matriculas == null || matriculas.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("Alunos da Turma:");
        for (Integer m : matriculas) {
            Aluno a = AlunoDAO.buscarAluno(m);
            if (a != null) {
                System.out.println("- " + a.getNome() + " (Matrícula: " + m + ")");
            }
        }
    }


    // ============================================================
    //              ADICIONAR ALUNO À TURMA
    // ============================================================
    public void adicionarAluno(int idTurma, Aluno aluno) {

        Document turmaDoc = collection.find(Filters.eq("idTurma", idTurma)).first();
        if (turmaDoc == null) {
            System.out.println("Turma não encontrada!");
            return;
        }

        List<Integer> alunos = turmaDoc.getList("alunos", Integer.class);

        if (alunos.contains(aluno.getMatricula())) {
            System.out.println("ERRO: Este aluno já está na turma!");
            return;
        }

        Document update = new Document("$push", new Document("alunos", aluno.getMatricula()));
        collection.updateOne(Filters.eq("idTurma", idTurma), update);

        System.out.println("Aluno adicionado!");
    }


    // ============================================================
    //                  ATRIBUIR PROFESSOR
    // ============================================================
    public void atribuirProfessor(int idTurma, Professor professor) {

        Document turmaDoc = collection.find(Filters.eq("idTurma", idTurma)).first();
        if (turmaDoc == null) {
            System.out.println("Turma não encontrada!");
            return;
        }

        Document outraTurma = collection.find(Filters.eq("professor", professor.getNumeroCNDB())).first();
        if (outraTurma != null) {
            System.out.println("Professor já está em outra turma.");
            return;
        }

        Document update = new Document("$set", new Document("professor", professor.getNumeroCNDB()));
        collection.updateOne(Filters.eq("idTurma", idTurma), update);

        Turma t = buscarTurma(idTurma);
        if (t != null) t.setProfessor(professor);

        System.out.println("Professor atribuído!");
    }


    // ============================================================
    //              DOCUMENT → OBJETO TURMA
    // ============================================================
    private static Turma documentToTurma(Document doc) {

        Turma turma = new Turma(
                doc.getInteger("idTurma"),
                doc.getString("nomeTurma")
        );

        // PROFESSOR
        String profCNDB = doc.getString("professor");
        if (profCNDB != null) {
            Professor p = ProfessorDAO.buscarProfessor(profCNDB);
            turma.setProfessor(p);
        }

        // ALUNOS
        List<Integer> matriculas = doc.getList("alunos", Integer.class);
        if (matriculas != null) {
            for (int m : matriculas) {
                Aluno a = AlunoDAO.buscarAluno(m);
                if (a != null) turma.getAlunos().add(a);
            }
        }

        return turma;
    }


    // ============================================================
    //                 FUNÇÕES AUXILIARES
    // ============================================================
    public static boolean turmaExiste(int idTurma) {
        return collection.find(Filters.eq("idTurma", idTurma)).first() != null;
    }

    public List<Turma> listarTurmasLocal() { return turmas; }

    public Turma buscarTurmaLocal(int idTurma) {
        for (Turma t : turmas) if (t.getIdTurma() == idTurma) return t;
        return null;
    }

}
