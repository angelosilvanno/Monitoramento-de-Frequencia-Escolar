package src;

import java.util.Scanner;
import views.TurmaView;
import views.AlunoView;
import views.ProfessorView;
import views.FrequenciaView; 

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gerenciar Turmas");
            System.out.println("2. Gerenciar Alunos");
            System.out.println("3. Gerenciar Professor");
            System.out.println("4. Gerenciar Frequência"); // Nova opção
            System.out.println("5. Sair"); // Opção Sair movida
            System.out.print("-> ");

            int op;

            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (op) {
                case 1 -> TurmaView.gerenciarTurmas();
                case 2 -> AlunoView.gerenciarAlunos();
                case 3 -> ProfessorView.gerenciarProfessor();
                case 4 -> FrequenciaView.gerenciarFrequencia(); // Chamada ao novo View
                case 5 -> {
                    System.out.println("Encerrando...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}