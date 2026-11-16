package br.com.catalogo.web;

import br.com.catalogo.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {

    protected void abrirPagina(String jsp, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/" + jsp + ".jsp").forward(req, resp);
    }

    protected void redirecionar(String caminho, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + caminho);
    }

    protected Usuario usuarioLogado(HttpServletRequest req) {
        return (Usuario) req.getSession().getAttribute("usuarioLogado");
    }
}
