package src;

import java.util.Scanner;
import views.TurmaView;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gerenciar Turmas");
            System.out.println("2. Sair");
            System.out.print("-> ");

            int op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1 -> TurmaView.gerenciarTurmas();
                case 2 -> {
                    System.out.println("Encerrando...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}
