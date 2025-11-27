package dao;

import models.Aluno;

public class TestAlunoDAO {
    public static void main(String[] args) {

        AlunoDAO dao = new AlunoDAO();

        // Criando aluno
        Aluno aluno = new Aluno(
                1,
                "Maria Silva",
                "12345678900",
                "maria@email.com",
                "123",
                20250101,
                "João Silva"
        );

        System.out.println("\n=== Criando aluno ===");
        dao.criarAluno(aluno);

        System.out.println("\n=== Buscando por matrícula ===");
        Aluno buscado = dao.buscarAluno(20250101);
        System.out.println(buscado);

        System.out.println("\n=== Buscando por ID ===");
        Aluno buscaId = dao.buscarPorId(1);
        System.out.println(buscaId);

        System.out.println("\n=== Listando todos ===");
        dao.listarTodos();

        System.out.println("\n=== Editando aluno ===");
        aluno.setNome("Maria Silva Atualizada");
        dao.editarAluno(aluno);

        System.out.println("\n=== Visualizando aluno ===");
        dao.visualizarAluno(20250101);

        System.out.println("\n=== Excluindo aluno ===");
        dao.excluirAluno(20250101);
    }
}
