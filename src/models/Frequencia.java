package models;

import java.time.LocalDate;

public class Frequencia {
    private int idFrequencia;
    private LocalDate dataAula;
    private String statusPresenca;
    private String justificativa;

    public Frequencia( int idFrequencia, LocalDate dataAula, String statusPresenca, String justificativa){
        this.idFrequencia = idFrequencia;
        this.dataAula = dataAula;
        this.statusPresenca = statusPresenca;
        this.justificativa = justificativa;
    }

    public int getIdFrequencia(){
        return idFrequencia;
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
        res += "id " + getIdFrequencia() + "\n";
        res += " Dia Da Aula: " + getDataAula() + "\n";
        res += " Status da presenca: " + getStatusPresenca() + "\n";
        res += " justificativa " + getJustificativa() + "\n";
        res += "==========================================\n";
        return res;
    }

}