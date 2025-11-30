package views;

import models.Usuario;
import models.Aluno;
import models.Professor;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.TurmaDAO;

import java.util.Scanner;

public class MenuPrincipalView {

    public static void menu(Usuario logado, Scanner sc) {

        boolean coordenador = false;

        // Verifica se é coordenador
        if (logado instanceof Professor prof) {
            coordenador = prof.ehCoordenador();
        }

        if (logado instanceof Aluno a) {
            menuAluno(a, sc);
            return;
        }


        while (true) {
            System.out.println("\n===== MENU PRINCIPAL =====");

            if (coordenador) {
                System.out.println("1. Listar Alunos");
                System.out.println("2. Listar Professores");
                System.out.println("3. Cadastrar Turma");
                System.out.println("4. Adicionar Aluno na Turma");
                System.out.println("5. Adicionar Professor na Turma");
                System.out.println("6. Editar Turma");
                System.out.println("7. Excluir Turma");
                System.out.println("8. Editar Aluno");
                System.out.println("9. Excluir Aluno");
                System.out.println("10. Editar Professor");
                System.out.println("11. Excluir Professor");
                System.out.println("12. Sair");
            } else {
                System.out.println("1. Ver Minhas Turmas");
                System.out.println("2. Ver uma Turma Específica");
                System.out.println("3. Sair");
            }

            System.out.print("-> ");
            int op = sc.nextInt();
            sc.nextLine();

            // ================================
            //           MENU COORDENADOR
            // ================================
            if (coordenador) {

                switch (op) {

                    case 1 -> AlunoDAO.visualizarAluno();

                    case 2 -> {
                        ProfessorDAO dao = new ProfessorDAO();
                        dao.visualizarProfessor();
                    }

                    case 3 -> {
                        System.out.print("ID da turma: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Nome da turma: ");
                        String nome = sc.nextLine();

                        TurmaDAO.criarTurma(id, nome);
                    }

                    case 4 -> {
                        System.out.print("ID da turma: ");
                        int idTurma = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Matrícula do aluno: ");
                        int matricula = sc.nextInt();
                        sc.nextLine();

                        var aluno = AlunoDAO.buscarAluno(matricula);
                        if (aluno == null) {
                            System.out.println("Aluno não encontrado.");
                            break;
                        }

                        TurmaDAO daoT = new TurmaDAO();
                        daoT.adicionarAluno(idTurma, aluno);
                    }

                    case 5 -> {
                        System.out.print("ID da turma: ");
                        int idTurma = sc.nextInt();
                        sc.nextLine();

                        System.out.print("CNDB do professor: ");
                        String cndb = sc.nextLine();

                        var prof = ProfessorDAO.buscarProfessor(cndb);
                        if (prof == null) {
                            System.out.println("Professor não encontrado.");
                            break;
                        }

                        TurmaDAO daoT = new TurmaDAO();
                        daoT.atribuirProfessor(idTurma, prof);
                    }

                    case 6 -> {
                        System.out.print("ID da turma atual: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        var turma = TurmaDAO.buscarTurma(id);
                        if (turma == null) {
                            System.out.println("Turma não encontrada.");
                            break;
                        }

                        System.out.print("Novo ID: ");
                        int novoId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Novo nome: ");
                        String novoNome = sc.nextLine();

                        TurmaDAO.editarTurma(turma, novoId, novoNome);
                    }

                    case 7 -> {
                        System.out.print("ID da turma: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        TurmaDAO.excluirTurma(id);
                    }

                    case 8 -> {
                        System.out.print("Matrícula: ");
                        int mat = sc.nextInt();
                        sc.nextLine();

                        var aluno = AlunoDAO.buscarAluno(mat);
                        if (aluno == null) {
                            System.out.println("Aluno não encontrado.");
                            break;
                        }

                        System.out.println("Novo nome: ");
                        String nome = sc.nextLine();

                        System.out.println("Novo CPF: ");
                        String cpf = sc.nextLine();

                        System.out.println("Novo email: ");
                        String email = sc.nextLine();

                        System.out.println("Nova senha: ");
                        String senha = sc.nextLine();

                        System.out.println("Novo ID usuário: ");
                        int idUsu = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Novo responsável: ");
                        String resp = sc.nextLine();

                        AlunoDAO.editarAluno(mat, idUsu, nome, cpf, email, senha, resp);
                    }

                    case 9 -> {
                        System.out.print("Matrícula: ");
                        int mat = sc.nextInt();
                        sc.nextLine();

                        AlunoDAO.excluirAluno(mat);
                    }

                    case 10 -> {
                        System.out.print("CNDB atual: ");
                        String cndb = sc.nextLine();

                        var prof = ProfessorDAO.buscarProfessor(cndb);
                        if (prof == null) {
                            System.out.println("Professor não encontrado.");
                            break;
                        }

                        System.out.println("Novo nome: ");
                        String nome = sc.nextLine();

                        System.out.println("Novo CPF: ");
                        String cpf = sc.nextLine();

                        System.out.println("Novo email: ");
                        String email = sc.nextLine();

                        System.out.println("Nova senha: ");
                        String senha = sc.nextLine();

                        System.out.println("Novo ID usuário: ");
                        int idUsu = sc.nextInt();
                        sc.nextLine();

                        System.out.println("É coordenador? (true/false): ");
                        boolean coord = sc.nextBoolean();
                        sc.nextLine();

                        System.out.println("Novo CNDB: ");
                        String novoCNDB = sc.nextLine();

                        ProfessorDAO.editarProfessor(prof, idUsu, nome, cpf, email, senha, coord, novoCNDB);
                    }

                    case 11 -> {
                        System.out.print("CNDB: ");
                        String cndb = sc.nextLine();

                        ProfessorDAO.excluirProfessor(cndb);
                    }

                    case 12 -> {
                        System.out.println("Saindo...");
                        return;
                    }

                    default -> System.out.println("Opção inválida!");
                }

            } else {

                // ================================
                //       MENU PROFESSOR NORMAL
                // ================================
                switch (op) {

                    case 1 -> {
                        System.out.println("Turmas associadas ao professor:");
                        Professor p = (Professor) logado;

                        TurmaDAO.listarTurma()
                                .stream()
                                .filter(t -> t.getProfessor() != null &&
                                        t.getProfessor().equals(p.getNumeroCNDB()))
                                .forEach(t -> System.out.println(t.getIdTurma() + " - " + t.getNomeTurma()));
                    }

                    case 2 -> {
                        System.out.print("ID da turma: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        TurmaDAO daoT = new TurmaDAO();
                        daoT.visualizarTurma(id);
                    }

                    case 3 -> {
                        System.out.println("Saindo...");
                        return;
                    }

                    default -> System.out.println("Opção inválida!");
                }
            }
        }
    }

    public static void menuAluno(Aluno logado, Scanner sc) {
        while (true) {
            System.out.println("\n===== MENU DO ALUNO =====");
            System.out.println("1. Ver minhas turmas");
            System.out.println("2. Ver detalhes de uma turma");
            System.out.println("3. Sair");
            System.out.print("-> ");

            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {

                case 1 -> {
                    int matricula = logado.getMatricula();

                    System.out.println("\nTurmas do aluno:");
                    TurmaDAO.listarTurma()
                            .stream()
                            .filter(t -> t.getAlunos().stream()
                                    .anyMatch(a -> a.getMatricula() == matricula)
                            )
                            .forEach(t -> System.out.println(t.getIdTurma() + " - " + t.getNomeTurma()));
                }

                case 2 -> {
                    System.out.print("ID da turma: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    TurmaDAO daoT = new TurmaDAO();
                    daoT.visualizarTurma(id);
                }

                case 3 -> {
                    System.out.println("Saindo...");
                    return;
                }

                default -> System.out.println("Opção inválida!");
            }
        }
    }

}
