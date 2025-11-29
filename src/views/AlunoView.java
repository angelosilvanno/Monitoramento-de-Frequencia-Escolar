package views;

import dao.AlunoDAO;
import models.Aluno;
import java.util.Scanner;

public class AlunoView {

    public static void gerenciarAlunos() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== GERENCIAMENTO DE ALUNOS ===");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Visualizar Aluno por Matrícula");
            System.out.println("3. Atualizar Aluno");
            System.out.println("4. Remover Aluno");
            System.out.println("5. Listar Todos os Alunos");
            System.out.println("6. Voltar ao Menu");
            System.out.print("> ");

            int opcao;

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1 -> cadastrarAluno(sc);
                case 2 -> visualizarAluno(sc);
                case 3 -> atualizarAluno(sc);
                case 4 -> removerAluno(sc);
                case 5 -> AlunoDAO.visualizarAluno(); // lista todos formatados
                case 6 -> { return; }
                default -> System.out.println("❌ Opção inválida!");
            }
        }
    }

    // ============================================================
    // CADASTRAR ALUNO
    // ============================================================
    private static void cadastrarAluno(Scanner sc) {

        try {
            System.out.print("ID do Usuário: ");
            int idUsuario = Integer.parseInt(sc.nextLine());

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("CPF: ");
            String cpf = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Senha: ");
            String senha = sc.nextLine();

            System.out.print("Matrícula do Aluno: ");
            int matriculaAluno = Integer.parseInt(sc.nextLine());

            System.out.print("Nome do Responsável: ");
            String nomeResp = sc.nextLine();

            Aluno aluno = AlunoDAO.criarAluno(nome, cpf, email, senha, idUsuario, matriculaAluno, nomeResp);

            if (aluno != null) {
                System.out.println("✔️ Aluno cadastrado com sucesso!");
            } else {
                System.out.println("❌ Falha ao cadastrar o aluno.");
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    // ============================================================
    // VISUALIZAR ALUNO POR MATRÍCULA
    // ============================================================
    private static void visualizarAluno(Scanner sc) {

        try {
            System.out.print("Informe a matrícula: ");
            int matricula = Integer.parseInt(sc.nextLine());

            Aluno aluno = AlunoDAO.buscarAluno(matricula);

            if (aluno == null) {
                System.out.println("❌ Aluno não encontrado!");
                return;
            }

            System.out.println("\n===== Dados do Aluno =====");
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("CPF: " + aluno.getCpf());
            System.out.println("Email: " + aluno.getEmail());
            System.out.println("Senha: " + aluno.getSenha());
            System.out.println("Matrícula: " + aluno.getMatricula());
            System.out.println("ID Usuário: " + aluno.getId());
            System.out.println("Responsável: " + aluno.getNomeResp());
            System.out.println("==========================\n");

        } catch (Exception e) {
            System.out.println("❌ Erro ao visualizar aluno: " + e.getMessage());
        }
    }

    // ============================================================
    // ATUALIZAR ALUNO
    // ============================================================
    private static void atualizarAluno(Scanner sc) {

        try {
            System.out.print("Matrícula do aluno a atualizar: ");
            int matriculaAluno = Integer.parseInt(sc.nextLine());

            Aluno aluno = AlunoDAO.buscarAluno(matriculaAluno);
            if (aluno == null) {
                System.out.println("❌ Aluno não encontrado!");
                return;
            }

            System.out.println("Deixe vazio para manter o valor atual.");

            System.out.print("Novo nome (" + aluno.getNome() + "): ");
            String nome = sc.nextLine();
            if (nome.isBlank()) nome = aluno.getNome();

            System.out.print("Novo CPF (" + aluno.getCpf() + "): ");
            String cpf = sc.nextLine();
            if (cpf.isBlank()) cpf = aluno.getCpf();

            System.out.print("Novo email (" + aluno.getEmail() + "): ");
            String email = sc.nextLine();
            if (email.isBlank()) email = aluno.getEmail();

            System.out.print("Nova senha (" + aluno.getSenha() + "): ");
            String senha = sc.nextLine();
            if (senha.isBlank()) senha = aluno.getSenha();

            System.out.print("Novo nome do responsável (" + aluno.getNomeResp() + "): ");
            String nomeResp = sc.nextLine();
            if (nomeResp.isBlank()) nomeResp = aluno.getNomeResp();

            System.out.print("Novo ID do usuário (" + aluno.getId() + "): ");
            String idTxt = sc.nextLine();
            int idUsuario = idTxt.isBlank() ? aluno.getId() : Integer.parseInt(idTxt);

            AlunoDAO.editarAluno(matriculaAluno, idUsuario, nome, cpf, email, senha, nomeResp);
            System.out.println("✔️ Aluno atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    // ============================================================
    // REMOVER ALUNO
    // ============================================================
    private static void removerAluno(Scanner sc) {

        try {
            System.out.print("Informe a matrícula a remover: ");
            int matriculaAluno = Integer.parseInt(sc.nextLine());

            AlunoDAO.excluirAluno(matriculaAluno);
            System.out.println("✔️ Aluno removido com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro ao remover aluno: " + e.getMessage());
        }
    }
}
