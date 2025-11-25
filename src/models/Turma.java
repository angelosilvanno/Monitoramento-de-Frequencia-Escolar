package models;

public class Turma {
    private int idTurma;
    private String nomeTurma;

    public Turma(int idTurma, String nomeTurma){
        this.idTurma = idTurma;
        this.nomeTurma = nomeTurma;
    }

    public int getIdTurma(){
        return idTurma;
    }

    public String getNomeTurma(){
        return  nomeTurma;
    }

    public void setIdTurma(int idTurma){
        this.idTurma = idTurma;
    }

    public void setNomeTurma(String nomeTurma){
        this.nomeTurma = nomeTurma;
    }

   @Override
    public String toString() {
        String res = "";
        res += "id " + getIdTurma() + "\n";
        res += "nome da turma " + getNomeTurma() + "\n";
        res += "==========================================\n";
        return res;
    }

}