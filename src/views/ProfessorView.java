package views;

import dao.ProfessorDAO;
import models.Professor;
import java.util.Scanner;

public class ProfessorView {

    public static void gerenciarProfessor() {

        ProfessorDAO professorDAO = new ProfessorDAO();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== GERENCIAMENTO DE PROFESSORES ===");
            System.out.println("1. Cadastrar Professor");
            System.out.println("2. Visualizar Professor");
            System.out.println("3. Atualizar Professor");
            System.out.println("4. Remover Professor");
            System.out.println("5. Listar Professores");
            System.out.println("6. Voltar ao Menu");

            int opcao;

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarProfessor(sc);
                    break;
                case 2:
                    visualizarProfessor(sc);
                    break;
                case 3:
                    atualizarProfessor(sc);
                    break;
                case 4:
                    removerProfessor(sc);
                    break;
                case 5:
                    ProfessorDAO.listarProfessor();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ========== CADASTRAR PROFESSOR ==========
    private static void cadastrarProfessor(Scanner sc) {

        try {
            System.out.print("ID do Usuário: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("CPF: ");
            String cpf = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Senha: ");
            String senha = sc.nextLine();

            System.out.print("Número CNDB: ");
            String numeroCNDB = sc.nextLine(); 

            System.out.print("Coordenador (true/false): ");
            boolean coordenador = Boolean.parseBoolean(sc.nextLine());

            Professor professor = new Professor(id, nome, cpf, email, senha, numeroCNDB, coordenador);

            ProfessorDAO.criarProfessor(professor);
            System.out.println("Professor cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
        }
    }

    // ========== VISUALIZAR PROFESSOR ==========
    private static void visualizarProfessor(Scanner sc) {

        System.out.print("Informe o número CNDB: ");
        String numeroCNDB = sc.nextLine();

        ProfessorDAO dao = new ProfessorDAO();
        dao.visualizarProfessor(numeroCNDB);
    }

    // ========== ATUALIZAR PROFESSOR ==========
    private static void atualizarProfessor(Scanner sc) {

        try {
            System.out.print("Informe o número CNDB do professor a ser atualizado: ");
            String numeroCNDB = sc.nextLine();

            Professor professor = ProfessorDAO.buscarProfessor(numeroCNDB);

            if (professor == null) {
                System.out.println("Professor não encontrado!");
                return;
            }

            System.out.println("=== Deixe em branco para manter o valor atual ===");

            System.out.print("Novo nome (" + professor.getNome() + "): ");
            String nome = sc.nextLine();
            if (!nome.isEmpty()) professor.setNome(nome);

            System.out.print("Novo CPF (" + professor.getCpf() + "): ");
            String cpf = sc.nextLine();
            if (!cpf.isEmpty()) professor.setCpf(cpf);

            System.out.print("Novo Email (" + professor.getEmail() + "): ");
            String email = sc.nextLine();
            if (!email.isEmpty()) professor.setEmail(email);

            System.out.print("Nova Senha: ");
            String senha = sc.nextLine();
            if (!senha.isEmpty()) professor.setSenha(senha);

            System.out.print("Novo Coordenador (true/false) (" + professor.getCoordenador() + "): ");
            String coord = sc.nextLine();
            if (!coord.isEmpty()) professor.setCoordenador(Boolean.parseBoolean(coord));

            ProfessorDAO.editarProfessor(professor);

        } catch (Exception e) {
            System.out.println("Erro ao atualizar professor: " + e.getMessage());
        }
    }

    // ========== REMOVER PROFESSOR ==========
    private static void removerProfessor(Scanner sc) {
        System.out.print("Informe o CNDB do professor a remover: ");
        String numeroCNDB = sc.nextLine();

        ProfessorDAO.excluirProfessor(numeroCNDB);
        System.out.println("Professor removido com sucesso!");
    }
}