package models;

public class Aluno extends Usuario {
    private int matriculaAluno;
    private String nomeResp;
    public Aluno(int idUsuario, String nome,String cpf, String email, String senha, int matricula, String nomeResp){
        super(idUsuario,nome, cpf, email, senha);

        this.matriculaAluno = matricula;
        this.nomeResp = nomeResp;
    }

public int getMatricula(){
    return matriculaAluno;
}

public String getNomeResp(){
    return nomeResp;
}

public void setMatricula( int matricula){
    this.matriculaAluno = matricula;
}

public void setNomeResp(String nomeResp){
    this.nomeResp = nomeResp;
}

public String toString() {
    String res = "";
    res += "       Aluno       \n";
    res += "=========================\n";
    res += "ID: " + getId() + "\n";
    res += "Nome: " + getNome() + "\n";
    res += "CPF: " + getCpf() + "\n";
    res += "Email: " + getEmail() + "\n";
    res += "Matrícula: " + getMatricula() + "\n";
    res += "Responsável: " + getNomeResp() + "\n";
    res += "=========================";
    return res;
}
}
