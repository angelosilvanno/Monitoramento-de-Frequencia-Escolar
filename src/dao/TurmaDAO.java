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

    private static MongoCollection<Document> collection;

    // NOVA LISTA LOCAL ADICIONADA (SEM IMPACTAR O MONGODB)
    private static List<Turma> turmas = new ArrayList<>();

    public TurmaDAO() {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        collection = db.getCollection("turmas");
    }

    // ============================================================
    //                      CRIAR TURMA 
    // ============================================================
    public static Turma criarTurma(int idTurma, String nomeTurma) {

        TurmaDAO dao = new TurmaDAO();

        if (dao.turmaExiste(idTurma)) {
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
        turmas.add(turma); // <--- SINCRONIZA COM A LISTA LOCAL

        return turma;
    }

    // ============================================================
    //                      BUSCAR POR ID
    // ============================================================
    public static Turma buscarTurma(int idTurma) {
        Document doc = collection.find(Filters.eq("idTurma", idTurma)).first();
        if (doc == null) return null;

        Turma t = documentToTurma(doc);
        return t;
    }

    // ============================================================
    //                      EXCLUIR TURMA
    // ============================================================
    public static void excluirTurma(int idTurma) {
        collection.deleteOne(Filters.eq("idTurma", idTurma));
        turmas.removeIf(t -> t.getIdTurma() == idTurma); // remove da lista local
        System.out.println("Turma removida do MongoDB!");
    }

    // ============================================================
    //                      LISTAR TURMAS
    // ============================================================
    public static List<Turma> listarTurma() {

        List<Turma> lista = new ArrayList<>();
        FindIterable<Document> docs = collection.find();

        for (Document d : docs) {
            lista.add(documentToTurma(d));
        }

        return lista;
    }

    // ============================================================
    //                      EDITAR TURMA
    // ============================================================
    public static void editarTurma(Turma turma, int idTurma, String nomeTurma) {

        Document update = new Document("$set", new Document()
                .append("idTurma", idTurma)
                .append("nomeTurma", nomeTurma)
        );

        collection.updateOne(Filters.eq("idTurma", turma.getIdTurma()), update);

        turmas.removeIf(t -> t.getIdTurma() == turma.getIdTurma());
        turmas.add(new Turma(idTurma, nomeTurma)); // atualiza lista local

        System.out.println("Turma atualizada com sucesso!");
    }

    // ============================================================
    //                    VISUALIZAR TURMA
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

            if (professor != null) {
                System.out.println("Professor: " + professor.getNome() + " (CNDB: " + numeroCNDB + ")");
            } else {
                System.out.println("Professor não localizado no MongoDB (CNDB: " + numeroCNDB + ")");
            }
        } else {
            System.out.println("Nenhum professor atribuído.");
        }

        System.out.println("------------------------------------");

        // ALUNOS
        List<Integer> matriculas = turmaDoc.getList("alunos", Integer.class);

        if (matriculas == null || matriculas.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado nessa turma.");
            return;
        }

        System.out.println("Alunos da Turma:");

        for (Integer m : matriculas) {
            Aluno a = AlunoDAO.buscarAluno(m);

            if (a != null) {
                System.out.println("- " + a.getNome() + " (Matrícula: " + m + ")");
            } else {
                System.out.println("- Matrícula " + m + " não localizada no MongoDB");
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

        System.out.println("Aluno adicionado à turma!");
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

        String professorAtual = turmaDoc.getString("professor");

        if (professorAtual != null && !professorAtual.isEmpty()) {
            System.out.println("Essa turma já possui professor (CNDB: " + professorAtual + ")");
            return;
        }

        Document outraTurma = collection.find(Filters.eq("professor", professor.getNumeroCNDB())).first();

        if (outraTurma != null) {
            System.out.println("Professor já vinculado à turma ID: " + outraTurma.getInteger("idTurma"));
            return;
        }

        Document update = new Document("$set", new Document("professor", professor.getNumeroCNDB()));
        collection.updateOne(Filters.eq("idTurma", idTurma), update);

        // sincroniza professor na lista local
        Turma t = buscarTurma(idTurma);
        if (t != null) t.setProfessor(professor);

        System.out.println("Professor atribuído à turma!");
    }

    // ============================================================
    //                      DOCUMENT → TURMA
    // ============================================================
    private static Turma documentToTurma(Document doc) {
        return new Turma(
                doc.getInteger("idTurma"),
                doc.getString("nomeTurma")
        );
    }

    public boolean turmaExiste(int idTurma) {
        return collection.find(Filters.eq("idTurma", idTurma)).first() != null;
    }


    public void criarTurmaLocal(int idTurma, String nomeTurma) {
        turmas.add(new Turma(idTurma, nomeTurma));
    }

    public List<Turma> listarTurmasLocal() {
        return turmas;
    }

    public Turma buscarTurmaLocal(int idTurma) {
        for (Turma t : turmas) {
            if (t.getIdTurma() == idTurma) return t;
        }
        return null;
    }

    public boolean atribuirProfessorLocal(int idTurma, Professor professor) {
        Turma turma = buscarTurmaLocal(idTurma);
        if (turma != null) {
            turma.setProfessor(professor);
            return true;
        }
        return false;
    }
}
