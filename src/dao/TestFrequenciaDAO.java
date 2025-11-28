package dao;

import models.Frequencia;
import java.time.LocalDate;
import java.util.List;

public class TestFrequenciaDAO {

    public static void main(String[] args) {
        FrequenciaDAO frequenciaDAO = new FrequenciaDAO();

        int idTeste = 100;
        int idAlunoTeste = 50;
        int idTurmaTeste = 20;

        System.out.println("--- TESTE 1: CRIAR REGISTRO ---");
        Frequencia novoRegistro = new Frequencia(
            idTeste, 
            idAlunoTeste, 
            idTurmaTeste,
            LocalDate.of(2025, 10, 26), 
            "PRESENTE", 
            "" 
        );
        frequenciaDAO.criarFrequencia(novoRegistro);

        System.out.println("\n--- TESTE 2: BUSCAR REGISTRO ---");
        Frequencia registroEncontrado = frequenciaDAO.buscarFrequencia(idTeste);
        if (registroEncontrado != null) {
            System.out.println("Registro encontrado:");
            System.out.println(registroEncontrado);
        } else {
            System.out.println("Erro: Registro com ID " + idTeste + " não encontrado.");
        }

        System.out.println("\n--- TESTE 3: EDITAR REGISTRO ---");
        if (registroEncontrado != null) {
            registroEncontrado.setStatusPresenca("FALTA_JUSTIFICADA");
            registroEncontrado.setJustificativa("Motivo de saúde");
            frequenciaDAO.editarFrequencia(registroEncontrado);

            Frequencia registroAtualizado = frequenciaDAO.buscarFrequencia(idTeste);
            System.out.println("Registro após edição:");
            System.out.println(registroAtualizado);
        }

        System.out.println("\n--- TESTE 4: LISTAR TODOS OS REGISTROS ---");
        List<Frequencia> todosRegistros = frequenciaDAO.listarTodos();
        System.out.println("Total de registros no banco: " + todosRegistros.size());

        System.out.println("\n--- TESTE 5: EXCLUIR REGISTRO ---");
        frequenciaDAO.excluirFrequencia(idTeste);

        Frequencia registroExcluido = frequenciaDAO.buscarFrequencia(idTeste);
        if (registroExcluido == null) {
            System.out.println("Sucesso: Registro com ID " + idTeste + " foi removido.");
        } else {
            System.out.println("Erro: Registro ainda existe após tentativa de exclusão.");
        }
    }
}