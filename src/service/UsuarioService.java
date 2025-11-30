package service;

import dao.UsuarioDAO;
import models.Usuario;

public class UsuarioService {

    public Usuario login(String email, String senha) {
        return UsuarioDAO.login(email, senha);
    }

    public void cadastrar(Usuario u) {
        UsuarioDAO.cadastrarUsuario(u);
    }
}
