package br.com.catalogo.service;

import br.com.catalogo.dao.ItemDAO;
import br.com.catalogo.exception.RegistroNaoEncontradoException;
import br.com.catalogo.exception.ValidacaoException;
import br.com.catalogo.model.Item;
import br.com.catalogo.model.TipoItem;

import java.util.List;

public class ItemService {

    private ItemDAO dao;

    public ItemService() {
        dao = new ItemDAO();
    }

    
    public ItemService(ItemDAO dao) {
        this.dao = dao;
    }

    public List<Item> listar(Long usuarioId, String busca, TipoItem tipo) {
        return dao.listar(usuarioId, busca, tipo);
    }

    public int contar(Long usuarioId, TipoItem tipo) {
        return dao.contarPorTipo(usuarioId, tipo);
    }

    public Item buscar(Long id, Long usuarioId) {
        Item item = dao.buscarPorId(id, usuarioId);
        if (item == null) {
            throw new RegistroNaoEncontradoException("Item não encontrado.");
        }
        return item;
    }

    public void salvar(Item item) throws ValidacaoException {
        validar(item);
        if (item.getId() == null) {
            dao.inserir(item);
        } else {
            buscar(item.getId(), item.getUsuarioId()); 
            dao.atualizar(item);
        }
    }

    public void excluir(Long id, Long usuarioId) {
        boolean excluiu = dao.excluir(id, usuarioId);
        if (!excluiu) {
            throw new RegistroNaoEncontradoException("Item não encontrado.");
        }
    }

    public void validar(Item i) throws ValidacaoException {
        if (i.getTitulo() == null || i.getTitulo().trim().isEmpty()) {
            throw new ValidacaoException("Informe o título.");
        }
        if (i.getTitulo().length() > 150) {
            throw new ValidacaoException("O título pode ter no máximo 150 caracteres.");
        }
        if (i.getTipo() == null) {
            throw new ValidacaoException("Escolha o tipo (livro, série ou filme).");
        }
        if (i.getAno() != null && (i.getAno() < 1800 || i.getAno() > 2100)) {
            throw new ValidacaoException("Ano inválido.");
        }
        if (i.getNota() != null && (i.getNota() < 1 || i.getNota() > 5)) {
            throw new ValidacaoException("A nota tem que ser de 1 a 5.");
        }
        if (i.getDescricao() != null && i.getDescricao().length() > 1000) {
            throw new ValidacaoException("O comentário pode ter no máximo 1000 caracteres.");
        }
    }
}
