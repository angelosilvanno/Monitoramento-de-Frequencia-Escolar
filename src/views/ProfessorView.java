package views;

import dao.ProfessorDAO;
import models.Professor;

import java.util.List;
import java.util.Scanner;

public class ProfessorView {

    private static final Scanner sc = new Scanner(System.in);
    private static final ProfessorDAO dao = new ProfessorDAO();

    public static void gerenciarProfessor() {

        while (true) {
            System.out.println("\n=== GERENCIAMENTO DE PROFESSORES ===");
            System.out.println("1. Cadastrar Professor");
            System.out.println("2. Visualizar Todos Professores");
            System.out.println("3. Visualizar Professor");
            System.out.println("4. Atualizar Professor");
            System.out.println("5. Remover Professor");
            System.out.println("6. Verificar se é Coordenador");
            System.out.println("7. Voltar ao Menu");
            System.out.print("> ");

            int opcao;
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1 -> cadastrarProfessor();
                case 2 -> dao.visualizarProfessor();
                case 3 -> visualizarProfessorPorCNDB();
                case 4 -> atualizarProfessor();
                case 5 -> removerProfessor();
                case 6 -> verificarCoordenador();
                case 7 -> { return; }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    // -------------------- CADASTRAR PROFESSOR --------------------
    private static void cadastrarProfessor() {
        try {
            System.out.print("ID do usuário: ");
            int idUsuario = Integer.parseInt(sc.nextLine());

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

            System.out.print("É coordenador? (true/false): ");
            boolean coordenador = Boolean.parseBoolean(sc.nextLine());

            Professor professor = ProfessorDAO.criarProfessor(nome, cpf, email, senha, idUsuario, numeroCNDB, coordenador);
            if (professor != null) System.out.println("Professor cadastrado com sucesso!");
            else System.out.println("Falha ao cadastrar professor!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
        }
    }
    // -------------------- VISUALIZAR PROFESSOR POR CNDB --------------------
    private static void visualizarProfessorPorCNDB() {
        try {
            System.out.print("Número CNDB do professor: ");
            String numeroCNDB = sc.nextLine();

            Professor prof = ProfessorDAO.buscarProfessor(numeroCNDB);
            if (prof == null) {
                System.out.println("Professor não encontrado!");
                return;
            }

            System.out.println("\n===== Dados do Professor =====");
            System.out.println("Nome: " + prof.getNome());
            System.out.println("CPF: " + prof.getCpf());
            System.out.println("Email: " + prof.getEmail());
            System.out.println("ID Usuário: " + prof.getId());
            System.out.println("Número CNDB: " + prof.getNumeroCNDB());
            System.out.println("Coordenador: " + prof.getCoordenador());
            System.out.println("===============================\n");

        } catch (Exception e) {
            System.out.println("Erro ao visualizar professor: " + e.getMessage());
        }
    }

    // -------------------- ATUALIZAR PROFESSOR --------------------
    private static void atualizarProfessor() {
        try {
            System.out.print("Número CNDB do professor a atualizar: ");
            String numeroCNDB = sc.nextLine();

            Professor prof = ProfessorDAO.buscarProfessor(numeroCNDB);
            if (prof == null) {
                System.out.println("Professor não encontrado!");
                return;
            }

            System.out.println("Deixe vazio para manter o valor atual.");

            System.out.print("Novo nome (" + prof.getNome() + "): ");
            String nome = sc.nextLine();
            if (nome.isBlank()) nome = prof.getNome();

            System.out.print("Novo CPF (" + prof.getCpf() + "): ");
            String cpf = sc.nextLine();
            if (cpf.isBlank()) cpf = prof.getCpf();

            System.out.print("Novo email (" + prof.getEmail() + "): ");
            String email = sc.nextLine();
            if (email.isBlank()) email = prof.getEmail();

            System.out.print("Nova senha (" + prof.getSenha() + "): ");
            String senha = sc.nextLine();
            if (senha.isBlank()) senha = prof.getSenha();

            System.out.print("É coordenador? (" + prof.getCoordenador() + "): ");
            String coordTxt = sc.nextLine();
            boolean coordenador = coordTxt.isBlank() ? prof.getCoordenador() : Boolean.parseBoolean(coordTxt);

            ProfessorDAO.editarProfessor(prof, prof.getId(), nome, cpf, email, senha, coordenador, prof.getNumeroCNDB());
            System.out.println("Professor atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar professor: " + e.getMessage());
        }
    }

    // -------------------- REMOVER PROFESSOR --------------------
    private static void removerProfessor() {
        try {
            System.out.print("Número CNDB do professor a remover: ");
            String numeroCNDB = sc.nextLine();

            ProfessorDAO.excluirProfessor(numeroCNDB);
            System.out.println("Professor removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao remover professor: " + e.getMessage());
        }
    }

    // -------------------- VERIFICAR SE É COODENADOR --------------------
    private static void verificarCoordenador() {
        try {
            System.out.print("Número CNDB do professor: ");
            String numeroCNDB = sc.nextLine();

            boolean ehCoord = dao.ehCoordenador(numeroCNDB);
            if (ehCoord) System.out.println("O professor é coordenador!");
            else System.out.println("O professor não é coordenador!");

        } catch (Exception e) {
            System.out.println("Erro ao verificar coordenador: " + e.getMessage());
        }
    }

    // -------------------- LISTAR TODOS OS PROFESSORES --------------------
    public static void listarProfessores() {
        List<Professor> lista = ProfessorDAO.listarProfessor();
        if (lista.isEmpty()) {
            System.out.println("Nenhum professor cadastrado!");
            return;
        }

        System.out.println("\n===== Lista de Professores =====");
        for (Professor p : lista) {
            System.out.println("Nome: " + p.getNome() +
                               " | CNDB: " + p.getNumeroCNDB() +
                               " | Coordenador: " + p.getCoordenador());
        }
        System.out.println("===============================\n");
    }
}