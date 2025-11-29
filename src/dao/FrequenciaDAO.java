package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import models.Frequencia;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FrequenciaDAO {

    private MongoCollection<Document> collection;

    public FrequenciaDAO() {
        MongoDatabase db = MongoConnection.getDatabase("escola");
        this.collection = db.getCollection("frequencias");
    }

    // ============================================================
    // CRIAR FREQUÊNCIA
    // ============================================================
    public Frequencia criarFrequencia(int matriculaAluno, int idTurma, int idFrequencia,
                                      LocalDate dataAula, String statusPresenca) {

        // verificar se o ID já existe
        Document existente = collection.find(Filters.eq("idFrequencia", idFrequencia)).first();
        if (existente != null) {
            System.out.println("ERRO: Já existe frequência com este ID!");
            return null;
        }

        Frequencia f = new Frequencia(
                idFrequencia,
                matriculaAluno,
                idTurma,
                dataAula,
                statusPresenca,
                ""
        );

        Document doc = new Document()
                .append("idFrequencia", idFrequencia)
                .append("idAluno", matriculaAluno)
                .append("idTurma", idTurma)
                .append("dataAula", dataAula.toString())
                .append("statusPresenca", statusPresenca)
                .append("justificativa", "");

        collection.insertOne(doc);

        System.out.println("Frequência criada com sucesso!");

        return f;
    }

    // ============================================================
    // EDITAR FREQUÊNCIA
    // ============================================================
    public void editarFrequencia(int matriculaAluno, int idTurma, int idFrequencia,
                                 LocalDate dataAula, String statusPresenca) {

        Bson filter = Filters.eq("idFrequencia", idFrequencia);

        Bson update = Updates.combine(
                Updates.set("idAluno", matriculaAluno),
                Updates.set("idTurma", idTurma),
                Updates.set("dataAula", dataAula.toString()),
                Updates.set("statusPresenca", statusPresenca)
        );

        collection.updateOne(filter, update);

        System.out.println("Frequência atualizada com sucesso!");
    }

    // ============================================================
    // REGISTRAR PRESENÇA (não estático, sem parâmetros)
    // ============================================================
    public void registrarPresenca() {

        System.out.println("registrarPresenca() foi chamado, "
                + "mas é necessário saber qual ID de frequência atualizar.");

        // ⚠️ Aqui deixo pronto para você completar:
        // collection.updateOne(Filters.eq("idFrequencia", ???), Updates.set("statusPresenca", "PRESENTE"));

        // Você decide como quer que o método saiba qual frequência alterar.
    }

    // ============================================================
    // JUSTIFICAR FALTA
    // ============================================================
    public void justificarFalta(int idFrequencia, String texto) {

        collection.updateOne(
                Filters.eq("idFrequencia", idFrequencia),
                Updates.set("justificativa", texto)
        );

        System.out.println("Justificativa adicionada!");
    }

    // ============================================================
    // LISTAR POR ALUNO
    // ============================================================
    public List<Frequencia> listarFrequenciaAluno(int matriculaAluno) {

        List<Frequencia> lista = new ArrayList<>();

        for (Document d : collection.find(Filters.eq("idAluno", matriculaAluno))) {
            lista.add(documentToFrequencia(d));
        }

        return lista;
    }

    // ============================================================
    // LISTAR POR TURMA
    // ============================================================
    public List<Frequencia> listarFrequenciaTurma(int idTurma) {

        List<Frequencia> lista = new ArrayList<>();

        for (Document d : collection.find(Filters.eq("idTurma", idTurma))) {
            lista.add(documentToFrequencia(d));
        }

        return lista;
    }

    // ============================================================
    // REMOVER FREQUÊNCIA
    // ============================================================
    public void excluirFrequencia(int idFrequencia) {
        collection.deleteOne(Filters.eq("idFrequencia", idFrequencia));
        System.out.println("Frequência excluída!");
    }

    // ============================================================
    // GERAR RELATÓRIO
    // ============================================================
    public String gerarRelatorioFrequencia(String nome, int matriculaAluno, int idTurma,
                                           int idFrequencia, LocalDate dataAula,
                                           String statusPresenca) {

        return "\n===== RELATÓRIO DE FREQUÊNCIA =====\n"
                + "Aluno: " + nome + "\n"
                + "Matrícula: " + matriculaAluno + "\n"
                + "Turma: " + idTurma + "\n"
                + "ID Frequência: " + idFrequencia + "\n"
                + "Data da Aula: " + dataAula + "\n"
                + "Status: " + statusPresenca + "\n"
                + "===================================\n";
    }

    // ============================================================
    // DOCUMENT → OBJETO
    // ============================================================
    private Frequencia documentToFrequencia(Document doc) {

        LocalDate dataAula = LocalDate.parse(doc.getString("dataAula"));

        return new Frequencia(
                doc.getInteger("idFrequencia"),
                doc.getInteger("idAluno"),
                doc.getInteger("idTurma"),
                dataAula,
                doc.getString("statusPresenca"),
                doc.getString("justificativa")
        );
    }
}
