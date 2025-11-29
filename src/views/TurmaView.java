package views;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.TurmaDAO;
import java.util.List;
import java.util.Scanner;
import models.Aluno;
import models.Professor;
import models.Turma;

public class TurmaView {

    public static void gerenciarTurmas() {

        Scanner sc = new Scanner(System.in);
        AlunoDAO alunoDAO = new AlunoDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        boolean run = true;

        while (run) {

            System.out.println("\n=== GERENCIAMENTO DE TURMAS ===");
            System.out.println("1. Criar Turma");
            System.out.println("2. Visualizar Turma");
            System.out.println("3. Atualizar Turma");
            System.out.println("4. Remover Turma");
            System.out.println("5. Listar Turmas");
            System.out.println("6. Adicionar Aluno");
            System.out.println("7. Adicionar Professor");
            System.out.println("8. Voltar ao Menu");
            System.out.print("> ");

            int opcao;
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1 -> criarTurma(sc);
                case 2 -> visualizarTurma(sc);
                case 3 -> atualizarTurma(sc);
                case 4 -> removerTurma(sc);
                case 5 -> listarTurmas();
                case 6 -> adicionarAluno(sc, alunoDAO);
                case 7 -> atribuirProfessor(sc, professorDAO);
                case 8 -> { return; }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void criarTurma(Scanner sc) {
        System.out.print("ID da Turma: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nome da Turma: ");
        String nome = sc.nextLine();

        TurmaDAO.criarTurma(id, nome);
        System.out.println("Turma criada com sucesso!");
    }

    private static void visualizarTurma(Scanner sc) {
        System.out.print("Informe o ID da turma: ");
        int id = Integer.parseInt(sc.nextLine());

        TurmaDAO dao = new TurmaDAO();
        dao.visualizarTurma(id);
    }

    private static void atualizarTurma(Scanner sc) {
        System.out.print("ID da Turma a atualizar: ");
        int idAntigo = Integer.parseInt(sc.nextLine());

        Turma turma = TurmaDAO.buscarTurma(idAntigo);

        if (turma == null) {
            System.out.println("Turma não encontrada!");
            return;
        }

        System.out.print("Novo ID: ");
        int novoId = Integer.parseInt(sc.nextLine());

        System.out.print("Novo nome: ");
        String novoNome = sc.nextLine();

        TurmaDAO.editarTurma(turma, novoId, novoNome);
        System.out.println("Turma atualizada com sucesso!");
    }

    private static void removerTurma(Scanner sc) {
        System.out.print("ID da turma a remover: ");
        int id = Integer.parseInt(sc.nextLine());

        TurmaDAO.excluirTurma(id);
        System.out.println("Turma removida com sucesso!");
    }

    private static void listarTurmas() {
        System.out.println("\n=== LISTA DE TURMAS ===");

        List<Turma> turmas = TurmaDAO.listarTurma();

        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada!");
            return;
        }

        for (Turma t : turmas) {
            System.out.println("ID: " + t.getIdTurma() + " | Nome: " + t.getNomeTurma());
        }
    }

    private static void adicionarAluno(Scanner sc, AlunoDAO alunoDAO) {
        System.out.println("ID da Turma: ");
        int idTurma = Integer.parseInt(sc.nextLine());

        Turma turma = TurmaDAO.buscarTurma(idTurma);

        if (turma == null) {
            System.out.println("Turma não encontrada!");
            return;
        }

        System.out.print("Matrícula do Aluno: ");
        int matricula = Integer.parseInt(sc.nextLine());

        Aluno aluno = AlunoDAO.buscarAluno(matricula);

        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }

        TurmaDAO dao = new TurmaDAO();
        dao.adicionarAluno(idTurma, aluno);

        System.out.println("Aluno adicionado com sucesso!");
    }

    private static void atribuirProfessor(Scanner sc, ProfessorDAO professorDAO) {
        System.out.println("ID da Turma: ");
        int idTurma = Integer.parseInt(sc.nextLine());

        Turma turma = TurmaDAO.buscarTurma(idTurma);

        if (turma == null) {
            System.out.println("Turma não encontrada!");
            return;
        }

        System.out.println("Número CNDB do Professor: ");
        String numeroCNDB = sc.nextLine();

        Professor professor = ProfessorDAO.buscarProfessor(numeroCNDB);

        if (professor == null) {
            System.out.println("Professor não encontrado!");
            return;
        }

        TurmaDAO dao = new TurmaDAO();
        dao.atribuirProfessor(idTurma, professor);

        System.out.println("Professor atribuído com sucesso!");
    }
}
