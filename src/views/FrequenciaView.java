package views;

import dao.FrequenciaDAO;
import models.Frequencia;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FrequenciaView {

    private static final Scanner sc = new Scanner(System.in);
    private static final FrequenciaDAO dao = new FrequenciaDAO();

    public static void gerenciarFrequencia() {

        while (true) {
            System.out.println("\n=== GERENCIAMENTO DE FREQUÊNCIAS ===");
            System.out.println("1. Registrar Frequência");
            System.out.println("2. Editar Frequência");
            System.out.println("3. Excluir Frequência");
            System.out.println("4. Listar Frequência por Aluno");
            System.out.println("5. Listar Frequência por Turma");
            System.out.println("6. Registrar Presença");
            System.out.println("7. Justificar Falta");
            System.out.println("8. Gerar Relatório");
            System.out.println("9. Voltar ao Menu");
            System.out.print("> ");

            int opcao;
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println(" Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1 -> registrarFrequencia();
                case 2 -> editarFrequencia();
                case 3 -> excluirFrequencia();
                case 4 -> listarFrequenciaAluno();
                case 5 -> listarFrequenciaTurma();
                case 6 -> registrarPresenca();
                case 7 -> justificarFalta();
                case 8 -> gerarRelatorio();
                case 9 -> { return; }
                default -> System.out.println(" Opção inválida!");
            }
        }
    }

    private static void registrarFrequencia() {
        try {
            System.out.print("ID Frequência: ");
            int idFrequencia = Integer.parseInt(sc.nextLine());
            System.out.print("ID Aluno: ");
            int idAluno = Integer.parseInt(sc.nextLine());
            System.out.print("ID Turma: ");
            int idTurma = Integer.parseInt(sc.nextLine());
            System.out.print("Data da Aula (yyyy-MM-dd): ");
            LocalDate data = LocalDate.parse(sc.nextLine());
            System.out.print("Status Presença: ");
            String status = sc.nextLine();

            Frequencia f = dao.criarFrequencia(idAluno, idTurma, idFrequencia, data, status);
            if (f != null) System.out.println("✔️ Frequência registrada!");
            else System.out.println(" Falha ao registrar frequência!");

        } catch (Exception e) {
            System.out.println(" Erro: " + e.getMessage());
        }
    }

    private static void editarFrequencia() {
        try {
            System.out.print("ID Frequência a editar: ");
            int idF = Integer.parseInt(sc.nextLine());
            System.out.print("ID Aluno: ");
            int idAluno = Integer.parseInt(sc.nextLine());
            System.out.print("ID Turma: ");
            int idTurma = Integer.parseInt(sc.nextLine());
            System.out.print("Data da Aula (yyyy-MM-dd): ");
            LocalDate data = LocalDate.parse(sc.nextLine());
            System.out.print("Status Presença: ");
            String status = sc.nextLine();

            Frequencia f = new Frequencia(idF, idAluno, idTurma, data, status, "");
            dao.editarFrequencia(f, idAluno, idTurma, idF, data, status);

        } catch (Exception e) {
            System.out.println(" Erro: " + e.getMessage());
        }
    }

    private static void excluirFrequencia() {
        try {
            System.out.print("ID Frequência a remover: ");
            int idF = Integer.parseInt(sc.nextLine());
            dao.excluirFrequencia(idF);
        } catch (Exception e) {
            System.out.println(" Erro: " + e.getMessage());
        }
    }

    private static void listarFrequenciaAluno() {
        try {
            System.out.print("ID Aluno: ");
            int idAluno = Integer.parseInt(sc.nextLine());
            List<Frequencia> lista = dao.listarFrequenciaAluno(idAluno);
            if (lista.isEmpty()) System.out.println("❌ Nenhuma frequência encontrada!");
            else lista.forEach(f -> System.out.println(f));
        } catch (Exception e) {
            System.out.println(" Erro: " + e.getMessage());
        }
    }

    private static void listarFrequenciaTurma() {
        try {
            System.out.print("ID Turma: ");
            int idTurma = Integer.parseInt(sc.nextLine());
            List<Frequencia> lista = dao.listarFrequenciaTurma(idTurma);
            if (lista.isEmpty()) System.out.println("❌ Nenhuma frequência encontrada!");
            else lista.forEach(f -> System.out.println(f));
        } catch (Exception e) {
            System.out.println(" Erro: " + e.getMessage());
        }
    }

    private static void registrarPresenca() {
        try {
            System.out.print("ID Frequência: ");
            int idF = Integer.parseInt(sc.nextLine());
            System.out.print("Novo Status Presença: ");
            String status = sc.nextLine();
            dao.registrarPresenca(idF, status);
        } catch (Exception e) {
            System.out.println(" Erro: " + e.getMessage());
        }
    }

    private static void justificarFalta() {
        try {
            System.out.print("ID Frequência: ");
            int idF = Integer.parseInt(sc.nextLine());
            System.out.print("Justificativa: ");
            String texto = sc.nextLine();
            dao.justificarFalta(idF, texto);
        } catch (Exception e) {
            System.out.println(" Erro: " + e.getMessage());
        }
    }

    private static void gerarRelatorio() {
        try {
            System.out.print("ID Frequência: ");
            int idF = Integer.parseInt(sc.nextLine());
            System.out.print("ID Aluno: ");
            int idAluno = Integer.parseInt(sc.nextLine());
            System.out.print("ID Turma: ");
            int idTurma = Integer.parseInt(sc.nextLine());
            System.out.print("Data da Aula (yyyy-MM-dd): ");
            LocalDate data = LocalDate.parse(sc.nextLine());
            System.out.print("Status Presença: ");
            String status = sc.nextLine();

            Frequencia f = new Frequencia(idF, idAluno, idTurma, data, status, "");
            String relatorio = dao.gerarRelatorioFrequencia(f);
            System.out.println(relatorio);
        } catch (Exception e) {
            System.out.println(" Erro: " + e.getMessage());
        }
    }
}