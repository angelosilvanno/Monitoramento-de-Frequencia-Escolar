package dao;

import models.Turma;

public class TestTurmaDAO {
    public static void main(String[] args) {
        System.out.println("=== Teste de TurmaDAO ===");

        // Criar turma
        Turma turma = new Turma(3, "Ensino Médio");
        TurmaDAO.criarTurma(turma.getIdTurma(), turma.getNomeTurma());

        System.out.println("Turma inserida com sucesso!");

        // Listar turmas
        System.out.println("\n--- Listando ---");
        for (Turma t : TurmaDAO.listarTurma()) {
            System.out.println(t);
        }
    }
}
