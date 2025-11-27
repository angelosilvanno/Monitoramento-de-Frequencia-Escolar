package dao;

import models.Turma;
import dao.TurmaDAO;

public class TestTurmaDAO {
    public static void main(String[] args) {
        System.out.println("=== Teste de TurmaDAO ===");

        TurmaDAO turmaDAO = new TurmaDAO();

        // Criar turma
        Turma t = new Turma(3, "Ensino Médio");
        TurmaDAO.criarTurma(t.getIdTurma(), t.getNomeTurma());

        System.out.println("Turma inserida com sucesso!");

        // Listar turmas
        System.out.println("\n--- Listando ---");
        for (Turma turma : turmaDAO.listarTurma()) {
            System.out.println(turma);
        }
    }
}
