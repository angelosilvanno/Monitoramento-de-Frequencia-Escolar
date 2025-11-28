package models;

import java.time.LocalDate;

public class Frequencia {
    private int idFrequencia;
    private int idAluno;     
    private int idTurma;     
    private LocalDate dataAula;
    private String statusPresenca;
    private String justificativa;

    public Frequencia(int idFrequencia, int idAluno, int idTurma, LocalDate dataAula, String statusPresenca, String justificativa) {
        this.idFrequencia = idFrequencia;
        this.idAluno = idAluno;
        this.idTurma = idTurma;
        this.dataAula = dataAula;
        this.statusPresenca = statusPresenca;
        this.justificativa = justificativa;
    }

    public int getIdFrequencia(){
        return idFrequencia;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public LocalDate getDataAula(){
        return dataAula;
    }

    public String getStatusPresenca(){
        return statusPresenca;
    }

    public String getJustificativa(){
        return justificativa;
    }

    public void setIdfrequencia( int idFrequencia){
        this.idFrequencia = idFrequencia;
    }
    
    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public void setDataAula(LocalDate dataAula){
        this.dataAula = dataAula;
    }

    public void setStatusPresenca(String statusPresenca){
        this.statusPresenca = statusPresenca;
    }

    public void setJustificativa( String justificativa){
        this.justificativa = justificativa;
    }

    @Override
    public String toString() {
        String res = "";
        res += "==========================================\n";
        res += "id Frequência: " + getIdFrequencia() + "\n";
        res += "id Aluno: " + getIdAluno() + "\n";
        res += "id Turma: " + getIdTurma() + "\n";
        res += "Dia Da Aula: " + getDataAula() + "\n";
        res += "Status da presenca: " + getStatusPresenca() + "\n";
        res += "Justificativa: " + getJustificativa() + "\n";
        res += "==========================================\n";
        return res;
    }
}