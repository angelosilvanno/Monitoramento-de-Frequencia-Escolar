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

// Cria e lista foi os unicos testados 
public class TurmaDAO {

    private static MongoCollection<Document> collection;

    public TurmaDAO() {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        collection = db.getCollection("turmas");
    }

    // ============================================================
    //                      CRIAR TURMA 
    // ============================================================
    public static Turma criarTurma(int idTurma, String nomeTurma) {

        Document doc = new Document()
                .append("idTurma", idTurma )
                .append("nomeTurma", nomeTurma)
                .append("alunos", new ArrayList<>())
                .append("professor", null);

        collection.insertOne(doc);
        System.out.println("Turma criada no MongoDB!");

        return new Turma(idTurma, nomeTurma);
    }

    // ============================================================
    //                  BUSCAR POR MATRÍCULA 
    // ============================================================
    public static Turma buscarTurma(int idTurma) {
        Document doc = collection.find(Filters.eq("idTurma", idTurma)).first();

        if (doc == null) return  null;

        return documentToTurma(doc);
    }

    // ============================================================
    //                  EXCLUIR TURMA 
    // ============================================================
    public static void excluirTurma(int idTurma) {

        collection.deleteOne(Filters.eq("idTurma", idTurma));
        System.out.println("Turma removida do MongoDB!");
    }  

    // ============================================================
    //                  LISTAR TURMAS  
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

        System.out.println("Turma atualizada com sucesso!");
    }


    // ============================================================
    //                      VISUALIZAR TURMA 
    // ============================================================
    public void visualizarTurma(int id) {

        Turma turma = buscarTurma(id);

        if (turma == null) {
            System.out.println("Turma não encontrada!");
            return;
        }

        System.out.println("\n===== Dados da Turma =====");
        System.out.println("ID Turma: " + turma.getIdTurma());
        System.out.println("Nome: " + turma.getNomeTurma());
        System.out.println("==========================\n");
    }

    // ============================================================
    //              ADICIONAR ALUNO NA TURMA 
    // ============================================================
    public void adicionarAluno(int idTurma, Aluno aluno) {

        Document update = new Document("$push",
                new Document("alunos", aluno.getMatricula()));

        collection.updateOne(Filters.eq("idTurma", idTurma), update);

        System.out.println("Aluno adicionado à turma!");
    }

    // ============================================================
    //                  ATRIBUIR PROFESSOR 
    // ============================================================
    public void atribuirProfessor(int idTurma, Professor professor) {

        Document update = new Document("$set", new Document("professor", professor.getId()));

        collection.updateOne(Filters.eq("idTurma", idTurma), update);

        System.out.println("Professor atribuído à turma!");
    }


    // ============================================================
    //                  DOCUMENT TURMA
    // ============================================================
    private static Turma documentToTurma(Document doc) {

        return new Turma(
                doc.getInteger("idTurma"),
                doc.getString("nomeTurma")
        );
    }
}
