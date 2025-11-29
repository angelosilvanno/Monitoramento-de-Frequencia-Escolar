package models;

public class Turma {
    private int idTurma;
    private String nomeTurma;
    private Professor professor; 

    public Turma(int idTurma, String nomeTurma){
        this.idTurma = idTurma;
        this.nomeTurma = nomeTurma;
    }

    public int getIdTurma(){
        return idTurma;
    }

    public String getNomeTurma(){
        return nomeTurma;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setIdTurma(int idTurma){
        this.idTurma = idTurma;
    }

    public void setNomeTurma(String nomeTurma){
        this.nomeTurma = nomeTurma;
    }

    @Override
    public String toString() {
        String nomeProf = (professor != null) ? professor.getNome() : "Nenhum";
        String res = "";
        res += "==========================================\n";
        res += "id " + getIdTurma() + "\n";
        res += "nome da turma " + getNomeTurma() + "\n";
        res += "professor " + nomeProf + "\n";
        res += "==========================================\n";
        return res;
    }
}
