package br.com.catalogo.util;

import br.com.catalogo.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BancoDeDados {

    public static void criarTabelas() {
        String usuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "nome VARCHAR(100) NOT NULL, "
                + "login VARCHAR(30) NOT NULL UNIQUE, "
                + "senha VARCHAR(100) NOT NULL)";

        String itens = "CREATE TABLE IF NOT EXISTS itens ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "titulo VARCHAR(150) NOT NULL, "
                + "tipo VARCHAR(10) NOT NULL, "
                + "criador VARCHAR(100), "
                + "ano INT, "
                + "genero VARCHAR(50), "
                + "nota INT, "
                + "descricao VARCHAR(1000), "
                + "usuario_id BIGINT NOT NULL, "
                + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE)";

        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement()) {
            st.execute(usuarios);
            st.execute(itens);
        } catch (SQLException e) {
            throw new DAOException("Erro ao criar tabelas", e);
        }
    }
}
