package br.com.catalogo.web;

import br.com.catalogo.exception.RegistroNaoEncontradoException;
import br.com.catalogo.exception.ValidacaoException;
import br.com.catalogo.model.Item;
import br.com.catalogo.model.TipoItem;
import br.com.catalogo.model.Usuario;
import br.com.catalogo.service.ItemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/itens")
public class ItemServlet extends BaseServlet {

    private ItemService service = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");
        Usuario usuario = usuarioLogado(req);

        if ("novo".equals(acao)) {
            req.setAttribute("item", new Item());
            req.setAttribute("tipos", TipoItem.values());
            abrirPagina("item-form", req, resp);

        } else if ("editar".equals(acao)) {
            try {
                Long id = Long.parseLong(req.getParameter("id"));
                req.setAttribute("item", service.buscar(id, usuario.getId()));
                req.setAttribute("tipos", TipoItem.values());
                abrirPagina("item-form", req, resp);
            } catch (NumberFormatException | RegistroNaoEncontradoException e) {
                req.getSession().setAttribute("mensagem", "Item não encontrado.");
                redirecionar("/itens", req, resp);
            }

        } else {
            listar(usuario, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");
        Usuario usuario = usuarioLogado(req);

        if ("excluir".equals(acao)) {
            try {
                Long id = Long.parseLong(req.getParameter("id"));
                service.excluir(id, usuario.getId());
                req.getSession().setAttribute("mensagem", "Item excluído.");
            } catch (NumberFormatException | RegistroNaoEncontradoException e) {
                req.getSession().setAttribute("mensagem", "Item não encontrado.");
            }
            redirecionar("/itens", req, resp);
            return;
        }

        
        Item item = new Item();
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            item.setId(Long.parseLong(id));
        }
        item.setTitulo(req.getParameter("titulo"));
        item.setTipo(TipoItem.converter(req.getParameter("tipo")));
        item.setCriador(req.getParameter("criador"));
        item.setGenero(req.getParameter("genero"));
        item.setDescricao(req.getParameter("descricao"));
        item.setUsuarioId(usuario.getId());

        try {
            item.setAno(converterNumero(req.getParameter("ano"), "O ano tem que ser um número."));
            item.setNota(converterNumero(req.getParameter("nota"), "Nota inválida."));
            service.salvar(item);
            req.getSession().setAttribute("mensagem", "Item salvo!");
            redirecionar("/itens", req, resp);
        } catch (ValidacaoException e) {
            req.setAttribute("erro", e.getMessage());
            req.setAttribute("item", item);
            req.setAttribute("tipos", TipoItem.values());
            abrirPagina("item-form", req, resp);
        } catch (RegistroNaoEncontradoException e) {
            req.getSession().setAttribute("mensagem", "Item não encontrado.");
            redirecionar("/itens", req, resp);
        }
    }

    private void listar(Usuario usuario, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String busca = req.getParameter("busca");
        TipoItem tipo = TipoItem.converter(req.getParameter("tipo"));

        req.setAttribute("itens", service.listar(usuario.getId(), busca, tipo));
        req.setAttribute("totalLivros", service.contar(usuario.getId(), TipoItem.LIVRO));
        req.setAttribute("totalSeries", service.contar(usuario.getId(), TipoItem.SERIE));
        req.setAttribute("totalFilmes", service.contar(usuario.getId(), TipoItem.FILME));
        req.setAttribute("busca", busca);
        req.setAttribute("tipo", tipo);
        abrirPagina("itens", req, resp);
    }

    
    private Integer converterNumero(String valor, String mensagemErro) throws ValidacaoException {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new ValidacaoException(mensagemErro);
        }
    }
}
