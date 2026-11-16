package br.com.catalogo.service;

import br.com.catalogo.dao.UsuarioDAO;
import br.com.catalogo.exception.ValidacaoException;
import br.com.catalogo.model.Usuario;
import br.com.catalogo.util.SenhaUtil;

public class UsuarioService {

    private UsuarioDAO dao = new UsuarioDAO();

    public Usuario cadastrar(String nome, String login, String senha, String confirmacao) throws ValidacaoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Informe o nome.");
        }
        if (login == null || login.trim().length() < 3) {
            throw new ValidacaoException("O usuário precisa ter pelo menos 3 caracteres.");
        }
        if (senha == null || senha.length() < 6) {
            throw new ValidacaoException("A senha precisa ter pelo menos 6 caracteres.");
        }
        if (!senha.equals(confirmacao)) {
            throw new ValidacaoException("As senhas não são iguais.");
        }
        if (dao.buscarPorLogin(login.trim()) != null) {
            throw new ValidacaoException("Esse usuário já existe.");
        }

        Usuario u = new Usuario(nome.trim(), login.trim(), SenhaUtil.gerarHash(senha));
        return dao.inserir(u);
    }

    
    public Usuario autenticar(String login, String senha) {
        if (login == null || senha == null) {
            return null;
        }
        Usuario u = dao.buscarPorLogin(login.trim());
        if (u != null && SenhaUtil.confere(senha, u.getSenha())) {
            return u;
        }
        return null;
    }
}
