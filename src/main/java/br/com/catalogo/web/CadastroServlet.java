package br.com.catalogo.web;

import br.com.catalogo.exception.ValidacaoException;
import br.com.catalogo.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cadastro")
public class CadastroServlet extends BaseServlet {

    private UsuarioService service = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        abrirPagina("cadastro", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String login = req.getParameter("login");
        try {
            service.cadastrar(nome, login, req.getParameter("senha"), req.getParameter("confirmacao"));
            req.getSession().setAttribute("mensagem", "Conta criada! Agora é só entrar.");
            redirecionar("/login", req, resp);
        } catch (ValidacaoException e) {
            req.setAttribute("erro", e.getMessage());
            req.setAttribute("nome", nome);
            req.setAttribute("login", login);
            abrirPagina("cadastro", req, resp);
        }
    }
}
