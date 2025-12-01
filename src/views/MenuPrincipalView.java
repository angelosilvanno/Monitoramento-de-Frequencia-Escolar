package views;

import models.Usuario;
import models.Aluno;
import models.Frequencia;
import models.Professor;

import dao.AlunoDAO;
import dao.FrequenciaDAO;
import dao.ProfessorDAO;
import dao.TurmaDAO;

import views.FrequenciaView;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipalView {

    public static void menu(Usuario logado, Scanner sc) {

    boolean coordenador = false;

    if (logado instanceof Professor prof) {
        coordenador = prof.ehCoordenador();
    }

    boolean sairMenu = false;
    while (!sairMenu) {
        System.out.println("\n===== MENU PRINCIPAL =====");

        if (coordenador) {
            System.out.println("1. Menu de Alunos");
            System.out.println("2. Menu de Professores");
            System.out.println("3. Menu de Turmas");
            System.out.println("4. Sair");
        } else if (logado instanceof Professor) {
            System.out.println("1. Ver Minhas Turmas");
            System.out.println("2. Ver Turma Específica");
            System.out.println("3. Gerenciar Frequência");
            System.out.println("4. Sair");
        } else if (logado instanceof Aluno) {
            System.out.println("1 - Minhas Turmas");
            System.out.println("2 - Minha Frequência");
            System.out.println("3 - Sair");
        }

        System.out.print("-> ");
        int op = sc.nextInt();
        sc.nextLine();

        // --------------- MENU COORDENADOR --------------
        if (coordenador) {
            switch (op) {
                case 1 -> submenuAlunos(sc);
                case 2 -> submenuProfessores(sc);
                case 3 -> submenuTurmas(sc);
                case 4 -> {
                    System.out.println("Saindo...");
                    sairMenu = true;
                }
                default -> System.out.println("Opção inválida!");
            }

        // ------------ MENU PROFESSOR (NÃO COORDENADOR) ---------------
        } else if (logado instanceof Professor p) {
            switch (op) {
                case 1 -> {
                    System.out.println("\nSuas turmas:");
                    TurmaDAO.listarTurma()
                            .stream()
                            .filter(t -> t.getProfessor() != null &&
                                    t.getProfessor().getNumeroCNDB().equals(p.getNumeroCNDB()))
                            .forEach(t -> System.out.println(t.getIdTurma() + " - " + t.getNomeTurma()));
                }
                case 2 -> {
                    System.out.print("ID da turma: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    TurmaDAO daoT = new TurmaDAO();
                    daoT.visualizarTurma(id);
                }
                case 3 -> FrequenciaView.gerenciarFrequencia();
                case 4 -> {
                    System.out.println("Saindo...");
                    sairMenu = true;
                }
                default -> System.out.println("Opção inválida!");
            }

        // ------------------ MENU ALUNO -----------------
        } else if (logado instanceof Aluno a) {
            switch (op) {
                case 1 -> {
                    System.out.println("\nSuas turmas: ");
                    TurmaDAO.listarTurma()
                            .stream()
                            .filter(t -> t.getAlunos().stream()
                                    .anyMatch(al -> al.getMatricula() == a.getMatricula()))
                            .forEach(t -> System.out.println(t.getIdTurma() + " - " + t.getNomeTurma()));
                }
                case 2 -> {
                    FrequenciaDAO dao = new FrequenciaDAO();
                    List<Frequencia> lista = dao.listarFrequenciaAluno(a.getMatricula());
                    if (lista.isEmpty()) {
                        System.out.println("Nenhuma frequência encontrada!");
                    } else {
                        System.out.println("\n=== Suas Frequências ===");
                        lista.forEach(f ->
                                System.out.println(
                                        "Turma ID: " + f.getIdTurma() +
                                                " | Data: " + f.getDataAula() +
                                                " | Status: " + f.getStatusPresenca()
                                )
                        );
                    }
                }
                case 3 -> {
                    System.out.println("Saindo...");
                    sairMenu = true;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}

    //--------------- SUBMENU DE ALUNOS (PARA COORDENADOR) ------------------
    private static void submenuAlunos(Scanner sc) {
        int op;

        do {
            System.out.println("\n----- GERENCIAR ALUNOS -----");
            System.out.println("1 - Cadastrar Alunos");
            System.out.println("2 - Listar Alunos");
            System.out.println("3 - Editar Aluno");
            System.out.println("4 - Excluir Aluno");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> {
                    System.out.print("Matrícula: ");
                    int matricula = sc.nextInt();
                    sc.nextLine();

                    System.out.print("ID do Usuário: ");
                    int idUsuario = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Senha: ");
                    String senha = sc.nextLine();

                    System.out.print("Responsável: ");
                    String nomeResp = sc.nextLine();

                    AlunoDAO.criarAluno(nome, cpf, email, senha, idUsuario, matricula, nomeResp);
                }
                case 2 -> AlunoDAO.visualizarAluno();
                case 3 -> {
                    System.out.print("Matrícula: ");
                    int mat = sc.nextInt();
                    sc.nextLine();

                    var aluno = AlunoDAO.buscarAluno(mat);
                    if (aluno == null) {
                        System.out.println("Aluno não encontrado.");
                        break;
                    }

                    System.out.print("Novo nome: ");
                    String nome = sc.nextLine();

                    System.out.print("Novo CPF: ");
                    String cpf = sc.nextLine();

                    System.out.print("Novo email: ");
                    String email = sc.nextLine();

                    System.out.print("Nova senha: ");
                    String senha = sc.nextLine();

                    System.out.print("Novo ID usuário: ");
                    int idUsu = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Novo responsável: ");
                    String resp = sc.nextLine();

                    AlunoDAO.editarAluno(mat, idUsu, nome, cpf, email, senha, resp);
                }

                case 4 -> {
                    System.out.print("Matrícula: ");
                    int mat = sc.nextInt();
                    sc.nextLine();
                    AlunoDAO.excluirAluno(mat);
                }

                case 0 -> System.out.println("Voltando...");

                default -> System.out.println("Opção inválida!");
            }

        } while (op != 0);
    }

    //--------------- SUBMENU DE PROFESSORES ------------------
    private static void submenuProfessores(Scanner sc) {
        int op;

        do {
            System.out.println("\n----- GERENCIAR PROFESSORES -----");
            System.out.println("1 - Listar Professores");
            System.out.println("2 - Editar Professor");
            System.out.println("3 - Excluir Professor");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> {
                    ProfessorDAO dao = new ProfessorDAO();
                    dao.visualizarProfessor();
                }

                case 2 -> {
                    System.out.print("CNDB atual: ");
                    String cndb = sc.nextLine();

                    var prof = ProfessorDAO.buscarProfessor(cndb);
                    if (prof == null) {
                        System.out.println("Professor não encontrado.");
                        break;
                    }

                    System.out.print("Novo nome: ");
                    String nome = sc.nextLine();

                    System.out.print("Novo CPF: ");
                    String cpf = sc.nextLine();

                    System.out.print("Novo email: ");
                    String email = sc.nextLine();

                    System.out.print("Nova senha: ");
                    String senha = sc.nextLine();

                    System.out.print("Novo ID usuário: ");
                    int idUsu = sc.nextInt();
                    sc.nextLine();

                    System.out.print("É coordenador? (true/false): ");
                    boolean coord = sc.nextBoolean();
                    sc.nextLine();

                    System.out.print("Novo CNDB: ");
                    String novoCNDB = sc.nextLine();

                    ProfessorDAO.editarProfessor(prof, idUsu, nome, cpf, email, senha, coord, novoCNDB);
                }

                case 3 -> {
                    System.out.print("CNDB: ");
                    String cndb = sc.nextLine();
                    ProfessorDAO.excluirProfessor(cndb);
                }

                case 0 -> System.out.println("Voltando...");

                default -> System.out.println("Opção inválida!");
            }

        } while (op != 0);
    }

    //--------------- SUBMENU DE TURMAS ----------------
    private static void submenuTurmas(Scanner sc) {
        int op;

        do {
            System.out.println("\n----- GERENCIAR TURMAS -----");
            System.out.println("1 - Criar Turma");
            System.out.println("2 - Editar Turma");
            System.out.println("3 - Excluir Turma");
            System.out.println("4 - Adicionar Aluno");
            System.out.println("5 - Adicionar Professor");
            System.out.println("6 - Visualizar Turma");
            System.out.println("7 - Visualizar Todas as Turmas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    TurmaDAO.criarTurma(id, nome);
                }

                case 2 -> {
                    System.out.print("ID turma atual: ");
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

                case 3 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    TurmaDAO.excluirTurma(id);
                }

                case 4 -> {
                    System.out.print("ID da turma: ");
                    int idTurma = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Matrícula do aluno: ");
                    int mat = sc.nextInt();
                    sc.nextLine();

                    var aluno = AlunoDAO.buscarAluno(mat);
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
                    System.out.print("ID da turma: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    TurmaDAO daoT = new TurmaDAO();
                    daoT.visualizarTurma(id);
                }
                
                case 7 -> TurmaDAO.visualizarTodasTurmas();

                case 0 -> System.out.println("Voltando...");

                default -> System.out.println("Opção inválida!");
            }

        } while (op != 0);
    }
}
