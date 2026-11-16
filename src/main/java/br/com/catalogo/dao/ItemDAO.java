package br.com.catalogo.dao;

import br.com.catalogo.exception.DAOException;
import br.com.catalogo.model.Item;
import br.com.catalogo.model.TipoItem;
import br.com.catalogo.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public Item inserir(Item i) {
        String sql = "INSERT INTO itens (titulo, tipo, criador, ano, genero, nota, descricao, usuario_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencher(ps, i);
            ps.setLong(8, i.getUsuarioId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                i.setId(rs.getLong(1));
            }
            return i;
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir item", e);
        }
    }

    public void atualizar(Item i) {
        
        String sql = "UPDATE itens SET titulo = ?, tipo = ?, criador = ?, ano = ?, genero = ?, nota = ?, descricao = ? "
                + "WHERE id = ? AND usuario_id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            preencher(ps, i);
            ps.setLong(8, i.getId());
            ps.setLong(9, i.getUsuarioId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar item", e);
        }
    }

    
    public Item buscarPorId(Long id, Long usuarioId) {
        String sql = "SELECT * FROM itens WHERE id = ? AND usuario_id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.setLong(2, usuarioId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return montarItem(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar item", e);
        }
    }

    public boolean excluir(Long id, Long usuarioId) {
        String sql = "DELETE FROM itens WHERE id = ? AND usuario_id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.setLong(2, usuarioId);
            int linhas = ps.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new DAOException("Erro ao excluir item", e);
        }
    }

    
    public List<Item> listar(Long usuarioId, String busca, TipoItem tipo) {
        String sql = "SELECT * FROM itens WHERE usuario_id = ?";
        if (busca != null && !busca.isEmpty()) {
            sql += " AND (LOWER(titulo) LIKE ? OR LOWER(criador) LIKE ? OR LOWER(genero) LIKE ?)";
        }
        if (tipo != null) {
            sql += " AND tipo = ?";
        }
        sql += " ORDER BY titulo";

        List<Item> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int pos = 1;
            ps.setLong(pos++, usuarioId);
            if (busca != null && !busca.isEmpty()) {
                String termo = "%" + busca.toLowerCase() + "%";
                ps.setString(pos++, termo);
                ps.setString(pos++, termo);
                ps.setString(pos++, termo);
            }
            if (tipo != null) {
                ps.setString(pos++, tipo.name());
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(montarItem(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar itens", e);
        }
    }

    public int contarPorTipo(Long usuarioId, TipoItem tipo) {
        String sql = "SELECT COUNT(*) FROM itens WHERE usuario_id = ? AND tipo = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            ps.setString(2, tipo.name());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DAOException("Erro ao contar itens", e);
        }
    }

    private void preencher(PreparedStatement ps, Item i) throws SQLException {
        ps.setString(1, i.getTitulo());
        ps.setString(2, i.getTipo().name());
        ps.setString(3, i.getCriador());
        if (i.getAno() == null) {
            ps.setNull(4, Types.INTEGER);
        } else {
            ps.setInt(4, i.getAno());
        }
        ps.setString(5, i.getGenero());
        if (i.getNota() == null) {
            ps.setNull(6, Types.INTEGER);
        } else {
            ps.setInt(6, i.getNota());
        }
        ps.setString(7, i.getDescricao());
    }

    private Item montarItem(ResultSet rs) throws SQLException {
        Item i = new Item();
        i.setId(rs.getLong("id"));
        i.setTitulo(rs.getString("titulo"));
        i.setTipo(TipoItem.valueOf(rs.getString("tipo")));
        i.setCriador(rs.getString("criador"));
        i.setAno((Integer) rs.getObject("ano"));
        i.setGenero(rs.getString("genero"));
        i.setNota((Integer) rs.getObject("nota"));
        i.setDescricao(rs.getString("descricao"));
        i.setUsuarioId(rs.getLong("usuario_id"));
        return i;
    }
}
