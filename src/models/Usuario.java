package Usuario;

public abstract class Usuario {
    protected int idUsuario;
    protected String nome;
    protected String cpf;
    protected String email;
    protected String senha;

    public Usuario(int idUsuario, String nome, String cpf, String email, String senha) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}