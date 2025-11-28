package views;

import dao.FrequenciaDAO;
import models.Frequencia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class FrequenciaView {

    public static void gerenciarFrequencia() {
        FrequenciaDAO frequenciaDAO = new FrequenciaDAO();
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== GERENCIAMENTO DE FREQUÊNCIA ===");
            System.out.println("1. Registrar Frequência (Criar)");
            System.out.println("2. Editar Registro de Frequência");
            System.out.println("3. Excluir Registro de Frequência");
            System.out.println("4. Listar Frequência por Aluno");
            System.out.println("5. Listar Frequência por Turma");
            System.out.println("6. Voltar ao Menu");
            System.out.print("> ");

            int opcao;

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida! Por favor, insira um número.");
                continue;
            }

            switch (opcao) {
                case 1 -> registrarFrequencia(sc, frequenciaDAO, DATE_FORMATTER);
                case 2 -> editarFrequencia(sc, frequenciaDAO, DATE_FORMATTER);
                case 3 -> excluirFrequencia(sc, frequenciaDAO);
                case 4 -> listarFrequenciaAluno(sc, frequenciaDAO, DATE_FORMATTER);
                case 5 -> listarFrequenciaTurma(sc, frequenciaDAO, DATE_FORMATTER);
                case 6 -> { return; }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    // ============================================================
    // REGISTRAR FREQUÊNCIA (CRIAR)
    // ============================================================
    private static void registrarFrequencia(Scanner sc, FrequenciaDAO frequenciaDAO, DateTimeFormatter DATE_FORMATTER) {
        try {
            System.out.print("ID do Registro de Frequência: ");
            int idFrequencia = Integer.parseInt(sc.nextLine());

            System.out.print("ID do Aluno (Matrícula): ");
            int idAluno = Integer.parseInt(sc.nextLine());

            System.out.print("ID da Turma: ");
            int idTurma = Integer.parseInt(sc.nextLine());

            System.out.print("Data da Aula (Formato dd/MM/yyyy): ");
            String dataStr = sc.nextLine();
            LocalDate dataAula = LocalDate.parse(dataStr, DATE_FORMATTER);

            System.out.print("Status de Presença (PRESENTE / FALTA / FALTA_JUSTIFICADA): ");
            String status = sc.nextLine().toUpperCase();
            
            String justificativa = "";
            if (status.equals("FALTA") || status.equals("FALTA_JUSTIFICADA")) {
                System.out.print("Justificativa (Se houver): ");
                justificativa = sc.nextLine();
            }

            Frequencia novoRegistro = new Frequencia(
                idFrequencia, idAluno, idTurma, dataAula, status, justificativa
            );

            frequenciaDAO.criarFrequencia(novoRegistro);
            System.out.println("Registro de Frequência criado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: ID, Matrícula ou Turma devem ser números inteiros.");
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido. Use dd/MM/yyyy.");
        } catch (Exception e) {
            System.out.println("Erro ao registrar frequência: " + e.getMessage());
        }
    }

    // ============================================================
    // EDITAR REGISTRO
    // ============================================================
    private static void editarFrequencia(Scanner sc, FrequenciaDAO frequenciaDAO, DateTimeFormatter DATE_FORMATTER) {
        try {
            System.out.print("ID do Registro de Frequência para editar: ");
            int idFrequencia = Integer.parseInt(sc.nextLine());

            System.out.println("Informe os novos dados para o registro ID: " + idFrequencia);

            System.out.print("Novo ID do Aluno (Matrícula): ");
            int idAluno = Integer.parseInt(sc.nextLine());

            System.out.print("Novo ID da Turma: ");
            int idTurma = Integer.parseInt(sc.nextLine());

            System.out.print("Nova Data da Aula (Formato dd/MM/yyyy): ");
            String dataStr = sc.nextLine();
            LocalDate dataAula = LocalDate.parse(dataStr, DATE_FORMATTER);

            System.out.print("Novo Status de Presença (PRESENTE / FALTA / FALTA_JUSTIFICADA): ");
            String status = sc.nextLine().toUpperCase();
            
            System.out.print("Nova Justificativa (Deixe vazio se não houver): ");
            String justificativa = sc.nextLine();


            Frequencia registroAtualizado = new Frequencia(
                idFrequencia, idAluno, idTurma, dataAula, status, justificativa
            );

            frequenciaDAO.editarFrequencia(registroAtualizado);
            System.out.println("Registro de Frequência atualizado com sucesso!");
        
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID, Matrícula ou Turma devem ser números inteiros.");
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido. Use dd/MM/yyyy.");
        } catch (Exception e) {
            System.out.println("Erro ao editar frequência: " + e.getMessage());
        }
    }

    // ============================================================
    // EXCLUIR REGISTRO
    // ============================================================
    private static void excluirFrequencia(Scanner sc, FrequenciaDAO frequenciaDAO) {
        try {
            System.out.print("ID do Registro de Frequência para remover: ");
            int idFrequencia = Integer.parseInt(sc.nextLine());

            frequenciaDAO.excluirFrequencia(idFrequencia);
            System.out.println("Registro de Frequência removido com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número inteiro.");
        } catch (Exception e) {
            System.out.println("Erro ao remover frequência: " + e.getMessage());
        }
    }

    // ============================================================
    // LISTAR FREQUÊNCIA POR ALUNO
    // ============================================================
    private static void listarFrequenciaAluno(Scanner sc, FrequenciaDAO frequenciaDAO, DateTimeFormatter DATE_FORMATTER) {
        try {
            System.out.print("Informe a Matrícula (ID) do Aluno: ");
            int idAluno = Integer.parseInt(sc.nextLine());

            List<Frequencia> frequencias = frequenciaDAO.listarFrequenciaAluno(idAluno);

            if (frequencias.isEmpty()) {
                System.out.println("Nenhum registro de frequência encontrado para o aluno " + idAluno + ".");
                return;
            }

            System.out.println("\n--- FREQUÊNCIA DO ALUNO ID: " + idAluno + " ---");
            System.out.printf("%-15s | %-12s | %-12s | %-20s | %s\n", 
                "ID Registro", "ID Turma", "Data Aula", "Status", "Justificativa");
            System.out.println("----------------------------------------------------------------------------------");

            for (Frequencia f : frequencias) {
                System.out.printf("%-15d | %-12d | %-12s | %-20s | %s\n",
                    f.getIdFrequencia(),
                    f.getIdTurma(),
                    f.getDataAula().format(DATE_FORMATTER),
                    f.getStatusPresenca(),
                    f.getJustificativa().isEmpty() ? "-" : f.getJustificativa()
                );
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Matrícula deve ser um número inteiro.");
        } catch (Exception e) {
            System.out.println("Erro ao listar frequência por aluno: " + e.getMessage());
        }
    }

    // ============================================================
    // LISTAR FREQUÊNCIA POR TURMA
    // ============================================================
    private static void listarFrequenciaTurma(Scanner sc, FrequenciaDAO frequenciaDAO, DateTimeFormatter DATE_FORMATTER) {
        try {
            System.out.print("Informe o ID da Turma: ");
            int idTurma = Integer.parseInt(sc.nextLine());

            List<Frequencia> frequencias = frequenciaDAO.listarFrequenciaTurma(idTurma);

            if (frequencias.isEmpty()) {
                System.out.println("Nenhum registro de frequência encontrado para a turma " + idTurma + ".");
                return;
            }

            System.out.println("\n--- FREQUÊNCIA DA TURMA ID: " + idTurma + " ---");
            System.out.printf("%-15s | %-12s | %-12s | %-20s | %s\n", 
                "ID Registro", "ID Aluno", "Data Aula", "Status", "Justificativa");
            System.out.println("----------------------------------------------------------------------------------");

            for (Frequencia f : frequencias) {
                System.out.printf("%-15d | %-12d | %-12s | %-20s | %s\n",
                    f.getIdFrequencia(),
                    f.getIdAluno(),
                    f.getDataAula().format(DATE_FORMATTER),
                    f.getStatusPresenca(),
                    f.getJustificativa().isEmpty() ? "-" : f.getJustificativa()
                );
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID da Turma deve ser um número inteiro.");
        } catch (Exception e) {
            System.out.println("Erro ao listar frequência por turma: " + e.getMessage());
        }
    }
}