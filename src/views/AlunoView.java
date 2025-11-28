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
            System.out.println("2. Visualizar Aluno");
            System.out.println("3. Atualizar Aluno");
            System.out.println("4. Remover Aluno");
            System.out.println("5. Listar Alunos");
            System.out.println("6. Voltar ao Menu");
            System.out.print("> ");

            int opcao;

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1 -> cadastrarAluno(sc);
                case 2 -> visualizarAluno(sc);
                case 3 -> atualizarAluno(sc);
                case 4 -> removerAluno(sc);
                case 5 -> AlunoDAO.listarAlunos();
                case 6 -> { return; }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    // ============================================================
    // CADASTRAR ALUNO
    // ============================================================
    private static void cadastrarAluno(Scanner sc) {

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

            System.out.print("Matrícula do Aluno: ");
            int matricula = Integer.parseInt(sc.nextLine());

            System.out.print("Nome do Responsável: ");
            String nomeResp = sc.nextLine();

            Aluno aluno = new Aluno(id, nome, cpf, email, senha, matricula, nomeResp);

            AlunoDAO.criarAluno(aluno);
            System.out.println("Aluno cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    // ============================================================
    // VISUALIZAR ALUNO
    // ============================================================
    private static void visualizarAluno(Scanner sc) {

        System.out.print("Informe a matrícula: ");
        int matricula = Integer.parseInt(sc.nextLine());

        AlunoDAO.visualizarAluno(matricula);
    }

    // ============================================================
    // ATUALIZAR ALUNO
    // ============================================================
    private static void atualizarAluno(Scanner sc) {

        System.out.print("Matrícula do aluno a atualizar: ");
        int matricula = Integer.parseInt(sc.nextLine());

        Aluno aluno = AlunoDAO.buscarAluno(matricula);

        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }

        System.out.println("Deixe vazio para manter o valor atual.");

        System.out.print("Novo nome (" + aluno.getNome() + "): ");
        String nome = sc.nextLine();
        if (!nome.isBlank()) aluno.setNome(nome);

        System.out.print("Novo CPF (" + aluno.getCpf() + "): ");
        String cpf = sc.nextLine();
        if (!cpf.isBlank()) aluno.setCpf(cpf);

        System.out.print("Novo email (" + aluno.getEmail() + "): ");
        String email = sc.nextLine();
        if (!email.isBlank()) aluno.setEmail(email);

        System.out.print("Nova senha (" + aluno.getSenha() + "): ");
        String senha = sc.nextLine();
        if (!senha.isBlank()) aluno.setSenha(senha);

        System.out.print("Novo nome do responsável (" + aluno.getNomeResp() + "): ");
        String nomeResp = sc.nextLine();
        if (!nomeResp.isBlank()) aluno.setNomeResp(nomeResp);

        System.out.print("Novo ID do usuário (" + aluno.getId() + "): ");
        String idTxt = sc.nextLine();
        if (!idTxt.isBlank()) aluno.setId(Integer.parseInt(idTxt));

        AlunoDAO.editarAluno(aluno);
        System.out.println("Aluno atualizado com sucesso!");
    }

    // ============================================================
    // REMOVER ALUNO
    // ============================================================
    private static void removerAluno(Scanner sc) {

        System.out.print("Informe a matrícula a remover: ");
        int matricula = Integer.parseInt(sc.nextLine());

        AlunoDAO.excluirAluno(matricula);
        System.out.println("Aluno removido com sucesso!");
    }
}
