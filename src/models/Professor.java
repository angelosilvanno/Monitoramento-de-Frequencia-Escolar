package models;

public class Professor extends Usuario {

    private String numeroCNDB;
    private boolean coordenador;

    public Professor(int idUsuario, String nome, String cpf, String email, String senha, String numeroCNDB, boolean coordenador) {
        super(idUsuario, nome, cpf, email, senha);
        this.numeroCNDB = numeroCNDB;
        this.coordenador = coordenador;
    }

    //Gets e Sets
    public String getNumeroCNDB() {
        return numeroCNDB;
    }

    public void setNumeroCNDB(String numeroCNDB) {
        this.numeroCNDB = numeroCNDB;
    }

    public boolean getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(boolean coordenador) {
        this.coordenador = coordenador;
    }

    public String toString() {
        String res = "";
        res += super.toString();
        res += "NumeroCNDB: " + getNumeroCNDB() + "\n";
        res += "Coordenador: " + getCoordenador() + "\n";

        return res;
    }

}
