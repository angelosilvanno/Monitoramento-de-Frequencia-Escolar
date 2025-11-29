package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import models.Frequencia;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FrequenciaDAO {

    private MongoCollection<Document> collection;

    public FrequenciaDAO() {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        collection = db.getCollection("frequencias");
    }

    // ============================================================
    // CRIAR FREQUÊNCIA
    // ============================================================
    public Frequencia criarFrequencia(int idAluno, int idTurma, int idFrequencia, LocalDate dataAula, String statusPresenca) {

        try {
            Document doc = new Document()
                    .append("idFrequencia", idFrequencia)
                    .append("idAluno", idAluno)
                    .append("idTurma", idTurma)
                    .append("dataAula", dataAula.toString())
                    .append("statusPresenca", statusPresenca)
                    .append("justificativa", "");

            collection.insertOne(doc);

            return new Frequencia(idFrequencia, idAluno, idTurma, dataAula, statusPresenca, "");

        } catch (Exception e) {
            System.out.println("❌ Erro ao criar frequência: " + e.getMessage());
            return null;
        }
    }

    // ============================================================
    // EDITAR FREQUÊNCIA
    // ============================================================
    public void editarFrequencia(Frequencia f, int idAluno, int idTurma, int idFrequencia, LocalDate dataAula, String statusPresenca) {
        try {
            Document update = new Document("$set", new Document()
                    .append("idAluno", idAluno)
                    .append("idTurma", idTurma)
                    .append("dataAula", dataAula.toString())
                    .append("statusPresenca", statusPresenca)
            );

            collection.updateOne(Filters.eq("idFrequencia", idFrequencia), update);
            System.out.println("✔️ Frequência atualizada!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao atualizar frequência: " + e.getMessage());
        }
    }

    // ============================================================
    // EXCLUIR FREQUÊNCIA
    // ============================================================
    public void excluirFrequencia(int idFrequencia) {
        try {
            collection.deleteOne(Filters.eq("idFrequencia", idFrequencia));
            System.out.println("✔️ Frequência removida!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao excluir frequência: " + e.getMessage());
        }
    }

    // ============================================================
    // LISTAR FREQUÊNCIA DE UM ALUNO
    // ============================================================
    public List<Frequencia> listarFrequenciaAluno(int idAluno) {
        List<Frequencia> lista = new ArrayList<>();
        FindIterable<Document> docs = collection.find(Filters.eq("idAluno", idAluno));

        for (Document d : docs) {
            lista.add(documentToFrequencia(d));
        }

        return lista;
    }

    // ============================================================
    // LISTAR FREQUÊNCIA DE UMA TURMA
    // ============================================================
    public List<Frequencia> listarFrequenciaTurma(int idTurma) {
        List<Frequencia> lista = new ArrayList<>();
        FindIterable<Document> docs = collection.find(Filters.eq("idTurma", idTurma));

        for (Document d : docs) {
            lista.add(documentToFrequencia(d));
        }

        return lista;
    }

    // ============================================================
    // REGISTRAR PRESENÇA
    // ============================================================
    public void registrarPresenca(int idFrequencia, String statusPresenca) {
        try {
            collection.updateOne(Filters.eq("idFrequencia", idFrequencia),
                    new Document("$set", new Document("statusPresenca", statusPresenca)));
            System.out.println("✔️ Presença registrada!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao registrar presença: " + e.getMessage());
        }
    }

    // ============================================================
    // JUSTIFICAR FALTA
    // ============================================================
    public void justificarFalta(int idFrequencia, String texto) {
        try {
            collection.updateOne(Filters.eq("idFrequencia", idFrequencia),
                    new Document("$set", new Document("justificativa", texto)));
            System.out.println("✔️ Falta justificada!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao justificar falta: " + e.getMessage());
        }
    }

    // ============================================================
    // GERAR RELATÓRIO
    // ============================================================
    public String gerarRelatorioFrequencia(Frequencia f) {
        return f.toString();
    }

    // ============================================================
    // DOCUMENT → FREQUÊNCIA
    // ============================================================
    private Frequencia documentToFrequencia(Document doc) {
        return new Frequencia(
                doc.getInteger("idFrequencia"),
                doc.getInteger("idAluno"),
                doc.getInteger("idTurma"),
                LocalDate.parse(doc.getString("dataAula")),
                doc.getString("statusPresenca"),
                doc.getString("justificativa")
        );
    }
}