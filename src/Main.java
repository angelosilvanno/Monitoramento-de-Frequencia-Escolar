import java.util.Scanner;
import models.Usuario;
import views.LoginView;
//import views.MenuPrincipalView;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Usuario logado = LoginView.telaLogin(sc);

        if (logado == null) {
            System.out.println("\nEncerrando...");
            return;
        }

        //MenuPrincipalView.menu(logado, sc);
    }
}
