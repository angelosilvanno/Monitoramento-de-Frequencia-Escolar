package dao;

import models.Aluno;

public class TestAlunoDAO {

    public static void main(String[] args) {

        Aluno aluno = new Aluno(
                1,
                "Maria Silva",
                "12345678900",
                "maria@email.com",
                "123",
                20250101,
                "João Silva"
        );

        // Criar aluno
        AlunoDAO.criarAluno(aluno);

        // Buscar aluno por matrícula
        System.out.println("\n=== Buscando aluno por matrícula ===");
        Aluno a1 = AlunoDAO.buscarAluno(20250101);
        System.out.println(a1);

        // Listar todos
        System.out.println("\n=== Listando todos ===");
        AlunoDAO.listarAlunos();

        // Excluir um aluno
        System.out.println("\n=== Excluindo ===");
        AlunoDAO.excluirAluno(20250101);
    }
}
