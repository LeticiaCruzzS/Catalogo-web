package br.com.catalogo.web;

import br.com.catalogo.dao.UsuarioDAO;
import br.com.catalogo.model.Usuario;
import br.com.catalogo.util.BancoDeDados;
import br.com.catalogo.util.SenhaUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InicializacaoListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BancoDeDados.criarTabelas();

        UsuarioDAO dao = new UsuarioDAO();
        if (dao.buscarPorLogin("admin") == null) {
            dao.inserir(new Usuario("Administrador", "admin", SenhaUtil.gerarHash("admin123")));
        }
    }
}
