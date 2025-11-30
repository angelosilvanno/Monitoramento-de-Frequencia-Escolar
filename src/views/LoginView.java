package views;

import models.Aluno;
import models.Professor;
import models.Usuario;
import service.UsuarioService;

import java.util.Scanner;

public class LoginView {

    public static Usuario telaLogin(Scanner sc) {

        UsuarioService service = new UsuarioService();

        while (true) {
            System.out.println("\n===== LOGIN =====");
            System.out.println("1. Fazer Login");
            System.out.println("2. Cadastrar Usuário");
            System.out.println("3. Sair");
            System.out.print("Escolha: ");
            int op = sc.nextInt();
            sc.nextLine();

            if (op == 1) {

                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Senha: ");
                String senha = sc.nextLine();

                Usuario u = service.login(email, senha);

                if (u == null) {
                    System.out.println("\n❌ Credenciais inválidas!\n");
                    continue;
                }

                System.out.println("\n✔ Login realizado com sucesso!\n");
                return u;

            } else if (op == 2) {
                telaCadastro(sc, service);

            } else if (op == 3) {
                return null;
            }
        }
    }

    private static void telaCadastro(Scanner sc, UsuarioService service) {
        System.out.println("\n===== CADASTRO =====");
        System.out.println("1. Aluno");
        System.out.println("2. Professor");
        System.out.print("Escolha: ");
        int tipo = sc.nextInt();
        sc.nextLine();

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        if (tipo == 1) {
            System.out.print("Matrícula: ");
            int mat = sc.nextInt();
            sc.nextLine();

            System.out.print("Nome do responsável: ");
            String resp = sc.nextLine();

            service.cadastrar(new Aluno(id, nome, cpf, email, senha, mat, resp));
        }
        else {
            System.out.print("Número CNDB: ");
            String cndb = sc.nextLine();

            System.out.print("É coordenador? (true/false): ");
            boolean coord = sc.nextBoolean();

            service.cadastrar(new Professor(id, nome, cpf, email, senha, cndb, coord));
        }

        System.out.println("\n✔ Cadastro realizado!\n");
    }
}
