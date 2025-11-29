package dao;

import models.Frequencia;
import java.time.LocalDate;
import java.util.List;

public class TestFrequenciaDAO {
    public static void main(String[] args) {

        FrequenciaDAO dao = new FrequenciaDAO();

        int idFreq = 999;
        int idAluno = 50;
        int idTurma = 20;

        System.out.println("\n=== TESTE 1: CRIAR FREQUÊNCIA ===");
        Frequencia f = dao.criarFrequencia(idAluno, idTurma, idFreq,
                LocalDate.of(2025, 10, 26),
                "PRESENTE");

        if (f != null) System.out.println("✔ Registro criado:\n" + f);
        else System.out.println("❌ Falha ao criar");


        System.out.println("\n=== TESTE 2: EDITAR FREQUÊNCIA ===");
        dao.editarFrequencia(f, idAluno, idTurma, idFreq,
                LocalDate.of(2025, 10, 27),
                "FALTA");

        List<Frequencia> freqAluno = dao.listarFrequenciaAluno(idAluno);
        System.out.println("✔ Frequências do aluno após edição:");
        freqAluno.forEach(System.out::println);


        System.out.println("\n=== TESTE 3: REGISTRAR PRESENÇA ===");
        dao.registrarPresenca(idFreq, "PRESENTE");


        System.out.println("\n=== TESTE 4: JUSTIFICAR FALTA ===");
        dao.justificarFalta(idFreq, "Consulta médica");

        freqAluno = dao.listarFrequenciaAluno(idAluno);
        System.out.println("✔ Frequências do aluno após justificativa:");
        freqAluno.forEach(System.out::println);


        System.out.println("\n=== TESTE 5: LISTAR POR TURMA ===");
        List<Frequencia> freqTurma = dao.listarFrequenciaTurma(idTurma);
        freqTurma.forEach(System.out::println);


        System.out.println("\n=== TESTE 6: GERAR RELATÓRIO ===");
        if (!freqTurma.isEmpty()) {
            String relatorio = dao.gerarRelatorioFrequencia(freqTurma.get(0));
            System.out.println(relatorio);
        }


        System.out.println("\n=== TESTE 7: EXCLUIR FREQUÊNCIA ===");
        dao.excluirFrequencia(idFreq);

        freqAluno = dao.listarFrequenciaAluno(idAluno);
        boolean existe = freqAluno.stream().anyMatch(freq -> freq.getIdFrequencia() == idFreq);

        if (!existe) System.out.println("✔ Registro removido com sucesso!");
        else System.out.println("❌ Registro ainda existe!");
    }
}
