package br.com.catalogo.dao;

import br.com.catalogo.exception.DAOException;
import br.com.catalogo.model.Usuario;
import br.com.catalogo.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {

    public Usuario inserir(Usuario u) {
        String sql = "INSERT INTO usuarios (nome, login, senha) VALUES (?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getSenha());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                u.setId(rs.getLong(1));
            }
            return u;
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir usuario", e);
        }
    }

    
    public Usuario buscarPorLogin(String login) {
        String sql = "SELECT * FROM usuarios WHERE login = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario(rs.getString("nome"), rs.getString("login"), rs.getString("senha"));
                u.setId(rs.getLong("id"));
                return u;
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar usuario", e);
        }
    }
}
