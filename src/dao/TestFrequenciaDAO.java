package dao;

import models.Frequencia;
import java.time.LocalDate;
import java.util.List;

public class TestFrequenciaDAO {

    public static void main(String[] args) {
        FrequenciaDAO frequenciaDAO = new FrequenciaDAO();

        int idTeste = 900;
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
        System.out.println("Registro criado com sucesso.");

        System.out.println("\n--- TESTE 2: LISTAR POR ALUNO ---");
        List<Frequencia> listaAluno = frequenciaDAO.listarFrequenciaAluno(idAlunoTeste);
        System.out.println("Total de registros para Aluno " + idAlunoTeste + ": " + listaAluno.size());
        if (!listaAluno.isEmpty()) {
            novoRegistro = listaAluno.get(0);
        }

        System.out.println("\n--- TESTE 3: EDITAR REGISTRO ---");
        if (novoRegistro != null) {
            System.out.println("Tentando editar registro ID: " + novoRegistro.getIdFrequencia());
            novoRegistro.setStatusPresenca("FALTA_JUSTIFICADA");
            novoRegistro.setJustificativa("Motivo de saúde");
            frequenciaDAO.editarFrequencia(novoRegistro);
        } else {
             System.out.println("Falha ao encontrar registro para edição.");
        }
        
        System.out.println("\n--- TESTE 4: LISTAR POR TURMA ---");
        List<Frequencia> listaTurma = frequenciaDAO.listarFrequenciaTurma(idTurmaTeste);
        System.out.println("Total de registros para Turma " + idTurmaTeste + ": " + listaTurma.size());

        System.out.println("\n--- TESTE 5: EXCLUIR REGISTRO ---");
        frequenciaDAO.excluirFrequencia(idTeste);
        
        System.out.println("\n--- TESTE 6: VERIFICAR EXCLUSÃO (Listando por Aluno novamente) ---");
        listaAluno = frequenciaDAO.listarFrequenciaAluno(idAlunoTeste);
        boolean excluido = true;
        for (Frequencia f : listaAluno) {
            if (f.getIdFrequencia() == idTeste) {
                excluido = false;
                break;
            }
        }
        if (excluido) {
            System.out.println("Sucesso: Registro com ID " + idTeste + " foi removido.");
        } else {
            System.out.println("Erro: Registro ainda existe após tentativa de exclusão.");
        }
    }
}