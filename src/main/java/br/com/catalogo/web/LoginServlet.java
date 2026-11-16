package br.com.catalogo.web;

import br.com.catalogo.model.Usuario;
import br.com.catalogo.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    private UsuarioService service = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        abrirPagina("login", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String senha = req.getParameter("senha");
        Usuario usuario = service.autenticar(login, senha);

        if (usuario == null) {
            req.setAttribute("erro", "Usuário ou senha inválidos.");
            req.setAttribute("login", login);
            abrirPagina("login", req, resp);
        } else {
            req.getSession().setAttribute("usuarioLogado", usuario);
            redirecionar("/itens", req, resp);
        }
    }
}
