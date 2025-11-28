package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.MongoCursor;

import models.Frequencia;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class FrequenciaDAO {

    private MongoCollection<Document> collection;
    private String nomeColecao = "frequencias"; 
    private String nomeBanco = "escola";     


    public FrequenciaDAO() {
        // Assume que MongoConnection está disponível e funcional.
        MongoDatabase db = MongoConnection.getDatabase(nomeBanco);
        this.collection = db.getCollection(nomeColecao);
    }

    // ============================================================
    // CRIAR REGISTRO DE FREQUÊNCIA
    // ============================================================
    
    public void criarFrequencia(Frequencia frequencia) {
        try {
            Document doc = new Document()
                    .append("idFrequencia", frequencia.getIdFrequencia()) 
                    .append("idAluno", frequencia.getIdAluno())
                    .append("idTurma", frequencia.getIdTurma())
                    .append("dataAula", frequencia.getDataAula().toString()) 
                    .append("statusPresenca", frequencia.getStatusPresenca())
                    .append("justificativa", frequencia.getJustificativa());
            collection.insertOne(doc);
            System.out.println("Registro de Frequência inserido no MongoDB!");
        } catch (Exception e) {
            System.err.println("Erro ao criar registro de frequência: " + e.getMessage());
        }
    }

    // ============================================================
    // EDITAR REGISTRO
    // ============================================================
    
    public void editarFrequencia(Frequencia frequenciaAtualizada) {
        Bson filter = Filters.eq("idFrequencia", frequenciaAtualizada.getIdFrequencia());
        
        Bson update = Updates.combine(
            Updates.set("idAluno", frequenciaAtualizada.getIdAluno()),
            Updates.set("idTurma", frequenciaAtualizada.getIdTurma()),
            Updates.set("dataAula", frequenciaAtualizada.getDataAula().toString()),
            Updates.set("statusPresenca", frequenciaAtualizada.getStatusPresenca()),
            Updates.set("justificativa", frequenciaAtualizada.getJustificativa())
        );

        collection.updateOne(filter, update);
        System.out.println("Registro de Frequência atualizado com sucesso!");
    }

    // ============================================================
    // LISTAR FREQUÊNCIA POR ALUNO
    // ============================================================
    
    public List<Frequencia> listarFrequenciaAluno(int idAluno) {
        List<Frequencia> frequencias = new ArrayList<>();
        Bson filter = Filters.eq("idAluno", idAluno);
        try (MongoCursor<Document> cursor = collection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                frequencias.add(documentToFrequencia(cursor.next()));
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar registros do aluno: " + e.getMessage());
        }
        return frequencias;
    }

    // ============================================================
    // LISTAR FREQUÊNCIA POR TURMA
    // ============================================================
    
    public List<Frequencia> listarFrequenciaTurma(int idTurma) {
        List<Frequencia> frequencias = new ArrayList<>();
        Bson filter = Filters.eq("idTurma", idTurma);
        try (MongoCursor<Document> cursor = collection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                frequencias.add(documentToFrequencia(cursor.next()));
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar registros da turma: " + e.getMessage());
        }
        return frequencias;
    }

    // ============================================================
    // EXCLUIR REGISTRO
    // ============================================================
    
    public void excluirFrequencia(int idFrequencia) {
        collection.deleteOne(Filters.eq("idFrequencia", idFrequencia));
        System.out.println("Registro de Frequência removido do MongoDB!");
    }

    // ============================================================
    // CONVERSÃO DOCUMENTO PARA OBJETO
    // ============================================================
    
    private Frequencia documentToFrequencia(Document doc) {
        int idAluno = doc.getInteger("idAluno");
        int idTurma = doc.getInteger("idTurma");
        
        LocalDate dataAula = LocalDate.parse(doc.getString("dataAula"));

        return new Frequencia(
                doc.getInteger("idFrequencia"),
                idAluno, 
                idTurma, 
                dataAula,
                doc.getString("statusPresenca"),
                doc.getString("justificativa")
        );
    }
}