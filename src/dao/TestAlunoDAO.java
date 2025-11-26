package dao;

import models.Aluno;

public class TestAlunoDAO {
    public static void main(String[] args) {

        AlunoDAO dao = new AlunoDAO();

        Aluno a = new Aluno(
                1,
                "Maria Silva",
                "12345678900",
                "maria@email.com",
                "123",
                20250101,
                "João Silva"
        );

        dao.inserir(a);

        System.out.println("\n=== Buscando aluno ===");
        Aluno buscado = dao.buscarPorId(1);
        System.out.println(buscado);

        System.out.println("\n=== Listando todos ===");
        dao.listarTodos();
    }
}
